'use strict';
const fs = require('fs');
const path = require('path');
const crypto = require('crypto');

/** JSON-file store for local development and tests (production uses Cloud Firestore). */
class FileStore {
  constructor(filePath = null) {
    this.name = 'file';
    this.filePath = filePath;
    this.data = { users: {}, devices: {} };
    if (filePath && fs.existsSync(filePath)) {
      try { this.data = JSON.parse(fs.readFileSync(filePath, 'utf8')); } catch (e) { /* start empty */ }
    }
  }

  _save() {
    if (!this.filePath) return;
    fs.mkdirSync(path.dirname(this.filePath), { recursive: true });
    fs.writeFileSync(this.filePath, JSON.stringify(this.data));
  }

  _coll(uid, coll) {
    if (!this.data.users[uid]) this.data.users[uid] = {};
    if (!this.data.users[uid][coll]) this.data.users[uid][coll] = {};
    return this.data.users[uid][coll];
  }

  async getMany(uid, coll, ids) {
    const c = this._coll(uid, coll);
    return ids.map((id) => c[id]).filter(Boolean).map((x) => ({ ...x }));
  }

  async putMany(uid, coll, items) {
    const c = this._coll(uid, coll);
    items.forEach((i) => { c[i.id] = { ...i }; });
    this._save();
  }

  async changedSince(uid, coll, since) {
    return Object.values(this._coll(uid, coll)).filter((i) => i.serverVersion > since).map((x) => ({ ...x }));
  }

  async listAll(uid, coll) {
    return Object.values(this._coll(uid, coll)).map((x) => ({ ...x }));
  }

  async deleteUser(uid) {
    delete this.data.users[uid];
    Object.keys(this.data.devices).forEach((k) => { if (this.data.devices[k].uid === uid) delete this.data.devices[k]; });
    this._save();
  }

  _key(token) { return crypto.createHash('sha256').update(token).digest('hex'); }

  async saveDevice(uid, device) {
    this.data.devices[this._key(device.token)] = { ...device, uid };
    this._save();
  }

  async removeDevice(uid, token) {
    const k = this._key(token);
    if (this.data.devices[k] && this.data.devices[k].uid === uid) delete this.data.devices[k];
    this._save();
  }

  async removeDeviceByToken(token) {
    delete this.data.devices[this._key(token)];
    this._save();
  }

  async devicesForUser(uid) {
    return Object.values(this.data.devices).filter((d) => d.uid === uid).map((x) => ({ ...x }));
  }

  async allDevices() {
    return Object.values(this.data.devices).map((x) => ({ ...x }));
  }
}

module.exports = { FileStore };
