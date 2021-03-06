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
    with(Dependencies.Android) {
        implementation(androidxCore)
        implementation(androidxAppCompat)
        implementation(material)
        implementation(constraintLayout)
        implementation(lifecycleLivedata)
        implementation(lifecycleViewModel)
        implementation(okHttp)
        implementation(androidxAppCompat)
        implementation(recyclerView)
        implementation(preference)
        implementation(vectordrawable)
        implementation(vcard)
        implementation(annotation)
        implementation(androidxBiometrics)
        implementation(gmsPlayServicesAnalytics)
        implementation(gmsPlayServicesAdsIdentifier)

        // ROOM
        implementation(roomRuntime)
        implementation(room)

        // HILT
        implementation(hilt)
        implementation(hiltAndroid)

        // Navigation component
        implementation(activityCompose)
        implementation(composeUiTooling)
        implementation(navRuntime)
        implementation(composeUi)
        implementation(composeMaterial)

        // maybe the other navigation components can be removed because of compose
        // Compose Stuff
        implementation(navCompose)
        implementation(coordinatorlayout)
        implementation(viewpager2)

        // Billing
        implementation(billing)
    }

    with(Dependencies.NavController) {
        implementation(navigationFragment)
        implementation(navigationUi)
    }

    with(Dependencies.Firebase) {
        // Import the BoM for the Firebase platform
        implementation(platform(firebaseBom))
        // Declare the dependencies for the Crashlytics and Analytics libraries
        // When using the BoM, you don"t specify versions in Firebase library dependencies
        implementation(firebaseAnalytics)
        implementation(firebaseCrashlytics)
    }

    with(Dependencies.Libs) {
//        implementation(glide)
    }

    with(Dependencies.TestLibs) {
        testImplementation(junit)
        androidTestImplementation(junitExt)
        androidTestImplementation(espressoCore)
    }

    // TODO properly implement
    implementation("com.facebook.android:audience-network-sdk:6.11.0")
    implementation("io.coil-kt:coil:2.1.0")

    // TODO properly implement

    // TODO Libraries to get rid of eventually:
    // for "ExpandableLayout"
    implementation("com.github.traex.expandablelayout:library:1.3")
    implementation("com.h6ah4i.android.widget.verticalseekbar:verticalseekbar:1.0.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.rengwuxian.materialedittext:library:2.1.4") // MaterialEditText, app:met_baseColor="@color/primary_text" etc.
    implementation("com.github.HITGIF:TextFieldBoxes:1.4.5") // app:maxCharacters="50"
    implementation("org.greenrobot:eventbus:3.3.1")
    implementation("com.zsoltsafrany:needle:1.0.0")
    implementation("com.github.ksoichiro:android-observablescrollview:1.6.0")
    implementation("com.wdullaer:materialdatetimepicker:4.2.3")
    implementation("com.github.MikeOrtiz:TouchImageView:3.1.1")
}
