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
  try { return JSON.parse(fs.readFileSync(DATA_FILE, 'utf8')); }
  catch { return { habits: [], logs: [] }; }
}
function writeDB(db) { fs.writeFileSync(DATA_FILE, JSON.stringify(db, null, 2)); }

app.get('/health', (req, res) => res.json({ status: 'ok', time: Date.now() }));

app.post('/sync', (req, res) => {
  const { habits = [], logs = [] } = req.body || {};
  const db = readDB();

  for (const h of habits) {
    const existing = db.habits.findIndex(x => x.remoteId === h.remoteId);
    if (existing >= 0) db.habits[existing] = h;
    else db.habits.push(h);
  }
  for (const l of logs) {
    const key = `${l.habitRemoteId}|${l.date}`;
    const existing = db.logs.findIndex(x => `${x.habitRemoteId}|${x.date}` === key);
    if (existing >= 0) db.logs[existing] = l;
    else db.logs.push(l);
  }
  writeDB(db);
  res.json({ habits: db.habits, logs: db.logs, serverTime: Date.now() });
});

app.listen(PORT, () => console.log(`Streakly API listening on http://0.0.0.0:${PORT}`));
