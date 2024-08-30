import org.gradle.kotlin.dsl.kotlin

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.bella.camera_w7"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bella.camera_w7"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // ktx
    implementation("androidx.activity:activity-ktx:1.9.0")
    implementation("androidx.fragment:fragment-ktx:1.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")

    // cameraX
    implementation("androidx.camera:camera-core:1.3.3")
    implementation("androidx.camera:camera-camera2:1.3.3")
    implementation("androidx.camera:camera-lifecycle:1.3.3")

    implementation("androidx.camera:camera-view:1.3.3")
    implementation("androidx.camera:camera-extensions:1.3.3")

    // room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    // qrcode generator
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
    implementation("androidx.multidex:multidex:2.0.1")

    // MLKit
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-android-compiler:2.46")

    // timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // lottie
    implementation("com.airbnb.android:lottie:6.4.0")
}