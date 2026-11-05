'use strict';
const request = require('supertest');
const { createApp } = require('../src/app');
const { createAuth } = require('../src/auth');
const { createPusher } = require('../src/push');
const { FileStore } = require('../src/store/fileStore');
const streaks = require('../src/streaks');

function build() {
  const store = new FileStore(null);
  const sent = [];
  const messaging = {
    sendEachForMulticast: async (m) => {
      sent.push(m);
      return { responses: m.tokens.map(() => ({ success: true })) };
    },
  };
  const app = createApp({
    store,
    authenticate: createAuth({ firebaseAuth: null, allowDevAuth: true }),
    pusher: createPusher(messaging, store),
    jobSecret: 'secret',
  });
  return { app, store, sent };
}

const habit = (over = {}) => ({
  id: 'h1', uid: 'x', name: 'Read', description: '', notes: '', icon: '📚', color: '#FF7A00',
  frequencyType: 'DAILY', daysOfWeek: '', interval: 1, reminderHour: -1, reminderMinute: 0,
  createdAt: '2026-10-01', updatedAt: 1000, serverVersion: 0, syncState: 'PENDING', deleted: false, ...over,
});
const sync = (app, uid, body) => request(app).post('/api/sync').set('x-dev-uid', uid).send(body);

describe('health and auth', () => {
  test('GET /health is public', async () => {
    const { app } = build();
    const res = await request(app).get('/health');
    expect(res.status).toBe(200);
    expect(res.body.status).toBe('ok');
    expect(res.body.store).toBe('file');
  });

  test('protected routes reject callers without a token', async () => {
    const { app } = build();
    expect((await request(app).post('/api/sync').send({})).status).toBe(401);
    expect((await request(app).post('/api/devices').send({})).status).toBe(401);
    expect((await request(app).delete('/api/account')).status).toBe(401);
  });

  test('unknown routes return JSON 404 and bad JSON returns 400', async () => {
    const { app } = build();
    expect((await request(app).get('/nope')).status).toBe(404);
    const bad = await request(app).post('/api/sync').set('x-dev-uid', 'u').set('Content-Type', 'application/json').send('{oops');
    expect(bad.status).toBe(400);
  });
});

