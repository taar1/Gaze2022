package net.gazeapp

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import net.gazeapp.utilities.TrackingUtils

/**
 * Created by erbsland on 27.01.2016.
 */
@HiltAndroidApp
class GazeApplication : Application(), ActivityLifecycleCallbacks {

    private var firebaseAnalytics: FirebaseAnalytics? = null
    private var connectivityManager: ConnectivityManager? = null
    private var isActivityVisible = false

    init {
        Log.d(TAG, "GAZE application has been started!")
        registerActivityLifecycleCallbacks(this)
    }

    companion object {
        private const val TAG = "GazeApplication"

        lateinit var appContext: Context
        private var app: GazeApplication? = null

        fun getApp(): GazeApplication? {
            return app
        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        app = this

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