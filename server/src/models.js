'use strict';
// Validation and normalisation of the four synchronised collections. The Android app deserialises these
// objects with Gson, so every field must always be present with the right type.

const COLLECTIONS = ['habits', 'logs', 'rewards', 'redemptions'];
const MAX_ITEMS = 5000;

const str = (v, max = 200) => (typeof v === 'string' ? v.slice(0, max) : '');
const int = (v, def = 0) => (Number.isFinite(Number(v)) ? Math.trunc(Number(v)) : def);
const bool = (v) => v === true;
const validId = (id) => typeof id === 'string' && id.length > 0 && id.length <= 200 && !id.includes('/') && id !== '.' && id !== '..';

const normalisers = {
  habits: (i) => ({
    id: i.id, name: str(i.name, 100), description: str(i.description, 300), notes: str(i.notes, 500),
    icon: str(i.icon, 16) || '🔥', color: str(i.color, 9) || '#FF7A00',
    frequencyType: ['DAILY', 'DAYS', 'INTERVAL'].includes(i.frequencyType) ? i.frequencyType : 'DAILY',
    daysOfWeek: str(i.daysOfWeek, 20), interval: Math.min(365, Math.max(1, int(i.interval, 1))),
    reminderHour: int(i.reminderHour, -1), reminderMinute: int(i.reminderMinute, 0),
    createdAt: str(i.createdAt, 10), updatedAt: int(i.updatedAt), deleted: bool(i.deleted),
  }),
  logs: (i) => ({
    id: i.id, habitId: str(i.habitId), date: str(i.date, 10), completed: bool(i.completed),
    note: str(i.note, 500), updatedAt: int(i.updatedAt), deleted: bool(i.deleted),
  }),
  rewards: (i) => ({
    id: i.id, name: str(i.name, 100), description: str(i.description, 300), cost: Math.max(0, int(i.cost)),
    icon: str(i.icon, 16) || '🎁', updatedAt: int(i.updatedAt), deleted: bool(i.deleted),
  }),
  redemptions: (i) => ({
    id: i.id, rewardId: str(i.rewardId), rewardName: str(i.rewardName, 100), cost: Math.max(0, int(i.cost)),
    date: str(i.date, 10), updatedAt: int(i.updatedAt), deleted: bool(i.deleted),
  }),
};

/** Returns the normalised item (without uid / serverVersion) or null when it is unusable. */
function normalise(collection, item) {
  if (!item || typeof item !== 'object' || !validId(item.id)) return null;
  return normalisers[collection](item);
}

/** Fields that describe the content (used to tell a real conflict from a harmless re-upload). */
function contentKey(item) {
  const { uid, serverVersion, syncState, updatedAt, ...rest } = item;
  return JSON.stringify(Object.keys(rest).sort().map((k) => [k, rest[k]]));
}

module.exports = { COLLECTIONS, MAX_ITEMS, normalise, contentKey, validId };