describe('sync', () => {
  test('a pushed row is stored, echoed with a server version and visible to another device', async () => {
    const { app } = build();
    const first = await sync(app, 'u1', { since: 0, changes: { habits: [habit()] } });
    expect(first.status).toBe(200);
    expect(first.body.changes.habits).toHaveLength(1);
    expect(first.body.changes.habits[0].serverVersion).toBeGreaterThan(0);
    expect(first.body.changes.habits[0].updatedAt).toBe(1000);
    expect(first.body.conflicts.habits).toHaveLength(0);

    const other = await sync(app, 'u1', { since: 0, changes: {} });
    expect(other.body.changes.habits.map((h) => h.id)).toEqual(['h1']);
  });

  test('only rows newer than the cursor are returned', async () => {
    const { app } = build();
    const first = await sync(app, 'u1', { since: 0, changes: { habits: [habit()] } });
    const again = await sync(app, 'u1', { since: first.body.serverTime, changes: {} });
    expect(again.body.changes.habits).toHaveLength(0);
  });

  test('re-uploading identical content is idempotent and is echoed so the client can confirm it', async () => {
    const { app } = build();
    const first = await sync(app, 'u1', { since: 0, changes: { habits: [habit()] } });
    const second = await sync(app, 'u1', { since: first.body.serverTime, changes: { habits: [habit({ updatedAt: 2000 })] } });
    expect(second.body.conflicts.habits).toHaveLength(0);
    expect(second.body.changes.habits).toHaveLength(1);
    expect(second.body.changes.habits[0].updatedAt).toBe(2000);
  });

  test('editing a stale copy with different content is reported as a conflict and not overwritten', async () => {
    const { app } = build();
    await sync(app, 'u1', { since: 0, changes: { habits: [habit({ name: 'Read' })] } });
    // Device B never saw the server copy (serverVersion 0) but edits the same row
    const b = await sync(app, 'u1', { since: 0, changes: { habits: [habit({ name: 'Read 30 min', updatedAt: 3000 })] } });
    expect(b.body.conflicts.habits).toHaveLength(1);
    expect(b.body.conflicts.habits[0].name).toBe('Read');
    const check = await sync(app, 'u1', { since: 0, changes: {} });
    expect(check.body.changes.habits[0].name).toBe('Read');
  });

  test('rebasing on the server version resolves the conflict (keep local)', async () => {
    const { app } = build();
    await sync(app, 'u1', { since: 0, changes: { habits: [habit({ name: 'Read' })] } });
    const conflict = await sync(app, 'u1', { since: 0, changes: { habits: [habit({ name: 'Mine', updatedAt: 3000 })] } });
    const serverVersion = conflict.body.conflicts.habits[0].serverVersion;
    const resolved = await sync(app, 'u1', { since: 0, changes: { habits: [habit({ name: 'Mine', updatedAt: 4000, serverVersion })] } });
    expect(resolved.body.conflicts.habits).toHaveLength(0);
    expect(resolved.body.changes.habits[0].name).toBe('Mine');
  });

  test('all four collections sync and soft-deleted rows travel too', async () => {
    const { app } = build();
    const res = await sync(app, 'u1', {
      since: 0,
      changes: {
        habits: [habit({ deleted: true })],
        logs: [{ id: 'h1|2026-10-02', habitId: 'h1', date: '2026-10-02', completed: true, note: 'ok', updatedAt: 5 }],
        rewards: [{ id: 'r1', name: 'Coffee', cost: 50, updatedAt: 6 }],
        redemptions: [{ id: 'x1', rewardId: 'r1', rewardName: 'Coffee', cost: 50, date: '2026-10-03', updatedAt: 7 }],
      },
    });
    expect(res.body.changes.habits[0].deleted).toBe(true);
    expect(res.body.changes.logs[0].completed).toBe(true);
    expect(res.body.changes.rewards[0].cost).toBe(50);
    expect(res.body.changes.redemptions[0].rewardName).toBe('Coffee');
  });

  test('every field the app needs is always present with the right type', async () => {
    const { app } = build();
    const res = await sync(app, 'u1', { since: 0, changes: { habits: [{ id: 'bare' }] } });
    const h = res.body.changes.habits[0];
    expect(typeof h.name).toBe('string');
    expect(typeof h.interval).toBe('number');
    expect(typeof h.deleted).toBe('boolean');
    expect(h.syncState).toBe('SYNCED');
    expect(h.uid).toBe('u1');
  });

  test('invalid rows are rejected without breaking the request', async () => {
    const { app } = build();
    const res = await sync(app, 'u1', { since: 'abc', changes: { habits: [null, { name: 'no id' }, { id: 'a/b' }, habit()], logs: 'nonsense', extra: [1] } });
    expect(res.status).toBe(200);
    expect(res.body.rejected).toBe(3);
    expect(res.body.changes.habits).toHaveLength(1);
  });

  test('users can never see each other\'s data', async () => {
    const { app } = build();
    await sync(app, 'alice', { since: 0, changes: { habits: [habit()] } });
    const bob = await sync(app, 'bob', { since: 0, changes: {} });
    expect(bob.body.changes.habits).toHaveLength(0);
  });
});

describe('devices, push and account', () => {
  const token = 'x'.repeat(40);

  test('registering a device validates the token', async () => {
    const { app } = build();
    const bad = await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token: 'short' });
    expect(bad.status).toBe(400);
    const ok = await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token, language: 'zu', timezone: 'Africa/Johannesburg' });
    expect(ok.status).toBe(200);
  });

  test('test push goes to the caller\'s devices only', async () => {
    const { app, sent } = build();
    await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token });
    await request(app).post('/api/devices').set('x-dev-uid', 'u2').send({ token: 'y'.repeat(40) });
    const res = await request(app).post('/api/push/test').set('x-dev-uid', 'u1');
    expect(res.body.sent).toBe(1);
    expect(sent[0].tokens).toEqual([token]);
    expect(sent[0].data.type).toBe('TEST');
  });

  test('push without a registered device reports zero', async () => {
    const { app } = build();
    const res = await request(app).post('/api/push/test').set('x-dev-uid', 'nobody');
    expect(res.body.sent).toBe(0);
  });

  test('a token that moves to another account is no longer used for the old one', async () => {
    const { app, store } = build();
    await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token });
    await request(app).post('/api/devices').set('x-dev-uid', 'u2').send({ token });
    expect(await store.devicesForUser('u1')).toHaveLength(0);
    expect(await store.devicesForUser('u2')).toHaveLength(1);
  });

  test('deleting the account removes the data and devices', async () => {
    const { app, store } = build();
    await sync(app, 'u1', { since: 0, changes: { habits: [habit()] } });
    await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token });
    const res = await request(app).delete('/api/account').set('x-dev-uid', 'u1');
    expect(res.status).toBe(200);
    expect((await sync(app, 'u1', { since: 0, changes: {} })).body.changes.habits).toHaveLength(0);
    expect(await store.devicesForUser('u1')).toHaveLength(0);
  });
});

