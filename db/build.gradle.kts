plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }

    buildTypes {
        release {
            isMinifyEnabled = true
        }
    }
}

dependencies {
    implementation(projects.common)

    ksp(libs.room.compiler)

    implementation(libs.koin.android)

    api(libs.room.runtime)
    implementation(libs.room.ktx)
}