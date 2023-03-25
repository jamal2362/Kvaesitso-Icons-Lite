import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.sergei-lapin.napt")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
}

val buildCommit = providers.exec {
    commandLine("git", "rev-parse", "--short=7", "HEAD")
}.standardOutput.asText.get().trim()

val ciBuild = System.getenv("CI") == "true"
val ciRef = System.getenv("GITHUB_REF").orEmpty()
val ciRunNumber = System.getenv("GITHUB_RUN_NUMBER").orEmpty()
val isReleaseBuild = ciBuild && ciRef == "main"
val devReleaseName = if (ciBuild) "(Build #$ciRunNumber)" else "($buildCommit)"

val version = "1.7.2"
val versionDisplayName = "$version ${if (isReleaseBuild) "" else devReleaseName}"

android {
    compileSdk = 33
    namespace = "de.kvaesitso.icons"

    defaultConfig {
        applicationId = "de.kvaesitso.icons"
        minSdk = 26
        targetSdk = 33
        versionCode = 12
        versionName = versionDisplayName
        resourceConfigurations.addAll(listOf("en", "cs", "de", "es", "fr", "hi", "ja", "nl", "pl", "ru", "sk", "tr"))
    }

    signingConfigs {
        create("gh-actions") {
            storeFile = file("${System.getenv("RUNNER_TEMP")}/keystore/keystore.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            enableV1Signing = true
            enableV2Signing = true
            enableV3Signing = true
        }
    } 

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.findByName("gh-actions")
            proguardFiles("proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.findByName("gh-actions")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    applicationVariants.all {
        outputs.all {
            (this as? ApkVariantOutputImpl)?.outputFileName =
                "Kvaesitso-Icons ${versionName}.apk"
        }
    }
}

hilt.enableAggregatingTask = false

dependencies {
    val lifecycleVersion = "2.6.1"
    val accompanistVersion = "0.30.0"
    val hiltVersion = "2.45"
    val retrofitVersion = "2.9.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3:1.1.0-beta01")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("com.google.accompanist:accompanist-insets:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-navigation-animation:$accompanistVersion")
    implementation("io.github.fornewid:material-motion-compose-core:0.11.1")
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    annotationProcessor("com.google.dagger:hilt-android-compiler:$hiltVersion")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.github.LawnchairLauncher:oss-notices:1.0.2")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
}
