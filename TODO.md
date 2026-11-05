# Streakly Part 3 POE submission checklist

This checklist records what is complete in Git and what still requires the team,
Firebase, Render, Play Console, a physical phone, or submission evidence. Do not
mark an item complete without retaining the evidence listed.

## Rubric audit (maximum-mark actions)

The implementation for offline sync, NoSQL/API storage, blob storage, authentication,
SSO, notifications, multi-language support, progress analytics, rewards, export/import,
settings, validation, and UI is present in the repository. The remaining unchecked
items below are external proof or release tasks; complete them before submission so
the implementation can earn the top rubric band.

- [x] Automated coverage expanded to 43 Kotlin unit tests and 33 API tests, including
  utility functions and exported server helpers.
- [x] GitHub Actions runs Android tests, API tests, and a debug APK build.
- [x] Gradle wrapper aligned with AGP 8.2.2 at Gradle 8.5; rerun CI and retain the
  green run URL after pushing this change.
- [ ] Prove the app works on a physical Android phone, not only an emulator; retain
  installation and feature-test evidence.
- [ ] Demonstrate Firebase Authentication password handling, email verification,
  Google SSO, Firestore reads/writes, Firebase Storage photo upload, and account
  deletion in the video.
- [ ] Demonstrate offline writes, WorkManager retry/sync, conflict resolution, and
  push notifications in foreground, background, and killed-app states.
- [ ] Have isiZulu and Setswana translations reviewed by speakers and retain the
  review evidence.
- [ ] Complete the professional voice-over video and add its unlisted URL to README.
- [ ] Add physical-phone screenshots, Play Store feature graphic, signed APK/AAB,
  Play Console internal-test evidence, and live privacy-policy URL.
- [ ] Replace placeholders in `docs/AI_USE.md`, cite tools truthfully, and ensure
  every team member can explain submitted code.
- [ ] Confirm comments/logging evidence, final release notes, multiple contributor
  commits, final tag, and GitHub Release assets.

## Repository and CI

- [x] Repository is hosted at `https://github.com/mzamon/streakly`.
- [x] `README.md`, Kotlin source, Firebase rules, server source, and documentation are committed.
- [x] GitHub Actions workflow runs Kotlin tests, an Android debug build, and API tests.
- [x] README badge points to `mzamon/streakly`.
- [ ] Run and retain the successful CI run URL and artifact links.
- [ ] Use feature branches for any remaining changes and merge them through pull requests.
- [ ] Confirm visible commits from all three team members.
- [ ] After every required external item is complete, create and push:
  `git tag -a v1.0.0-final-poe -m "Final POE"` and
  `git push origin v1.0.0-final-poe`.

## Firebase and backend

- [ ] Create the Firebase project and Android app with package `com.streakly.app`.
- [ ] Enable Email/Password and Google sign-in.
- [ ] Enable Firestore and Storage; confirm Storage billing requirements.
- [ ] Add debug, release/upload, and Play App Signing SHA-1/SHA-256 fingerprints.
- [ ] Add the real `app/google-services.json` locally. Never commit it.
- [ ] Publish and verify `firebase/firestore.rules` and `firebase/storage.rules`.
- [ ] Deploy the Render blueprint from `render.yaml`.
- [ ] Configure `FIREBASE_SERVICE_ACCOUNT` and retain the service health response showing
  `"store":"firestore"`.
- [ ] Configure `JOB_SECRET` and verify the nightly streak-risk endpoint.
- [ ] Set `API_BASE_URL` and `PRIVACY_URL` in local `local.properties`.

## Build and testing

- [x] Run `./gradlew testDebugUnitTest` with the Gradle 8.5 wrapper; Android compilation and unit tests pass.
- [x] Run `cd server && npm ci && npm test`; all 33 API tests pass.
- [x] Run `./gradlew assembleDebug` successfully with the Gradle 8.5 wrapper.
- [ ] Run `./gradlew assembleRelease` and `./gradlew bundleRelease` successfully with
  the real signing configuration.
- [ ] Install the signed release build on a physical Android phone.
- [ ] Test registration, email login, password handling, Google SSO, settings, habits,
  calendar, rewards, progress, export/import, account deletion, and error states.
- [ ] Test offline habit changes and synchronization after reconnecting.
- [ ] Test foreground, background, and killed-app push notifications.
- [ ] Have isiZulu and Setswana speakers review the translations and record their feedback.

## POE evidence and publishing

- [ ] Confirm User Defined 4 and 5 against the Part 2 marking memo; update README and
  release notes if the selected features overlap.
- [ ] Capture physical-phone screenshots for splash, home, calendar, habit detail,
  rewards, progress, settings, and at least one translated screen.
- [ ] Add screenshots under `docs/screenshots/` and verify all README links render.
- [ ] Record the final demonstration video using `docs/DEMO_SCRIPT.md`, including
  voice-over, hosted authentication/API/database evidence, offline sync, notifications,
  SSO, and both South African languages.
- [ ] Add the unlisted video URL to README.
- [ ] Edit `docs/AI_USE.md` truthfully, keep it under 500 words, and cite all tools used.
- [ ] Create the Play Store feature graphic and complete `docs/PLAY_STORE.md`.
- [ ] Publish `docs/privacy-policy.html` with GitHub Pages and use its live URL in
  `PRIVACY_URL` and Play Console.
- [ ] Complete Play Console listing, data safety, content rating, target audience,
  account deletion, internal testing, and screenshots.
- [ ] Upload the signed AAB to Play Console internal testing.
- [ ] Add `GOOGLE_SERVICES_JSON`, `KEYSTORE_B64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`,
  and `KEY_PASSWORD` as GitHub Actions secrets.
- [ ] Run the release workflow from `v1.0.0-final-poe` and attach the signed APK/AAB
  and release notes to the GitHub release.

## Current verified repository changes

- API tests pass locally after `npm ci`: 33 tests passed.
- Android unit-test verification passes with the Gradle 8.5 wrapper in an unlocked
  working copy; retain the green CI run as the authoritative evidence.
- No Firebase credentials, signing keys, personal device evidence, Play Console
  evidence, translation review, or final video link is present in this repository yet.
