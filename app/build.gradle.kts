plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.diffplug.spotless")
}

android {

    signingConfigs {
        getByName("debug") {
            storeFile = file("/Users/dirzaaulia/keystore/keystore.jks")
            storePassword = "stravinsky9"
            keyAlias = "keydirza"
            keyPassword = "stravinsky9"
        }
        create("release") {
            storeFile = file("/Users/dirzaaulia/keystore/keystore.jks")
            storePassword = "stravinsky9"
            keyAlias = "keydirza"
            keyPassword = "stravinsky9"
        }
    }

    namespace = AppConfig.namespace
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.namespace
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AppConfig.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain {
            this.languageVersion.set(JavaLanguageVersion.of(11))
        }
    }

    kotlinOptions {
        jvmTarget = "11"

        // Enable Coroutines and Flow APIs
        freeCompilerArgs = freeCompilerArgs +
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi" +
            "-opt-in=kotlinx.coroutines.FlowPreview" +
            "-opt-in=com.google.accompanist.pager.ExperimentalPagerApi" +
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi" +
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi" +
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi" +
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
    }

    buildFeatures {
        compose = true
        // Disable unused AGP features
        buildConfig = false
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Version.composeCompiler
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Implementation
    implementation(Dependencies.Accompanist.implementation)
    implementation(Dependencies.AndroidX.implementation)
    implementation(Dependencies.AndroidX.Compose.implementation)
    implementation(Dependencies.AndroidX.Lifecycle.implementation)
    implementation(Dependencies.Coil.implementation)
    implementation(Dependencies.Coroutines.implementation)
    implementation(Dependencies.Hilt.implementation)
    implementation(Dependencies.Kotlin.implementation)
    implementation(Dependencies.Material.implementation)
    implementation(Dependencies.Other.implementation)
    implementation(Dependencies.Paging.implementation)
    implementation(Dependencies.SquareUp.implementation)

    // Kapt
    kapt(Dependencies.Hilt.kapt)

    // Debug Implementation
    debugImplementation(Dependencies.Chucker.debugImplementation)

    // Release Implementation
    releaseImplementation(Dependencies.Chucker.releaseImplementation)

    // Android Test Implementation
    androidTestImplementation(Dependencies.AndroidX.Test.androidTestImplementation)
    androidTestImplementation(Dependencies.JUnit.androidTestImplementation)
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktlint()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
