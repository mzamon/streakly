# Google Play Store preparation

Everything needed for the "prepared for publication" criterion. Items marked ☐ must be done with your own accounts and devices.

## Build artefacts
- ☐ Signed **APK** (`./gradlew assembleRelease`) — installed and tested on a physical phone
- ☐ Signed **AAB** (`./gradlew bundleRelease`) — what Play Console accepts
- ☐ Both attached to the GitHub Release created by the *Release* workflow
- ☐ `applicationId` `com.streakly.app`, `versionCode 1`, `versionName 1.0.0`, `targetSdk 34` (**check Play's current required API level** before uploading and raise `compileSdk`/`targetSdk` if needed)

## Store listing text
**App name:** Streakly (≤ 30 characters)

**Short description (≤ 80):** Build habits that stick. Works offline, keeps your streak.

**Full description:**
> Streakly turns small daily habits into big changes.
>
> 🔥 Build streaks — daily, on chosen days, or every few days
> 📶 Works offline — your progress syncs automatically when you reconnect
> 🏆 Earn XP, level up, unlock badges and redeem rewards you create
> 📊 See your progress by week, month and year
> 🔔 Get reminders and a nudge before a streak breaks
> 🔐 Sign in with e-mail or Google; export or delete your data at any time
> 🌍 Available in English, isiZulu and Setswana
>
> Small habits. Big changes.

## Graphics
| Asset | Spec | Source |
|---|---|---|
| App icon | 512 × 512 PNG | `app/src/main/streakly-playstore.png` (existing) |
| Feature graphic | 1024 × 500 PNG/JPG | ☐ create (navy gradient, flame, wordmark) |
| Phone screenshots | at least 2, up to 8 | ☐ capture on the phone: Home, Calendar, Habit detail, Rewards, Progress, Settings, a Zulu or Setswana screen |

## Policy forms
- **Privacy policy URL:** publish `docs/privacy-policy.html` with GitHub Pages (Settings ▸ Pages ▸ `/docs`) and set the URL in `local.properties` (`PRIVACY_URL`) and in Play Console.
- **Data safety:** collects *name, e-mail, user IDs, photos (optional), app activity (habit data), device or other IDs (FCM token)*; data is **encrypted in transit**; users **can request deletion** (in-app account deletion); data is not sold or shared for advertising.
- **Content rating:** questionnaire → expected *Everyone*.
- **Target audience:** 13+ / general. **Ads:** none.
- **Permissions:** `INTERNET`, `ACCESS_NETWORK_STATE`, `POST_NOTIFICATIONS` (explain: reminders and streak alerts).
- **Account deletion:** in-app (Settings ▸ Account & security ▸ Delete account); provide the privacy-policy URL as the web link.

## Console steps
1. ☐ Create the developer account (one-time fee, identity verification can take days).
2. ☐ Create the app, complete the store listing and the policy forms above.
3. ☐ Upload the AAB to **Internal testing** (no review wait) and take a screenshot of the release page for your submission.
4. ☐ Add the Play App Signing certificate's SHA-1/SHA-256 to Firebase so Google sign-in works for Play-installed builds.
5. ☐ Personal accounts created recently may need a closed test with testers before production: check Play Console's current requirement.
