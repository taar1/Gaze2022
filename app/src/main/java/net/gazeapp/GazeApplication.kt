package net.gazeapp

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import net.gazeapp.utilities.TrackingUtils

@HiltAndroidApp
class GazeApplication : Application(), ActivityLifecycleCallbacks {

    private var firebaseAnalytics: FirebaseAnalytics? = null
    private var connectivityManager: ConnectivityManager? = null
    private var isActivityVisible = false

// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)
// TODO FIXME an den Application Context rankommen (in GazeApplication)

    init {
//        instance = this
//        appContext = applicationContext
        Log.d(TAG, "GAZE application has been started!")
        registerActivityLifecycleCallbacks(this)
    }

    companion object {
        private const val TAG = "GazeApplication"

//        lateinit var appContext: Context
//
//        private var instance: GazeApplication? = null

//        fun applicationContext(): Context {
//            return instance!!.applicationContext
//        }

    }

    override fun onCreate() {
        super.onCreate()

//        appContext = applicationContext

        // Set Firebase logging
        firebaseAnalytics = FirebaseAnalytics.getInstance(applicationContext)

        // Set the connectivity manager
        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity.javaClass == MainActivity::class.java) {
            isActivityVisible = true
        }
    }

    /**
     * [Application.ActivityLifecycleCallbacks.onActivityStarted]
     */
    override fun onActivityStarted(activity: Activity) {
        if (activity.javaClass == MainActivity::class.java) {
            firebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity.javaClass == MainActivity::class.java) {
            isActivityVisible = true
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity.javaClass == MainActivity::class.java) {
            isActivityVisible = false
            TrackingUtils.getInstance(applicationContext).reset()
        }
    }

    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}


}