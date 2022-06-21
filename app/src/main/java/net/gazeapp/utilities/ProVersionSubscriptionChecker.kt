package net.gazeapp.utilities

import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.view.View
import needle.Needle
import net.gazeapp.R
import net.gazeapp.billing.util.IabHelper
import net.gazeapp.billing.util.IabHelper.*
import net.gazeapp.billing.util.Purchase
import net.gazeapp.event.CheckSharedPreferencesEvent
import net.gazeapp.helpers.Const
import net.gazeapp.helpers.SnackBarType
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ProVersionSubscriptionChecker(val activity: Activity, val view: View?) : Service() {

    @Inject
    lateinit var tools: GazeTools

    // Does the user have an active subscription to the infinite gas plan?
    var mSubscribedToProVersion = false

    // Will the subscription auto-renew?
    var mAutoRenewEnabled = false

    // Tracks the currently owned Pro version SKU, and the options in the Manage dialog
    var mProVersionSku = ""
    var mGazeProMonthly = false
    var mGazeProAnnually = false

    // The helper object
    var mHelper: IabHelper? = null


    companion object {
        private const val TAG = "ProVersionSubscriptionC"
    }

    fun checkForProVersionSubscription() {
        /* base64EncodedPublicKey should be YOUR APPLICATION'S PUBLIC KEY
         * (that you got from the Google Play developer console). This is not your
         * developer public key, it's the *app-specific* public key.
         *
         * Instead of just storing the entire literal string here embedded in the
         * program,  construct the key at runtime from pieces or
         * use bit manipulation (for example, XOR with some other string) to hide
         * the actual key.  The key itself is not secret information, but we don't
         * want to make it easy for an attacker to replace the public key with one
         * of their own and then fake messages from the server.
         */
        val base64EncodedPublicKey = Const.BASE64_ENCODED_PUBLIC_KEY

        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.")
        mHelper = IabHelper(activity, base64EncodedPublicKey)

        // enable debug logging (for a production application, you should set this to false).
        mHelper!!.enableDebugLogging(SpecificValues.ENABLE_DEBUG_LOGGING)

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.")
        mHelper!!.startSetup(OnIabSetupFinishedListener { result ->
            Log.d(TAG, "Setup finished.")
            if (!result.isSuccess) {
                // Oh noes, there was a problem.
                complain("Problem setting up in-app billing: $result")
                return@OnIabSetupFinishedListener
            }

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return@OnIabSetupFinishedListener

            // IAB is fully set up. Now, let's get an inventory of stuff we own.
            Log.d(TAG, "Setup successful. Querying inventory.")
            try {
                mHelper!!.queryInventoryAsync(mGotInventoryListener)
            } catch (e: IabAsyncInProgressException) {
                complain("Error querying inventory. Another async operation in progress.")
            }
        })
    }

    // Listener that's called when we finish querying the items and subscriptions we own
    private var mGotInventoryListener = QueryInventoryFinishedListener { result, inventory ->
        Log.d(TAG, "Query inventory finished.")

        // Have we been disposed of in the meantime? If so, quit.
        if (mHelper == null) return@QueryInventoryFinishedListener

        // Is it a failure?
        if (result.isFailure) {
            complain("Failed to query inventory: $result")
            tools.showMaterialSnackBar(
                view,
                activity.getString(R.string.error_verifying_subscription),
                SnackBarType.WARNING
            )
            return@QueryInventoryFinishedListener
        }
        Log.d(TAG, "Query inventory was successful.")

        /*
        * Check for items we own. Notice that for each purchase, we check
        * the developer payload to see if it's correct! See
        * verifyDeveloperPayload().
        */
        val gazeProMonthly = inventory.getPurchase(Const.GAZE_PRO_MONTHLY_SKU)
        val gazeProAnnually = inventory.getPurchase(Const.GAZE_PRO_ANNUALLY_SKU)
        if (gazeProMonthly != null && gazeProMonthly.isAutoRenewing) {
            Log.d(TAG, "Purchase gazeProMonthly")
            Log.d(TAG, "Purchase gazeProMonthly 1: " + gazeProMonthly.developerPayload)
            Log.d(TAG, "Purchase gazeProMonthly 2: " + gazeProMonthly.itemType)
            Log.d(TAG, "Purchase gazeProMonthly 3: " + gazeProMonthly.orderId)
            Log.d(TAG, "Purchase gazeProMonthly 4: " + gazeProMonthly.originalJson)
            Log.d(TAG, "Purchase gazeProMonthly 5: " + gazeProMonthly.packageName)
            Log.d(TAG, "Purchase gazeProMonthly 5: " + gazeProMonthly.purchaseState)
            Log.d(TAG, "Purchase gazeProMonthly 6: " + gazeProMonthly.purchaseTime)
            Log.d(TAG, "Purchase gazeProMonthly 7: " + gazeProMonthly.signature)
            Log.d(TAG, "Purchase gazeProMonthly 8: " + gazeProMonthly.sku)
            Log.d(TAG, "Purchase gazeProMonthly 9: " + gazeProMonthly.token)
            mProVersionSku = Const.GAZE_PRO_MONTHLY_SKU
            mGazeProMonthly = true
            mAutoRenewEnabled = true

            // SAVING TO SHAREDPREFERENCES IS NOT VERY SAFE. COME UP WITH A MORE SECURE WAY.
            // Save this to SharedPreferences
            tools.setProUser(true)
            tools.setProUserSku(gazeProMonthly.sku)
            tools.setProUserSince(gazeProMonthly.purchaseTime)
            tools.setProUserJson(gazeProMonthly.originalJson)
        } else if (gazeProAnnually != null && gazeProAnnually.isAutoRenewing) {
            Log.d(TAG, "Purchase gazeProAnnually")
            Log.d(TAG, "Purchase gazeProAnnually 1: " + gazeProAnnually.developerPayload)
            Log.d(TAG, "Purchase gazeProAnnually 2: " + gazeProAnnually.itemType)
            Log.d(TAG, "Purchase gazeProAnnually 3: " + gazeProAnnually.orderId)
            Log.d(TAG, "Purchase gazeProAnnually 4: " + gazeProAnnually.originalJson)
            Log.d(TAG, "Purchase gazeProAnnually 5: " + gazeProAnnually.packageName)
            Log.d(TAG, "Purchase gazeProAnnually 5: " + gazeProAnnually.purchaseState)
            Log.d(TAG, "Purchase gazeProAnnually 6: " + gazeProAnnually.purchaseTime)
            Log.d(TAG, "Purchase gazeProAnnually 7: " + gazeProAnnually.signature)
            Log.d(TAG, "Purchase gazeProAnnually 8: " + gazeProAnnually.sku)
            Log.d(TAG, "Purchase gazeProAnnually 9: " + gazeProAnnually.token)
            mProVersionSku = Const.GAZE_PRO_ANNUALLY_SKU
            mGazeProAnnually = true
            mAutoRenewEnabled = true

            // SAVING TO SHAREDPREFERENCES IS NOT VERY SAFE. COME UP WITH A MORE SECURE WAY.
            // Save this to SharedPreferences
            tools.setProUser(true)
            tools.setProUserSku(gazeProAnnually.sku)
            tools.setProUserSince(gazeProAnnually.purchaseTime)
            tools.setProUserJson(gazeProAnnually.originalJson)
        } else {
            mProVersionSku = ""
            mAutoRenewEnabled = false

            // SAVING TO SHAREDPREFERENCES IS NOT VERY SAFE. COME UP WITH A MORE SECURE WAY.
            // Save this to SharedPreferences
            tools.removeProUser()
            tools.removeProUserSku()
            tools.removeProUserSince()
            tools.removeProUserJson()
            tools.removeAdsEnabled()
        }
        Log.d(TAG, "MYPREFS isProUser: " + tools.isProUser())
        Log.d(TAG, "MYPREFS proUserSku: " + tools.proUserSku)
        Log.d(TAG, "MYPREFS proUserSince: " + tools.proUserSince)
        Log.d(TAG, "MYPREFS proUserJson: " + tools.proUserJson)
        Log.d(TAG, "MYPREFS isAdsEnabled: " + tools.isAdsEnabled)
        EventBus.getDefault().post(CheckSharedPreferencesEvent())

        // The user is subscribed if either subscription exists, even if neither is auto renewing
        mSubscribedToProVersion = (gazeProMonthly != null && verifyDeveloperPayload(gazeProMonthly)
                || gazeProAnnually != null && verifyDeveloperPayload(gazeProAnnually))
        Log.d(
            TAG,
            "User " + (if (mSubscribedToProVersion) "HAS" else "DOES NOT HAVE") + " PRO subscription."
        )
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    fun verifyDeveloperPayload(p: Purchase): Boolean {
        val payload = p.developerPayload
        Log.d(TAG, "verifyDeveloperPayload: $payload")

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */
        return true
    }

    fun complain(message: String) {
        Log.e(TAG, "**** Gaze Error: $message")
        alert("Error: $message")
    }

    fun alert(message: String) {
        Needle.onMainThread().execute {
            val bld = AlertDialog.Builder(activity)
            bld.setMessage(message)
            bld.setNeutralButton("OK", null)
            Log.d(TAG, "Showing alert dialog: $message")
            bld.create().show()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}