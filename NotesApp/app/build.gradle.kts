plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.ripalnakiya.notesapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ripalnakiya.notesapp"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    // Room Database Dependencies
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.savedstate)     // Saved state module for ViewModel
    annotationProcessor(libs.lifecycle.compiler)

    // LiveData
    implementation(libs.lifecycle.livedata)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}