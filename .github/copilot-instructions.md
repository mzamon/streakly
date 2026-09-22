# Copilot instructions — Streakly

Short, actionable notes for AI coding agents working on this repo.

-- Quick actions
- Run server: `cd server && npm install && npm start` (server listens on :3000).
- Build app: `./gradlew assembleDebug` or open in Android Studio and run.
- Run unit tests: `./gradlew testDebugUnitTest`.
- Generate launcher icons in Android Studio (res → New → Image Asset) as `ic_flame_logo`.

-- Big picture
- Local-first Android app using Room (database: `streakly.db`) at `app/src/main/java/com/streakly/app/data/local`.
- Repositories live in `data/repository` and adapt Room entities to app models.
- Auth is local PBKDF2 (see `data/repository/AuthRepository.kt`) with salt+hash stored in `users` table; session info uses `data/session/SessionManager.kt` (EncryptedSharedPreferences).
- Remote sync is optional: Retrofit client at `data/remote/RetrofitClient.kt` points to `http://10.0.2.2:3000/` (emulator). Server is `server/server.js` (simple JSON persistence).
- Periodic sync via WorkManager: `worker/SyncWorker.kt` (uses `pushPending()` / `refreshFromServer()` patterns).

-- Critical project-specific patterns
- Dates: ISO `LocalDate.toString()` (see `utils/DateUtils.kt`). Treat `createdAt` as ISO date strings.
- Pending-sync pattern: entities include `pendingSync: Boolean`. Repositories set `pendingSync = true` on writes; `pushPending()` maps local ids → `remoteId` and POSTs to `/sync`.
- Remote id namespace: `remoteId` may be null locally; `HabitRepository` generates UUIDs before sync and persists them.
- DB migrations: `StreaklyDatabase.kt` currently uses `fallbackToDestructiveMigration()` and `version = 1`. To add columns safely, bump `version` and provide Migration objects.
- Encrypted prefs: session and small secrets live in `SessionManager` (EncryptedSharedPreferences + MasterKey). Avoid duplicating secret storage elsewhere.

-- Files to reference for common tasks
- Authentication flow & password hashing: `data/repository/AuthRepository.kt`.
- Local DB layout: `data/local/*` (entities, DAOs, `StreaklyDatabase.kt`).
- Sync API & client: `server/server.js` and `data/remote/RetrofitClient.kt` + `data/remote/ApiService.kt`.
- Background sync worker: `worker/SyncWorker.kt`.
- Streak logic: `utils/StreakCalculator.kt` and `utils/Gamification.kt` (unit-tested).
- Notifications & reminders: `utils/NotificationHelper.kt`, `ReminderScheduler.kt`, `ReminderReceiver.kt`.

-- Small but important constraints
- Network: `res/xml/network_security_config.xml` allows cleartext to `10.0.2.2` and `localhost` (emulator). Change `RetrofitClient.BASE_URL` for physical device testing.
- UI uses view binding in activities (e.g., `ActivityWelcomeBinding`) and simple RecyclerView adapters in `ui/adapters`.
- Icons and colours: resources under `res/values` and `res/drawable` — prefer existing palettes/naming when adding new assets.

-- When editing sync or data shapes
- Update DTOs in `data/remote/dto/Dtos.kt` and server `server/data.json` handling.
- Keep `pendingSync` lifecycle: mark synced items appropriately in DAOs after server ack (current `pushPending()` assumes success; add explicit markSynced calls if changing semantics).

-- CI and tests
- CI workflow: `.github/workflows/android.yml` runs `testDebugUnitTest` and `assembleDebug` on push/PR to `main`.
- Unit tests focus on `StreakCalculator` and `Gamification` — add tests there for any streak/points logic changes.

If anything here is unclear or you want more detail (migrations example, adding a new API endpoint, or where the sync scheduler is invoked), tell me which area to expand.
