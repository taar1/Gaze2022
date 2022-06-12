object Dependencies {

    object Android {
        const val buildTools =
            "com.android.tools.build:gradle:${Versions.Android.androidBuildTools}"
        const val googleServices =
            "com.google.gms:google-services:${Versions.Android.googleServices}"
        const val kotlinGradlePlugin =
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Android.kotlin}"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Android.hilt}"
        const val crashlyticsGradle =
            "com.google.firebase:firebase-crashlytics-gradle:${Versions.Android.crashlyticsGradle}"
        const val jsonSerialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Android.serialization}"
        const val serializationPlugin =
            "org.jetbrains.kotlin:kotlin-serialization:${Versions.Android.kotlin}"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Android.kotlinCoroutines}"
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Android.kotlinCoroutines}"
        const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Android.kotlinCoroutines}"
        const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.Android.appCompat}"
        const val androidxBiometrics =
            "androidx.biometric:biometric:${Versions.Android.androidxBiometrics}"
        const val androidxCore = "androidx.core:core:${Versions.Android.androidxCore}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.Android.swipeRefreshLayout}"
        const val fragment = "androidx.fragment:fragment:${Versions.Android.fragment}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.Android.fragment}"
        const val lifecycleProcess =
            "androidx.lifecycle:lifecycle-process:${Versions.Android.androidxLifecycle}"
        const val lifecycleLivedata =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Android.lifecycleLivedata}"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Android.lifecycleViewModel}"
        const val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.Android.okHttp}"
        const val material = "com.google.android.material:material:${Versions.Android.material}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.Android.recyclerView}"
        const val room = "androidx.room:room-ktx:${Versions.Android.room}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.Android.room}"
        const val preference = "androidx.preference:preference-ktx:${Versions.Android.preference}"
        const val vectordrawable =
            "androidx.vectordrawable:vectordrawable:${Versions.Android.vectordrawable}"
        const val constraintlayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Android.constraintLayout}"
        const val vcard = "com.googlecode.ez-vcard:ez-vcard:${Versions.Android.vcard}"
        const val annotation = "androidx.annotation:annotation:${Versions.Android.annotation}"
        const val gmsPlayServicesAnalytics =
            "com.google.android.gms:play-services-analytics:${Versions.Android.gmsPlayServices}"
        const val gmsPlayServicesAdsIdentifier =
            "com.google.android.gms:play-services-ads-identifier:${Versions.Android.gmsPlayServices}"


        const val activityCompose =
            "androidx.activity:activity-compose:${Versions.Android.activityCompose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.Android.composeUi}"
        const val navRuntime =
            "androidx.navigation:navigation-runtime-ktx:${Versions.Android.navRuntime}"
        const val composeUi = "androidx.compose.ui:ui:${Versions.Android.composeUi}"
        const val navCompose =
            "androidx.navigation:navigation-compose:${Versions.Android.navCompose}"
        const val coordinatorlayout =
            "androidx.coordinatorlayout:coordinatorlayout:${Versions.Android.coordinatorlayout}"
        const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.Android.viewpager2}"


    }

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.Android.firebaseBom}"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging-ktx"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    }

    object NavController {
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Android.navController}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.Android.navController}"
        const val navigationTesting =
            "androidx.navigation:navigation-testing:${Versions.Android.navController}"
        const val navSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Android.navController}"
    }

    object Libs {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Android.kotlin}"
    }

    object TestLibs {
        const val junit = "junit:junit:${Versions.Android.junit}"
        const val junitExt = "androidx.test.ext:junit:${Versions.Android.junitExt}"
        const val espressoCore =
            "androidx.test.espresso:espresso-core:${Versions.Android.espressoCore}"


    }
}

