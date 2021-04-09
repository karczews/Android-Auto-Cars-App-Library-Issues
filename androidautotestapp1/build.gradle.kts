plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}
val kotlin_version by extra("1.4.10")
android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "com.github.karczews.androidauto.automotive.testapp1"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation("androidx.core:core:1.5.0-alpha01")
    implementation("androidx.car.app:app:1.0.0-rc01")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.appcompat:appcompat:1.2.0")

    implementation("androidx.activity:activity-ktx:1.2.2")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.3.1")
}
