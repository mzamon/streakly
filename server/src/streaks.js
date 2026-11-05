'use strict';
// Port of the Android StreakCalculator so the server can decide who is "at risk" (kept in sync with the app).

const DAY_MS = 24 * 60 * 60 * 1000;

function parseDate(s) {
  if (typeof s !== 'string' || !/^\d{4}-\d{2}-\d{2}/.test(s)) return null;
  const t = Date.UTC(+s.slice(0, 4), +s.slice(5, 7) - 1, +s.slice(8, 10));
  return Number.isNaN(t) ? null : t;
}
function fmt(ms) { return new Date(ms).toISOString().slice(0, 10); }
function isoDow(ms) { const d = new Date(ms).getUTCDay(); return d === 0 ? 7 : d; }

function isDueOn(habit, dateMs, todayMs) {
  const created = parseDate(habit.createdAt) ?? todayMs;
  if (dateMs < created) return false;
  if (habit.frequencyType === 'DAYS') {
    const days = String(habit.daysOfWeek || '').split(',').map((x) => parseInt(x, 10)).filter((n) => !Number.isNaN(n));
    return days.includes(isoDow(dateMs));
  }
  if (habit.frequencyType === 'INTERVAL') {
    const gap = Math.round((dateMs - created) / DAY_MS);
    return gap >= 0 && gap % Math.max(1, habit.interval | 0) === 0;
  }
  return true;
}

function currentStreak(habit, doneSet, todayStr) {
  const today = parseDate(todayStr);
  const created = parseDate(habit.createdAt) ?? today;
  let cursor = today;
  if (isDueOn(habit, today, today) && !doneSet.has(fmt(today))) cursor -= DAY_MS;
  let count = 0;
  for (let guard = 0; cursor >= created && guard < 4000; guard++) {
    if (isDueOn(habit, cursor, today)) {
      if (doneSet.has(fmt(cursor))) count++; else break;
    }
    cursor -= DAY_MS;
  }
  return count;
}

function atRisk(habit, doneSet, todayStr) {
  const today = parseDate(todayStr);
  return isDueOn(habit, today, today) && !doneSet.has(todayStr) && currentStreak(habit, doneSet, todayStr) > 0;
}

module.exports = { isDueOn, currentStreak, atRisk, parseDate };
