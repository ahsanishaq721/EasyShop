plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.firebase)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.example.ecommerceapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.ecommerceapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.navigation)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    // live data
    implementation(libs.androidx.compose.runtime.livedata)
    debugImplementation(libs.androidx.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // retrofit
    debugImplementation(libs.androidx.retrofit2.retrofit)
    debugImplementation(libs.androidx.retrofit2.converter.gson)
    debugImplementation(libs.androidx.google.gson)
    // room
    debugImplementation(libs.androidx.room)
    debugImplementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // hilt
    debugImplementation(libs.hilt.android)
    debugImplementation(libs.hilt.android.compiler)
    debugImplementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)
    // coroutines
    implementation(libs.kotlinx.coroutines.android)
}