'use strict';
const { COLLECTIONS, MAX_ITEMS, normalise, contentKey } = require('./models');

let lastVersion = 0;
/** Strictly increasing version stamp, so "changed since" queries never miss a row. */
function nextVersion() {
  lastVersion = Math.max(Date.now(), lastVersion + 1);
  return lastVersion;
}

const empty = () => ({ habits: [], logs: [], rewards: [], redemptions: [] });

/**
 * Two-way sync for one user.
 *  - Every incoming row carries the serverVersion the client last saw (0 for new rows).
 *  - If the stored row is newer than that AND its content differs, the row is a conflict: it is NOT overwritten
 *    and the stored copy is returned so the user can choose.
 *  - Otherwise the row is written with a fresh serverVersion (last write wins; identical re-uploads are harmless).
 *  - The response holds everything changed after `since`, including the rows just written, so the client can
 *    confirm them.
 */
async function applySync(store, uid, since, incoming) {
  const version = nextVersion();
  const conflicts = empty();
  const changes = empty();
  let rejected = 0;
  let applied = 0;

  for (const coll of COLLECTIONS) {
    const raw = Array.isArray(incoming && incoming[coll]) ? incoming[coll].slice(0, MAX_ITEMS) : [];
    const items = [];
    for (const r of raw) {
      const n = normalise(coll, r);
      if (!n) { rejected++; continue; }
      n.baseVersion = Number.isFinite(Number(r.serverVersion)) ? Number(r.serverVersion) : 0;
      items.push(n);
    }
    const existing = new Map((await store.getMany(uid, coll, items.map((i) => i.id))).map((e) => [e.id, e]));
    const toWrite = [];
    for (const { baseVersion, ...item } of items) {
      const stored = existing.get(item.id);
      if (stored && stored.serverVersion > baseVersion && contentKey(stored) !== contentKey(item)) {
        conflicts[coll].push(stored);
      } else {
        toWrite.push({ ...item, uid, serverVersion: version, syncState: 'SYNCED' });
      }
    }
    if (toWrite.length) await store.putMany(uid, coll, toWrite);
    applied += toWrite.length;
  }

  for (const coll of COLLECTIONS) {
    changes[coll] = (await store.changedSince(uid, coll, since)).map((i) => ({ ...i, uid, syncState: 'SYNCED' }));
  }
  const withMeta = (bundle) => {
    const out = empty();
    COLLECTIONS.forEach((c) => { out[c] = bundle[c].map((i) => ({ ...i, uid, syncState: 'SYNCED' })); });
    return out;
  };
  return { serverTime: version, changes, conflicts: withMeta(conflicts), applied, rejected };
}

module.exports = { applySync, nextVersion };
