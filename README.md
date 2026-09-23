# Streakly

> **OPSC6312 Part 2 — App Prototype Development**
> **Authors:** Tumisho Kokhutja (ST10446195), Mzamo Richmond Ndlovu (ST10455453), Lesego Ayanda Mathe (ST10440650)

Streakly is a Kotlin Android habit-tracking application. It is designed around three demonstrable user-defined features: habit scheduling and logging, streak analytics with gamification, and reminders/settings with offline data export. The app stores data locally first and synchronises habits and completion logs through a custom REST API when a connection is available.
##YouTube Link: https://youtu.be/l8ESNy6DJ-c
## Rubric coverage

| Part 2 criterion | Streakly implementation | Evidence to capture |
|---|---|---|
| App runs on a mobile device | Debug APK, launcher splash screen, bottom navigation, and local Room database | Launch the APK on an Android emulator and navigate through all four tabs |
| Sign in | Registration and login with validation, duplicate-email protection, PBKDF2-HMAC-SHA256 password hashing, and encrypted session preferences | Register, log out, log in again, and show an invalid-password error |
| Settings menu | Personal details, password change, notification toggle, theme, language, manual sync, CSV export, local-data deletion, account deletion, and About | Open every settings row and demonstrate one successful change |
| Creation/use of REST API | `server/server.js` exposes `/health` and `/sync` using JSON persistence | Run the server, open `/health`, and show the terminal request/result |
| REST API integration | Retrofit calls `/sync`; pending Room habits/logs are pushed and server data is pulled; WorkManager retries when offline | Create a habit offline, start the API, use **Sync now**, then show the server JSON and updated sync time |
| User-defined feature 1 | Habit CRUD with Daily, Specific Days, and Interval schedules plus completion notes | Create, edit, complete, inspect, and delete a habit |
| User-defined feature 2 | Calendar history, current/best streaks, completion rate, streak-risk notification, and monthly heat-map | Complete habits on multiple dates and show Calendar and statistics |
| User-defined feature 3 | Points, levels, badges, custom rewards, redemption history, and insufficient-points handling | Earn points, open Rewards, redeem a reward, and show the balance/history |
| User interface | Consistent Material layouts, bottom navigation, empty states, confirmation dialogs, dark/light themes, and translated strings | Capture clean screenshots of Welcome, Home, Calendar, Rewards, Profile, Settings, and the main dialogs |
| GitHub, README, automated testing | This repository contains the source, setup instructions, multiple commits, and 12 unit tests for streak and gamification logic | Show the GitHub history, this README, and a successful `testDebugUnitTest` run |
| Demonstration video | A narrated walkthrough of every criterion above | Record the sequence in the checklist below; add the final link before submission |

## Features

- Local registration, login, and password reset with PBKDF2-HMAC-SHA256 (65,536 iterations, 256-bit key) and per-user salt.
- Habit create, edit, delete, daily completion, notes, reminders, and three schedule types.
- Calendar heat-map with completion history, current streak, best streak, and completion rate.
- Points, levels, badges, rewards, redemption history, and insufficient-balance validation.
- Profile and Settings screens with notification scheduling, theme/language selection, password changes, CSV export, data deletion, and logout.
- Room offline-first storage with periodic WorkManager synchronisation.
- Node.js/Express REST API with `/health` and `/sync` endpoints.
- English, isiZulu, and Setswana string resources.

Password reset is intentionally local because this prototype has no email
delivery service: the user verifies the account email stored on the device and
chooses a new password. Google sign-in is still a labelled placeholder and is
not advertised as an available authentication provider.

## App file tree

This is the maintained application tree. Generated build output, Gradle caches,
`node_modules`, machine-specific SDK paths, and runtime `server/data.json` are
intentionally excluded.

