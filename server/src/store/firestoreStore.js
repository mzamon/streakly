'use strict';
const crypto = require('crypto');

const BATCH = 400;

/**
 * Cloud Firestore (NoSQL) store.
 *   users/{uid}/habits|logs|rewards|redemptions/{id}   one document per synchronised row
 *   devices/{sha256(token)}                           FCM tokens, each owned by exactly one user
 */
class FirestoreStore {
  constructor(db) {
    this.name = 'firestore';
    this.db = db;
  }

  _col(uid, coll) { return this.db.collection('users').doc(uid).collection(coll); }
  _key(token) { return crypto.createHash('sha256').update(token).digest('hex'); }

  async getMany(uid, coll, ids) {
    const out = [];
    for (let i = 0; i < ids.length; i += 300) {
      const refs = ids.slice(i, i + 300).map((id) => this._col(uid, coll).doc(id));
      if (!refs.length) continue;
      const snaps = await this.db.getAll(...refs);
      snaps.forEach((s) => { if (s.exists) out.push(s.data()); });
    }
    return out;
  }

  async putMany(uid, coll, items) {
    for (let i = 0; i < items.length; i += BATCH) {
      const batch = this.db.batch();
      items.slice(i, i + BATCH).forEach((item) => batch.set(this._col(uid, coll).doc(item.id), item));
      await batch.commit();
    }
  }

  async changedSince(uid, coll, since) {
    const snap = await this._col(uid, coll).where('serverVersion', '>', since).get();
    return snap.docs.map((d) => d.data());
  }

  async listAll(uid, coll) {
    const snap = await this._col(uid, coll).get();
    return snap.docs.map((d) => d.data());
  }

  async deleteUser(uid) {
    await this.db.recursiveDelete(this.db.collection('users').doc(uid));
    const devices = await this.db.collection('devices').where('uid', '==', uid).get();
    await Promise.all(devices.docs.map((d) => d.ref.delete()));
  }

  async saveDevice(uid, device) {
    await this.db.collection('devices').doc(this._key(device.token)).set({ ...device, uid });
  }

  async removeDevice(uid, token) {
    const ref = this.db.collection('devices').doc(this._key(token));
    const snap = await ref.get();
    if (snap.exists && snap.data().uid === uid) await ref.delete();
  }

  async removeDeviceByToken(token) {
    await this.db.collection('devices').doc(this._key(token)).delete();
  }

  async devicesForUser(uid) {
    const snap = await this.db.collection('devices').where('uid', '==', uid).get();
    return snap.docs.map((d) => d.data());
  }

  async allDevices() {
    const snap = await this.db.collection('devices').get();
    return snap.docs.map((d) => d.data());
  }
}

module.exports = { FirestoreStore };
