plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    compileSdk = Versions.Apps.compileSdk

    defaultConfig {
        applicationId = "net.gazeapp"
        minSdk = 28
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        release {
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
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }

    packagingOptions {
        packagingOptions {
            jniLibs {
                excludes.add("META-INF/**")
            }
            resources {
                excludes.add("META-INF/DEPENDENCIES")
                excludes.add("META-INF/LICENSE")
                excludes.add("META-INF/LICENSE.txt")
                excludes.add("META-INF/license.txt")
                excludes.add("META-INF/NOTICE")
                excludes.add("META-INF/NOTICE.txt")
                excludes.add("META-INF/notice.txt")
                excludes.add("META-INF/ASL2.0")
                excludes.add("META-INF/LGPL2.1")
                excludes.add("META-INF/proguard/androidx-annotations.pro")
                excludes.add("META-INF/**")
                excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            }
        }
    }


    // TODO FIXME hier weitermachen.
    // Specifies a flavor dimension.
    flavorDimensions.add("myDimension")

    productFlavors {
        create("googlePlayFlavor") {
            dimension = "myDimension"
            versionNameSuffix = "-googlePlay"
        }
        create("amazonFlavor") {
            dimension = "myDimension"
            versionNameSuffix = "-amazon"
        }
        create("gRatedFlavor") {
            dimension = "myDimension"
            versionNameSuffix = "-gRated"
        }
    }

}

dependencies {
    implementation(Dependencies.Android.androidxCore)
    implementation(Dependencies.Android.androidxAppCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.constraintLayout)
    implementation(Dependencies.Android.lifecycleLivedata)
    implementation(Dependencies.Android.lifecycleViewModel)
    implementation(Dependencies.Android.okHttp)
    implementation(Dependencies.NavController.navigationFragment)
    implementation(Dependencies.NavController.navigationUi)

    // Import the BoM for the Firebase platform
    implementation(platform(Dependencies.Firebase.firebaseBom))
    // Declare the dependencies for the Crashlytics and Analytics libraries
    // When using the BoM, you don"t specify versions in Firebase library dependencies
    implementation(Dependencies.Firebase.firebaseAnalytics)
    implementation(Dependencies.Firebase.firebaseCrashlytics)

    implementation(Dependencies.Android.androidxAppCompat)
    implementation(Dependencies.Android.recyclerView)
    implementation(Dependencies.Android.preference)
    implementation(Dependencies.Android.vectordrawable)
    implementation(Dependencies.Android.vcard)
    implementation(Dependencies.Android.annotation)
    implementation(Dependencies.Android.androidxBiometrics)
    implementation(Dependencies.Android.gmsPlayServicesAnalytics)
    implementation(Dependencies.Android.gmsPlayServicesAdsIdentifier)

    // ROOM
    implementation(Dependencies.Android.roomRuntime)
    implementation(Dependencies.Android.room)

    // HILT
    implementation(Dependencies.Android.hilt)
    implementation(Dependencies.Android.hiltAndroid)

    // Navigation component
    implementation(Dependencies.Android.activityCompose)
    implementation(Dependencies.Android.composeUiTooling)
    implementation(Dependencies.Android.navRuntime)
    implementation(Dependencies.Android.composeUi)
    implementation(Dependencies.Android.composeMaterial)

    // maybe the other navigation components can be removed because of compose
    // Compose Stuff
    implementation(Dependencies.Android.navCompose)
    implementation(Dependencies.Android.coordinatorlayout)
    implementation(Dependencies.Android.viewpager2)

    // Testing
    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.junitExt)
    androidTestImplementation(Dependencies.TestLibs.espressoCore)
}