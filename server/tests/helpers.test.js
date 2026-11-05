'use strict';

const fs = require('fs');
const os = require('os');
const path = require('path');
const { createAuth } = require('../src/auth');
const { normalise, contentKey, validId } = require('../src/models');
const { parseDate, isDueOn, currentStreak, atRisk } = require('../src/streaks');
const { FileStore } = require('../src/store/fileStore');
const { createPusher } = require('../src/push');

describe('authentication helper', () => {
  test('dev authentication accepts only bounded non-empty ids', async () => {
    const authenticate = createAuth({ firebaseAuth: null, allowDevAuth: true });
    expect(await authenticate({ headers: { 'x-dev-uid': 'u1' } })).toBe('u1');
    expect(await authenticate({ headers: { 'x-dev-uid': '' } })).toBeNull();
    expect(await authenticate({ headers: { 'x-dev-uid': 'x'.repeat(128) } })).toBeNull();
  });

  test('firebase authentication verifies bearer tokens and rejects failures', async () => {
    const firebaseAuth = { verifyIdToken: jest.fn() };
    firebaseAuth.verifyIdToken.mockResolvedValueOnce({ uid: 'firebase-user' });
    firebaseAuth.verifyIdToken.mockRejectedValueOnce(new Error('invalid'));
    const authenticate = createAuth({ firebaseAuth, allowDevAuth: false });
    expect(await authenticate({ headers: { authorization: 'Bearer token' } })).toBe('firebase-user');
    expect(await authenticate({ headers: { authorization: 'Basic token' } })).toBeNull();
    expect(await authenticate({ headers: { authorization: 'Bearer bad' } })).toBeNull();
  });
});

describe('model helpers', () => {
  test('valid ids reject unsafe document paths', () => {
    expect(validId('habit-1')).toBe(true);
    expect(validId('a/b')).toBe(false);
    expect(validId('.')).toBe(false);
    expect(validId('')).toBe(false);
  });

  test('normalise supplies safe defaults and rejects unusable items', () => {
    expect(normalise('habits', { id: 'h1', interval: 999, frequencyType: 'UNKNOWN' })).toMatchObject({
      id: 'h1', interval: 365, frequencyType: 'DAILY', icon: '🔥', color: '#FF7A00', deleted: false,
    });
    expect(normalise('habits', null)).toBeNull();
  });

  test('content key excludes sync metadata but includes content', () => {
    const a = contentKey({ id: 'h1', name: 'Read', uid: 'u1', serverVersion: 1, syncState: 'SYNCED', updatedAt: 2 });
    const b = contentKey({ id: 'h1', name: 'Read', uid: 'u2', serverVersion: 9, syncState: 'PENDING', updatedAt: 3 });
    expect(a).toBe(b);
  });
});

describe('streak helpers', () => {
  const daily = { createdAt: '2026-10-01', frequencyType: 'DAILY', interval: 1 };
  test('parses valid dates and rejects malformed dates', () => {
    expect(parseDate('2026-10-27')).toBe(Date.UTC(2026, 9, 27));
    expect(parseDate('bad')).toBeNull();
  });

  test('supports daily, selected-day and interval schedules', () => {
    expect(isDueOn(daily, Date.UTC(2026, 9, 27), Date.UTC(2026, 9, 27))).toBe(true);
    expect(isDueOn({ ...daily, frequencyType: 'DAYS', daysOfWeek: '1,3,5' }, Date.UTC(2026, 9, 26), Date.UTC(2026, 9, 27))).toBe(true);
    expect(isDueOn({ ...daily, frequencyType: 'INTERVAL', interval: 3 }, Date.UTC(2026, 9, 4), Date.UTC(2026, 9, 27))).toBe(true);
    expect(isDueOn(daily, Date.UTC(2026, 8, 30), Date.UTC(2026, 9, 27))).toBe(false);
  });

  test('calculates current streak and risk', () => {
    const done = new Set(['2026-10-26', '2026-10-25']);
    expect(currentStreak(daily, done, '2026-10-27')).toBe(2);
    expect(atRisk(daily, done, '2026-10-27')).toBe(true);
  });
});

describe('file store and pusher helpers', () => {
  test('file store persists rows and removes a user', async () => {
    const file = path.join(fs.mkdtempSync(path.join(os.tmpdir(), 'streakly-')), 'store.json');
    const store = new FileStore(file);
    await store.putMany('u1', 'habits', [{ id: 'h1', serverVersion: 2 }]);
    expect(await store.changedSince('u1', 'habits', 1)).toHaveLength(1);
    await store.saveDevice('u1', { token: 'token' });
    expect(await store.devicesForUser('u1')).toHaveLength(1);
    await store.deleteUser('u1');
    expect(await store.listAll('u1', 'habits')).toHaveLength(0);
    expect(await store.devicesForUser('u1')).toHaveLength(0);
  });

  test('pusher counts successes and removes invalid tokens', async () => {
    const removed = [];
    const messaging = {
      sendEachForMulticast: jest.fn().mockResolvedValue({
        responses: [
          { success: true },
          { success: false, error: { code: 'messaging/invalid-registration-token' } },
        ],
      }),
    };
    const pusher = createPusher(messaging, { removeDeviceByToken: async (token) => removed.push(token) });
    expect(await pusher.sendToTokens(['good', 'bad'], { type: 'TEST', count: 1 })).toBe(1);
    expect(messaging.sendEachForMulticast.mock.calls[0][0].data).toEqual({ type: 'TEST', count: '1' });
    expect(removed).toEqual(['bad']);
    expect(await pusher.sendToTokens([], { type: 'TEST' })).toBe(0);
  });
});
