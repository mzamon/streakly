const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = process.env.PORT || 3000;
const DATA_FILE = path.join(__dirname, 'data.json');

app.use(cors());
app.use(bodyParser.json({ limit: '5mb' }));

function readDB() {
  try {
    const db = JSON.parse(fs.readFileSync(DATA_FILE, 'utf8'));
    return {
      habits: Array.isArray(db.habits) ? db.habits : [],
      logs: Array.isArray(db.logs) ? db.logs : []
    };
  } catch {
    return { habits: [], logs: [] };
  }
}
function writeDB(db) { fs.writeFileSync(DATA_FILE, JSON.stringify(db, null, 2)); }

app.get('/health', (req, res) => res.json({ status: 'ok', time: Date.now() }));

app.post('/sync', (req, res) => {
  const body = req.body || {};
  if (!Array.isArray(body.habits) || !Array.isArray(body.logs)) {
    return res.status(400).json({ error: 'habits and logs must be arrays' });
  }
  const { habits, logs } = body;
  const db = readDB();

  for (const h of habits) {
    if (!h || typeof h.remoteId !== 'string' || !h.remoteId.trim()) {
      return res.status(400).json({ error: 'each habit requires a remoteId' });
    }
    const existing = db.habits.findIndex(x => x.remoteId === h.remoteId);
    if (existing >= 0) db.habits[existing] = h;
    else db.habits.push(h);
  }
  for (const l of logs) {
    if (!l || typeof l.habitRemoteId !== 'string' || typeof l.date !== 'string') {
      return res.status(400).json({ error: 'each log requires habitRemoteId and date' });
    }
    const key = `${l.habitRemoteId}|${l.date}`;
    const existing = db.logs.findIndex(x => `${x.habitRemoteId}|${x.date}` === key);
    if (existing >= 0) db.logs[existing] = l;
    else db.logs.push(l);
  }
  writeDB(db);
  res.json({ habits: db.habits, logs: db.logs, serverTime: Date.now() });
});

app.listen(PORT, () => console.log(`Streakly API listening on http://0.0.0.0:${PORT}`));
