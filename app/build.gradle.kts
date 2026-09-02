import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")

    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use {
            load(it)
        }
    }
}

val geminiApiKey =
    localProperties.getProperty("GEMINI_API_KEY") ?: ""

android {
    namespace = "com.example.aistudybuddy"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.aistudybuddy"
        minSdk = 27
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        // =========================================================
        // SUPABASE
        // =========================================================

        buildConfigField(
            "String",
            "SUPABASE_URL",
            "\"https://yiczlrqoztouvryezwyw.supabase.co\""
        )

        buildConfigField(
            "String",
            "SUPABASE_PUBLISHABLE_KEY",
            "\"sb_publishable_XdmfebO5C3DB2UHHunPATQ_K_TqG0_w\""
        )

        // =========================================================
        // GEMINI
        // =========================================================

        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"$geminiApiKey\""
        )

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.VERSION_11

        targetCompatibility =
            JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    // =============================================================
    // SUPABASE
    // =============================================================

    implementation(
        platform(libs.supabase.bom)
    )

    implementation(
        libs.supabase.auth
    )

    implementation(
        libs.ktor.client.android
    )

    // =============================================================
    // COMPOSE MATERIAL ICONS
    // =============================================================

    implementation(
        libs.androidx.compose.material.icons.extended
    )

    // =============================================================
    // COMPOSE
    // =============================================================

    implementation(
        platform(libs.androidx.compose.bom)
    )

    implementation(
        libs.androidx.activity.compose
    )

    implementation(
        libs.androidx.compose.material3
    )

    implementation(
        libs.androidx.compose.ui
    )

    implementation(
        libs.androidx.compose.ui.graphics
    )

    implementation(
        libs.androidx.compose.ui.tooling.preview
    )

    // =============================================================
    // ANDROID CORE
    // =============================================================

    implementation(
        libs.androidx.core.ktx
    )

    // =============================================================
    // LIFECYCLE / VIEWMODEL
    // =============================================================

    implementation(
        libs.androidx.lifecycle.runtime.ktx
    )

    implementation(
        libs.androidx.lifecycle.viewmodel.ktx
    )

    implementation(
        libs.androidx.lifecycle.viewmodel.compose
    )

    implementation(
        libs.androidx.lifecycle.runtime.compose
    )

    // =============================================================
    // NAVIGATION
    // =============================================================

    implementation(
        libs.androidx.navigation.compose
    )

    // =============================================================
    // TESTING
    // =============================================================

    testImplementation(
        libs.junit
    )

    androidTestImplementation(
        platform(libs.androidx.compose.bom)
    )

    androidTestImplementation(
        libs.androidx.compose.ui.test.junit4
    )

    androidTestImplementation(
        libs.androidx.espresso.core
    )

    androidTestImplementation(
        libs.androidx.junit
    )

    debugImplementation(
        libs.androidx.compose.ui.test.manifest
    )

    debugImplementation(
        libs.androidx.compose.ui.tooling
    )
}