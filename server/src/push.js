'use strict';

/**
 * Sends data-only FCM messages (so the app's service runs in every state and translates the text on the device).
 * `messaging` is firebase-admin's messaging() instance; it is injected so tests can use a fake.
 */
function createPusher(messaging, store) {
  async function sendToTokens(tokens, data) {
    if (!tokens.length) return 0;
    const payload = Object.fromEntries(Object.entries(data).map(([k, v]) => [k, String(v)]));
    const res = await messaging.sendEachForMulticast({ tokens, data: payload, android: { priority: 'high' } });
    let sent = 0;
    for (let i = 0; i < res.responses.length; i++) {
      const r = res.responses[i];
      if (r.success) { sent++; continue; }
      const code = r.error && r.error.code;
      if (code === 'messaging/registration-token-not-registered' || code === 'messaging/invalid-registration-token') {
        await store.removeDeviceByToken(tokens[i]);   // clean up dead tokens
      }
    }
    return sent;
  }
  return { sendToTokens };
}

module.exports = { createPusher };
