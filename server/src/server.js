'use strict';
const path = require('path');
const { createApp } = require('./app');
const { createAuth } = require('./auth');
const { createPusher } = require('./push');
const { FileStore } = require('./store/fileStore');

function loadFirebase() {
  const json = process.env.FIREBASE_SERVICE_ACCOUNT;
  const hasAdc = !!process.env.GOOGLE_APPLICATION_CREDENTIALS;
  if (!json && !hasAdc) return null;
  const admin = require('firebase-admin');
  admin.initializeApp({
    credential: json ? admin.credential.cert(JSON.parse(json)) : admin.credential.applicationDefault(),
  });
  return admin;
}

const admin = loadFirebase();
let store; let firebaseAuth = null; let messaging = null;

if (admin) {
  const { FirestoreStore } = require('./store/firestoreStore');
  store = new FirestoreStore(admin.firestore());
  firebaseAuth = admin.auth();
  messaging = admin.messaging();
  console.log('Streakly API: Firebase Auth + Firestore + FCM enabled');
} else if (process.env.ALLOW_INSECURE_DEV_AUTH === '1') {
  store = new FileStore(path.join(process.env.DATA_DIR || path.join(__dirname, '..', 'data'), 'data.json'));
  messaging = { sendEachForMulticast: async (m) => ({ responses: m.tokens.map(() => ({ success: false, error: { code: 'dev' } })) }) };
  console.warn('Streakly API: DEV MODE (file store, header auth). Never use in production.');
} else {
  console.error('Set FIREBASE_SERVICE_ACCOUNT (production) or ALLOW_INSECURE_DEV_AUTH=1 (local development).');
  process.exit(1);
}

const app = createApp({
  store,
  authenticate: createAuth({ firebaseAuth, allowDevAuth: !admin && process.env.ALLOW_INSECURE_DEV_AUTH === '1' }),
  pusher: createPusher(messaging, store),
  jobSecret: process.env.JOB_SECRET || '',
});

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Streakly API listening on port ${port}`));
