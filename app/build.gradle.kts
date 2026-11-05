import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
}

// google-services.json is added by each developer (it is git-ignored). CI creates a placeholder when the secret is absent.
if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
}

val localProps = Properties().apply {
    val f = rootProject.file("local.properties")
    if (f.exists()) f.inputStream().use { load(it) }
}

/** Reads a setting from -P flags, local.properties or the environment (in that order). */
fun setting(name: String, default: String): String =
    (project.findProperty(name) as String?) ?: localProps.getProperty(name) ?: System.getenv(name) ?: default

android {
    namespace = "com.streakly.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.streakly.app"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Hosted REST API (must end with a slash). Override in local.properties: API_BASE_URL=https://your-api.onrender.com/
        buildConfigField("String", "API_BASE_URL", "\"${setting("API_BASE_URL", "https://streakly-api.onrender.com/")}\"")
        buildConfigField("String", "PRIVACY_URL", "\"${setting("PRIVACY_URL", "https://mzamon.github.io/streakly/privacy-policy.html")}\"")
        buildConfigField("String", "SUPPORT_EMAIL", "\"${setting("SUPPORT_EMAIL", "support@streakly.app")}\"")
    }

    signingConfigs {
        create("release") {
            val path = System.getenv("KEYSTORE_PATH") ?: localProps.getProperty("KEYSTORE_PATH")
            if (path != null && file(path).exists()) {
                storeFile = file(path)
                storePassword = System.getenv("KEYSTORE_PASSWORD") ?: localProps.getProperty("KEYSTORE_PASSWORD")
                keyAlias = System.getenv("KEY_ALIAS") ?: localProps.getProperty("KEY_ALIAS")
                keyPassword = System.getenv("KEY_PASSWORD") ?: localProps.getProperty("KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Only sign when a keystore was supplied; otherwise Gradle produces an unsigned release APK.
            if (signingConfigs.getByName("release").storeFile != null) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.viewpager2)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    // Room (offline-first local database)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // REST API client
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Background work: sync + reminders
    implementation(libs.androidx.work.runtime.ktx)


    // Firebase: Authentication, Cloud Messaging (push), Storage (blob storage)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.firebase.storage.ktx)

    // Google single sign-on through Credential Manager
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.googleid)

    // Image loading (profile photo)
    implementation(libs.glide)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