```text
streakly/
├── app/
│   └── src/
│       ├── main/
│       │   ├── java/com/streakly/app/
│       │   │   ├── data/
│       │   │   │   ├── local/          # Room database, DAOs, and entities
│       │   │   │   ├── model/          # Application models
│       │   │   │   ├── remote/         # Retrofit client and DTOs
│       │   │   │   ├── repository/     # Persistence and sync repositories
│       │   │   │   └── session/        # Encrypted session preferences
│       │   │   ├── ui/
│       │   │   │   ├── auth/           # Login, registration, and reset screen
│       │   │   │   ├── habit/          # Habit creation and detail screens
│       │   │   │   ├── main/           # Bottom-navigation host and tabs
│       │   │   │   ├── settings/       # Settings and account actions
│       │   │   │   ├── splash/         # Launcher splash flow
│       │   │   │   └── welcome/        # Welcome screen
│       │   │   ├── utils/              # Streak, gamification, reminders, UI
│       │   │   └── StreaklyApplication.kt
│       │   ├── res/                    # Layouts, drawables, values, and XML
│       │   └── AndroidManifest.xml
│       └── test/                       # JVM tests for streak and gamification
├── server/
│   ├── server.js                       # Express REST API
│   └── package.json
├── .github/workflows/android.yml       # CI test and debug-build workflow
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew / gradlew.bat
└── README.md
```

## Running the app

### Android app

1. Open the repository in Android Studio.
2. Use an Android emulator with API 24 or newer.
3. Start the API first if demonstrating synchronisation:

   ```powershell
   cd server
   npm install
   npm start
   ```

4. Run the `app` configuration. The emulator uses `http://10.0.2.2:3000/`; a physical device requires changing `RetrofitClient.BASE_URL` to the computer's LAN address.
5. For a clean demonstration, register a new account, create at least three habits, complete several entries, and then demonstrate Rewards, Calendar, Settings, and Sync.

### REST API

The server listens on port `3000`.

```powershell
cd server
npm install
npm start
curl http://localhost:3000/health
```

The `/sync` endpoint accepts:

```json
{
  "habits": [],
  "logs": []
}
```

The server creates `server/data.json` on its first successful synchronisation. The JSON file is runtime data and should not contain credentials or secrets.

## Testing and build checks

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
```

The unit tests cover streak calculation, due-date logic, completion rate, points, levels, and badge rules. The GitHub Actions workflow runs the unit tests and debug build for pushes and pull requests to `main`.

If a local Android SDK is missing the Android Gradle Plugin build tools,
Gradle can fail before compilation. Install the SDK platform and build-tools
required by the project in Android Studio, then rerun the commands above. CI
uses Java 17 and runs the same checks on every push and pull request to `main`.

## Screenshot checklist

Capture screenshots at emulator resolution with no unrelated windows visible:

1. Welcome/auth screen.
2. Successful registration and Home screen.
3. Login validation and successful login.
4. Habit creation showing a schedule type.
5. Home with completed habits and streak/level summary.
6. Calendar heat-map and habit detail statistics.
7. Rewards showing points, badges, rewards, and redemption history.
8. Settings showing notification, theme, language, sync, export, and deletion controls.
9. API `/health` response and server data after a sync.
10. Successful automated test output and the GitHub commit history.

## Demonstration video script

Use a short narrated, professional walkthrough:

1. State the application purpose and the three user-defined features.
2. Register, demonstrate validation, log out, and log back in.
3. Create/edit/delete a habit and demonstrate each scheduling option.
4. Complete a habit, open Calendar, and explain current/best streak and completion rate.
5. Open Rewards, explain points/levels/badges, redeem a reward, and show history.
6. Open Settings and demonstrate notifications, theme, language, password change, CSV export, deletion confirmation, and logout.
7. Start the REST server, perform Sync now, show `/health`, and show the synced JSON data.
8. Finish with the GitHub README, automated tests, and build result.

## Architecture

- **UI:** Kotlin, AndroidX, Material components, View Binding, RecyclerView, fragments.
- **Local persistence:** Room (`streakly.db`) and EncryptedSharedPreferences for session settings.
- **Networking:** Retrofit, OkHttp, Gson, and the Node.js/Express server.
- **Background work:** WorkManager for periodic synchronisation; AlarmManager for reminders.
- **Testing:** JUnit unit tests for pure streak and gamification logic.
- **CI:** `.github/workflows/android.yml` runs unit tests and `assembleDebug`.

## AI use

AI-assisted scaffolding was reviewed, integrated, tested, and adapted by the team. The final repository, app behavior, tests, documentation, screenshots, and demonstration video are the team's submission evidence.
