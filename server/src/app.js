'use strict';
const express = require('express');
const cors = require('cors');
const { applySync } = require('./sync');
const { atRisk, currentStreak } = require('./streaks');

/**
 * Builds the Express app. Dependencies are injected so the same code runs against Firestore in production and
 * an in-memory store in tests.
 *   deps = { store, authenticate, pusher, jobSecret }
 */
function createApp({ store, authenticate, pusher, jobSecret }) {
  const app = express();
  app.disable('x-powered-by');
  app.use(cors());
  app.use(express.json({ limit: '2mb' }));

  const requireUser = async (req, res, next) => {
    const uid = await authenticate(req);
    if (!uid) return res.status(401).json({ error: 'Missing or invalid token' });
    req.uid = uid;
    next();
  };
  const wrap = (fn) => (req, res, next) => Promise.resolve(fn(req, res, next)).catch(next);

  app.get('/health', (req, res) => res.json({ status: 'ok', time: Date.now(), store: store.name }));

  app.post('/api/sync', requireUser, wrap(async (req, res) => {
    const body = req.body || {};
    const since = Number.isFinite(Number(body.since)) ? Number(body.since) : 0;
    const result = await applySync(store, req.uid, since, body.changes || {});
    res.json(result);
  }));

  app.post('/api/devices', requireUser, wrap(async (req, res) => {
    const { token, language, timezone, platform } = req.body || {};
    if (typeof token !== 'string' || token.length < 20 || token.length > 4096) {
      return res.status(400).json({ error: 'A valid FCM token is required' });
    }
    await store.saveDevice(req.uid, {
      token,
      language: typeof language === 'string' ? language.slice(0, 8) : 'en',
      timezone: typeof timezone === 'string' ? timezone.slice(0, 64) : 'UTC',
      platform: typeof platform === 'string' ? platform.slice(0, 16) : 'android',
      updatedAt: Date.now(),
    });
    res.json({ ok: true, message: 'Device registered' });
  }));

  app.delete('/api/devices', requireUser, wrap(async (req, res) => {
    const { token } = req.body || {};
    if (typeof token === 'string' && token) await store.removeDevice(req.uid, token);
    res.json({ ok: true });
  }));

  app.post('/api/push/test', requireUser, wrap(async (req, res) => {
    const devices = await store.devicesForUser(req.uid);
    const sent = await pusher.sendToTokens(devices.map((d) => d.token), { type: 'TEST' });
    res.json({ ok: true, sent, message: sent > 0 ? 'Push sent' : 'No registered device' });
  }));

  app.delete('/api/account', requireUser, wrap(async (req, res) => {
    await store.deleteUser(req.uid);
    res.json({ ok: true, message: 'Account data deleted' });
  }));

  /**
   * Nightly job (Render cron / GitHub Actions schedule): pushes a "streak at risk" alert to every user who has a
   * running streak that is still unlogged today. Protected by the JOB_SECRET header. ?force=1 ignores the hour.
   */
  app.post('/api/jobs/streak-risk', wrap(async (req, res) => {
    if (!jobSecret || req.headers['x-job-secret'] !== jobSecret) return res.status(401).json({ error: 'Unauthorized' });
    const minHour = Number.isFinite(Number(req.query.minHour)) ? Number(req.query.minHour) : 17;
    const force = req.query.force === '1';
    const devices = await store.allDevices();
    const byUser = new Map();
    devices.forEach((d) => { if (!byUser.has(d.uid)) byUser.set(d.uid, []); byUser.get(d.uid).push(d); });

    let notified = 0;
    for (const [uid, userDevices] of byUser) {
      const habits = (await store.listAll(uid, 'habits')).filter((h) => !h.deleted);
      if (!habits.length) continue;
      const logs = (await store.listAll(uid, 'logs')).filter((l) => l.completed && !l.deleted);
      for (const device of userDevices) {
        const tz = device.timezone || 'UTC';
        let today; let hour;
        try {
          today = new Intl.DateTimeFormat('en-CA', { timeZone: tz, year: 'numeric', month: '2-digit', day: '2-digit' }).format(new Date());
          hour = parseInt(new Intl.DateTimeFormat('en-GB', { timeZone: tz, hour: '2-digit', hourCycle: 'h23' }).format(new Date()), 10);
        } catch (e) { continue; }
        if (!force && hour < minHour) continue;
        const risky = habits
          .map((h) => {
            const done = new Set(logs.filter((l) => l.habitId === h.id).map((l) => l.date));
            return { h, done };
          })
          .filter(({ h, done }) => atRisk(h, done, today))
          .map(({ h, done }) => ({ h, streak: currentStreak(h, done, today) }))
          .sort((a, b) => b.streak - a.streak);
        if (!risky.length) continue;
        const top = risky[0];
        const sent = await pusher.sendToTokens([device.token], {
          type: 'STREAK_RISK', habitId: top.h.id, habitName: top.h.name, streak: top.streak,
        });
        notified += sent;
      }
    }
    res.json({ ok: true, users: byUser.size, notified });
  }));

  app.use((req, res) => res.status(404).json({ error: 'Not found' }));
  // eslint-disable-next-line no-unused-vars
  app.use((err, req, res, next) => {
    if (err && err.type === 'entity.parse.failed') return res.status(400).json({ error: 'Invalid JSON' });
    console.error('Unhandled error:', err && err.message);
    res.status(500).json({ error: 'Internal server error' });
  });
  return app;
}

module.exports = { createApp };
