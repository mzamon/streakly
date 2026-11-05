'use strict';

/**
 * Resolves the caller's user id.
 *  - Production: verifies the Firebase ID token sent as "Authorization: Bearer <token>".
 *  - Tests / local dev (ALLOW_INSECURE_DEV_AUTH=1 and no Firebase credentials): trusts the "x-dev-uid" header.
 */
function createAuth({ firebaseAuth, allowDevAuth }) {
  return async function authenticate(req) {
    if (firebaseAuth) {
      const header = req.headers.authorization || '';
      const token = header.startsWith('Bearer ') ? header.slice(7).trim() : '';
      if (!token) return null;
      try {
        const decoded = await firebaseAuth.verifyIdToken(token);
        return decoded.uid;
      } catch (e) {
        return null;
      }
    }
    if (allowDevAuth) {
      const uid = req.headers['x-dev-uid'];
      return typeof uid === 'string' && uid.length > 0 && uid.length < 128 ? uid : null;
    }
    return null;
  };
}

module.exports = { createAuth };
