# Demonstration video script (record on the physical phone, with voice-over)

Open `/health` on the API a minute before recording (free hosting sleeps). Have these tabs ready: Firebase Authentication, Firestore, Storage, Render dashboard, GitHub (repo, Actions, release), Play Console.

| # | Show | Say |
|---|---|---|
| 1 | Launch the **signed release APK**: animated splash, onboarding | Team, module, what Streakly does, "recorded on a physical phone" |
| 2 | Register (show validation errors, strength meter, success) → Firebase ▸ Authentication ▸ Users | Passwords are hashed by Firebase Authentication |
| 3 | Logout → wrong password error → Forgot password → Login | Error handling |
| 4 | **Continue with Google** → Firebase shows provider `google.com`; Settings ▸ Account & security: link / unlink | SSO |
| 5 | Settings tour: personal details (change photo → Storage console shows `users/{uid}/profile.jpg`), notifications, preferences | Settings persist |
| 6 | Preferences ▸ Language: isiZulu then Setswana, theme light/dark | Multi-language, instant switch |
| 7 | Add a habit (all three schedules), complete it, calendar, habit detail, notes | Final Part 3 foundation features carried over from the prototype |
| 8 | **My Progress** week / month / year | UD4 |
| 9 | **Data & backup**: export CSV and JSON, import, clear cache | UD5 |
| 10 | REST API: Postman `/health` + Render dashboard; Firestore console shows `users/{uid}/habits` | API + NoSQL database |
| 11 | **Offline**: airplane mode → complete habits, add a habit → Offline & sync shows pending → reconnect → pending 0 → rows appear in Firestore | Room + WorkManager |
| 12 | **Conflict**: edit a habit offline on the phone, edit it via Postman/second device, reconnect → Sync status → keep local / cloud | Conflict resolution |
| 13 | **Push**: lock phone → Notifications ▸ *Send a test notification* → notification arrives → tap opens the app; also run the streak-risk job (`?force=1`) | FCM, data messages, deep links |
| 14 | Rewards: earn, create, redeem, history, badges | Gamification |
| 15 | GitHub: commits from all members, green Actions run, README, release notes, tag `v1.0.0-final-poe` | CI, testing |
| 16 | Play Console: signed APK, listing assets, internal-testing upload | Publication readiness |