describe('streak-risk job', () => {
  const today = new Date().toISOString().slice(0, 10);
  const day = (offset) => new Date(Date.now() - offset * 86400000).toISOString().slice(0, 10);

  async function seed(app, doneOffsets) {
    await request(app).post('/api/devices').set('x-dev-uid', 'u1').send({ token: 'z'.repeat(40), timezone: 'UTC' });
    await sync(app, 'u1', {
      since: 0,
      changes: {
        habits: [habit({ id: 'run', name: 'Run', createdAt: day(30) })],
        logs: doneOffsets.map((o) => ({ id: `run|${day(o)}`, habitId: 'run', date: day(o), completed: true, updatedAt: 1 })),
      },
    });
  }

  test('requires the job secret', async () => {
    const { app } = build();
    expect((await request(app).post('/api/jobs/streak-risk')).status).toBe(401);
    expect((await request(app).post('/api/jobs/streak-risk').set('x-job-secret', 'wrong')).status).toBe(401);
  });

  test('notifies a user whose running streak is unlogged today', async () => {
    const { app, sent } = build();
    await seed(app, [1, 2, 3]);
    const res = await request(app).post('/api/jobs/streak-risk?force=1').set('x-job-secret', 'secret');
    expect(res.body.notified).toBe(1);
    expect(sent[0].data.type).toBe('STREAK_RISK');
    expect(sent[0].data.habitName).toBe('Run');
    expect(sent[0].data.streak).toBe('3');
  });

  test('does not notify when today is already logged or there is no streak', async () => {
    const a = build();
    await seed(a.app, [0, 1, 2]);
    expect((await request(a.app).post('/api/jobs/streak-risk?force=1').set('x-job-secret', 'secret')).body.notified).toBe(0);
    const b = build();
    await seed(b.app, []);
    expect((await request(b.app).post('/api/jobs/streak-risk?force=1').set('x-job-secret', 'secret')).body.notified).toBe(0);
  });

  test('respects the evening hour unless forced', async () => {
    const { app } = build();
    await seed(app, [1, 2]);
    const res = await request(app).post('/api/jobs/streak-risk?minHour=24').set('x-job-secret', 'secret');
    expect(res.body.notified).toBe(0);
  });
});

describe('streak maths (mirrors the Android app)', () => {
  const habitDef = { createdAt: '2026-09-01', frequencyType: 'DAILY', daysOfWeek: '', interval: 1 };
  test('current streak and risk', () => {
    const done = new Set(['2026-10-26', '2026-10-25']);
    expect(streaks.currentStreak(habitDef, done, '2026-10-27')).toBe(2);
    expect(streaks.atRisk(habitDef, done, '2026-10-27')).toBe(true);
    done.add('2026-10-27');
    expect(streaks.atRisk(habitDef, done, '2026-10-27')).toBe(false);
    expect(streaks.currentStreak(habitDef, new Set(['2026-10-24']), '2026-10-27')).toBe(0);
  });
  test('specific days only count due days', () => {
    const mwf = { ...habitDef, frequencyType: 'DAYS', daysOfWeek: '1,3,5' };
    expect(streaks.currentStreak(mwf, new Set(['2026-10-26', '2026-10-23', '2026-10-21']), '2026-10-27')).toBe(3);
  });
});
