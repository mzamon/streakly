# Release notes

## v1.0.0 — Final PoE (Part 3)

### Innovative features added since the prototype (v0.1.0)
- **Google single sign-on** (Credential Manager + Firebase) on Welcome, Login and Register, with link / unlink in Settings ▸ Account & security.
- **Offline mode with automatic sync**: Room is the source of truth; edits made offline are queued and uploaded by WorkManager the moment a connection returns. Pending count, status banner and a **conflict-resolution screen** (keep this device / keep cloud) show what is happening.
- **Real-time push notifications** (Firebase Cloud Messaging): server-triggered *streak at risk* alerts, reward and conflict notices, a *Send test notification* button, translated on the device, with deep links to the right screen.
- **Multi-language support**: complete English, **isiZulu** and **Setswana** translations (including plurals), switchable instantly in-app.
- **User-defined feature 4 — My Progress analytics**: completion ring plus bar chart for the week, month or year, key stats and a per-habit breakdown.
- **User-defined feature 5 — Cloud profile & data portability**: profile photo stored in Firebase Storage, JSON and CSV export, backup import, clear local cache and delete-all-data.

### Backend
- REST API rewritten: Firebase ID-token verification, **Cloud Firestore** (NoSQL) storage, versioned two-way `/api/sync` with conflict detection, device registration, push endpoints, account deletion and a nightly streak-risk job. 23 automated tests.

### Interface
- Complete redesign: navy + flame-orange design system, animated splash, onboarding, rebuilt Home / Calendar / Rewards / Profile, new Progress, Settings, Sync-status and Backup screens, dark / light / system themes.

### Fixes and improvements (from Part 2 feedback and review)
- Data is now stored per account (previously one shared local table).
- Corrected streak calculation (an earlier missed day now correctly ends a streak) and added unit tests.
- Fixed the launcher icon (now the Streakly flame) and added the missing network-state permission.
- Minimum Android version raised to 8.0 (API 26) for `java.time` support.
- Release build: R8 shrinking with keep rules, signing from environment, `gradlew` fixed for Linux so GitHub Actions runs it directly.
- Reminders now use WorkManager (survive reboots, no exact-alarm permission needed).

### Publishing
- Signed APK / AAB workflow, adaptive icon, Play Store listing text, privacy policy and Data-safety answers in `docs/`.

## v0.1.0 — Part 2 prototype
- Local registration / login, habit scheduling and logging, calendar and streak statistics, points / levels / badges / rewards, CSV export, JSON-file API with manual sync.
