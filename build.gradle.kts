plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
}

buildscript {

    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
        classpath("com.google.gms:google-services:4.3.13")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.1")
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}