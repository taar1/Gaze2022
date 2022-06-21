package net.gazeapp.utilities

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Helper class for tracking tasks
 */
class TrackingUtils(context: Context) {

    private var firebaseAnalytics: FirebaseAnalytics? = null
    private var lastPageID: String? = null

    companion object {
        lateinit var INSTANCE: TrackingUtils
        private val initialized = AtomicBoolean()

        fun getInstance(context: Context): TrackingUtils {
            if (initialized.getAndSet(true)) {
                INSTANCE = TrackingUtils(context)
            }
            return INSTANCE
        }
    }

    init {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }


    /**
     * Helper function to reset the tracking controller when the app goes into background
     */
    fun reset() {
        lastPageID = null
    }

    /**
     * Track a page view
     *
     * @param pageID   the name / id of the page
     * @param pageType the type of the page
     */
    fun trackPageView(pageID: String, pageType: String?) {
        if (lastPageID != null && lastPageID == pageID) {
            return
        }
        lastPageID = pageID
        trackGoogleAnalyticsPageView(pageID)
        trackFirebasePageView(pageID, pageType, null)
    }

    /**
     * Track a page view without calling netmetrix
     *
     * @param pageID   the name / id of the page
     * @param pageType the type of the page
     */
    fun trackPageViewWithoutNetmetrix(pageID: String, pageType: String?) {
        if (lastPageID != null && lastPageID == pageID) {
            return
        }
        lastPageID = pageID
        trackGoogleAnalyticsPageView(pageID)
        trackFirebasePageView(pageID, pageType, null)
    }

    /**
     * Track a page view
     *
     * @param pageID     the name / id of the page
     * @param pageType   the type of the page
     * @param entityName the name of the entity on the page
     */
    fun trackPageView(pageID: String, pageType: String?, entityName: String?) {
        if (lastPageID != null && lastPageID == pageID) {
            return
        }
        lastPageID = pageID
        trackGoogleAnalyticsPageView(pageID)
        trackFirebasePageView(pageID, pageType, entityName)
    }

    /**
     * Track a page view without calling netmetrix
     *
     * @param pageID     the name / id of the page
     * @param pageType   the type of the page
     * @param entityName the name of the entity on the page
     */
    fun trackPageViewWithoutNetmetrix(pageID: String, pageType: String?, entityName: String?) {
        if (lastPageID != null && lastPageID == pageID) {
            return
        }
        lastPageID = pageID
        trackGoogleAnalyticsPageView(pageID)
        trackFirebasePageView(pageID, pageType, entityName)
    }

    /**
     * Track a page view in Firebase
     *
     * @param pageID     the name / id of the page
     * @param pageType   the type of the page
     * @param entityName the name of the entity on the page
     */
    private fun trackFirebasePageView(pageID: String?, pageType: String?, entityName: String?) {
        // Log the content view
        val bundle = Bundle()
        if (pageID != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, pageID)
        }
        if (pageType != null) {
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, pageType)
        }
        if (entityName != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, entityName)
        }
        if (pageID != null && pageType != null && entityName != null) {
            firebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
        }
    }

    /**
     * Track a page view in Google Analytics
     *
     * @param pageID the name / id of the page
     */
    private fun trackGoogleAnalyticsPageView(pageID: String) {
        // TODO FIXME google analytics wieder einbauen...
        // TODO FIXME google analytics wieder einbauen...
        // TODO FIXME google analytics wieder einbauen...
        // TODO FIXME google analytics wieder einbauen...
        // TODO FIXME google analytics wieder einbauen...
        // TODO FIXME google analytics wieder einbauen...
        // Log the content view
//        Tracker tracker = GazeApplication.getGoogleAnalyticsTracker();
//        tracker.setScreenName(pageID);
//        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


}