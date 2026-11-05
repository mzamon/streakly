# Streakly Part 3 POE submission checklist

This checklist records what is complete in Git and what still requires the team,
Firebase, Render, Play Console, a physical phone, or submission evidence. Do not
mark an item complete without retaining the evidence listed.

## Repository and CI

- [x] Repository is hosted at `https://github.com/mzamon/streakly`.
- [x] `README.md`, Kotlin source, Firebase rules, server source, and documentation are committed.
- [x] GitHub Actions workflow runs Kotlin tests, an Android debug build, API tests, and a README smoke test.
- [x] README badge points to `mzamon/streakly`.
- [x] Run and retain the successful CI run URL and artifact links.
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

- [x] Run `./gradlew testDebugUnitTest` with the real Kotlin compiler; all 39 tests pass.
- [x] Run `cd server && npm ci && npm test`; all 23 API tests pass.
- [x] Run `./gradlew assembleDebug` successfully.
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

- README smoke test passes locally: `python -m unittest -v test_readme_exists.py`.
- Android unit tests and the debug APK build pass locally with a temporary ignored `local.properties`.
- API tests pass locally after `npm ci`: 23 tests passed.
- The CI smoke test is committed in `8bd9f74`.
- No Firebase credentials, signing keys, personal device evidence, Play Console
  evidence, translation review, or final video link is present in this repository yet.
