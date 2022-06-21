package net.gazeapp.billing

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import net.gazeapp.R
import net.gazeapp.billing.util.IabBroadcastReceiver
import net.gazeapp.billing.util.IabBroadcastReceiver.IabBroadcastListener
import net.gazeapp.billing.util.IabHelper
import net.gazeapp.billing.util.IabHelper.*
import net.gazeapp.billing.util.Purchase
import net.gazeapp.databinding.ActivityUpgradingBinding
import net.gazeapp.event.CheckSharedPreferencesEvent
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.ProVersionSubscriptionChecker
import net.gazeapp.utilities.SpecificValues
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class UpgradingActivity : AppCompatActivity(), IabBroadcastListener,
    DialogInterface.OnClickListener {

    @Inject
    lateinit var tools: GazeTools

    lateinit var outerLayour: ConstraintLayout
    lateinit var toolbar: Toolbar

    //    lateinit var youHaveProMonthly: TextView
//    lateinit var youHaveProAnnually: TextView
    lateinit var screenFreeUser: ConstraintLayout
    lateinit var screenThankYou: LinearLayout
    lateinit var screenProUserAlready: ConstraintLayout
    lateinit var waitScreenLayout: LinearLayout
    lateinit var upgradeToProButton: MaterialButton
    lateinit var changeSubscriptionButton: MaterialButton

    lateinit var proVersionSubscriptionChecker: ProVersionSubscriptionChecker

    // Does the user have an active subscription to the infinite gas plan?
    var mSubscribedToProVersion = false

    // Will the subscription auto-renew?
    var mAutoRenewEnabled = false

    // Tracks the currently owned Pro version SKU, and the options in the Manage dialog
    var mProVersionSku = ""
    var mFirstChoiceSku = ""
    var mSecondChoiceSku = ""
    var mGazeProMonthly = false
    var mGazeProAnnually = false

    // Used to select between purchasing gas on a monthly or yearly basis
    var mSelectedSubscriptionPeriod = ""

    // The helper object
    var mHelper: IabHelper? = null

    lateinit var binding: ActivityUpgradingBinding

    companion object {
        private const val TAG = "UpgradingActivity"

        // (arbitrary) request code for the purchase flow
        const val RC_REQUEST = 10001
    }

    // Provides purchase notification while this app is running
    var mBroadcastReceiver: IabBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpgradingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        proVersionSubscriptionChecker = ProVersionSubscriptionChecker(this, outerLayour)
        doEverything()
    }

    private fun bindViews() {
        outerLayour = binding.activityUpgradingLayout
        toolbar = binding.toolbar

//        youHaveProMonthly = binding.inclProUser.youHaveProMonthly
//        youHaveProAnnually = binding.inclProUser.youHaveProAnnually

        screenFreeUser = binding.inclFreeUser.screenFreeUser
        screenThankYou = binding.screenThankYou
        screenProUserAlready = binding.inclProUser.screenProUserAlready
        waitScreenLayout = binding.screenWait
        upgradeToProButton = binding.inclFreeUser.upgradeToProButton
        changeSubscriptionButton = binding.inclProUser.changeSubscriptionButton

        upgradeToProButton.setOnClickListener {
            upgradeButtonClickedAction()
        }
        changeSubscriptionButton.setOnClickListener {
            upgradeButtonClickedAction()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun upgradeButtonClickedAction() {
        if (!mHelper!!.subscriptionsSupported()) {
            complain("Subscriptions not supported on your device yet. Sorry!")
            return
        }

        val options: Array<CharSequence?>
        if (!mSubscribedToProVersion || !mAutoRenewEnabled) {
            // Both subscription options should be available
            options = arrayOfNulls(2)
            options[0] = getString(R.string.gaze_pro_paid_monthly)
            options[1] = getString(R.string.gaze_pro_paid_yearly)
            mFirstChoiceSku = Const.GAZE_PRO_MONTHLY_SKU
            mSecondChoiceSku = Const.GAZE_PRO_ANNUALLY_SKU
        } else {
            // This is the subscription upgrade/downgrade path, so only one option is valid
            options = arrayOfNulls(1)
            if (mProVersionSku == Const.GAZE_PRO_MONTHLY_SKU) {
                // Give the option to upgrade to yearly
                options[0] = getString(R.string.gaze_pro_paid_yearly)
                mFirstChoiceSku = Const.GAZE_PRO_ANNUALLY_SKU
            } else {
                // Give the option to downgrade to monthly
                options[0] = getString(R.string.gaze_pro_paid_monthly)
                mFirstChoiceSku = Const.GAZE_PRO_MONTHLY_SKU
            }
            mSecondChoiceSku = ""
        }
        val titleResId: Int = if (!mSubscribedToProVersion) {
            R.string.subscription_period_prompt
        } else if (!mAutoRenewEnabled) {
            R.string.subscription_resignup_prompt
        } else {
            R.string.subscription_update_prompt
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle(titleResId)
            .setSingleChoiceItems(options, 0 /* checkedItem */, this)
            .setPositiveButton(R.string.subscription_prompt_continue, this)
            .setNegativeButton(R.string.subscription_prompt_cancel, this)
        val dialog = builder.create()
        dialog.show()
    }

    private fun doEverything() {
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
        mHelper = IabHelper(this, base64EncodedPublicKey)

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

            // Important: Dynamically register for broadcast messages about updated purchases.
            // We register the receiver here instead of as a <receiver> in the Manifest
            // because we always call getPurchases() at startup, so therefore we can ignore
            // any broadcasts sent while the app isn't running.
            // Note: registering this listener in an Activity is a bad idea, but is done here
            // because this is a SAMPLE. Regardless, the receiver must be registered after
            // IabHelper is setup, but before first call to getPurchases().
            mBroadcastReceiver = IabBroadcastReceiver(this@UpgradingActivity)
            val broadcastFilter = IntentFilter(IabBroadcastReceiver.ACTION)
            registerReceiver(mBroadcastReceiver, broadcastFilter)

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
    var mGotInventoryListener = QueryInventoryFinishedListener { result, inventory ->
        Log.d(TAG, "Query inventory finished.")

        // Have we been disposed of in the meantime? If so, quit.
        if (mHelper == null) return@QueryInventoryFinishedListener

        // Is it a failure?
        if (result.isFailure) {
            complain("Failed to query inventory: $result")
            return@QueryInventoryFinishedListener
        }
        Log.d(TAG, "Query inventory was successful.")

        /*
              * Check for items we own. Notice that for each purchase, we check
              * the developer payload to see if it's correct! See
              * verifyDeveloperPayload().
              */

        // Do we have the premium upgrade?
//            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
//            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
//            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
        val gazeProMonthly = inventory.getPurchase(Const.GAZE_PRO_MONTHLY_SKU)
        val gazeProAnnually = inventory.getPurchase(Const.GAZE_PRO_ANNUALLY_SKU)
        if (gazeProMonthly != null && gazeProMonthly.isAutoRenewing) {
            mProVersionSku = Const.GAZE_PRO_MONTHLY_SKU
            mGazeProMonthly = true
            mAutoRenewEnabled = true

            // SAVING TO SHAREDPREFERENCES IS NOT VERY SAFE. COME UP WITH A MORE SECURE WAY.
            // Save this to SharedPreferences
            tools.setProUser(true)
            tools.setProUserSku(gazeProMonthly.sku)
            tools.setProUserSince(gazeProMonthly.purchaseTime)
            tools.setProUserJson(gazeProMonthly.originalJson)
            tools.setAdsEnabled(true)
        } else if (gazeProAnnually != null && gazeProAnnually.isAutoRenewing) {
            mProVersionSku = Const.GAZE_PRO_ANNUALLY_SKU
            mGazeProAnnually = true
            mAutoRenewEnabled = true

            // SAVING TO SHAREDPREFERENCES IS NOT VERY SAFE. COME UP WITH A MORE SECURE WAY.
            // Save this to SharedPreferences
            tools.setProUser(true)
            tools.setProUserSku(gazeProAnnually.sku)
            tools.setProUserSince(gazeProAnnually.purchaseTime)
            tools.setProUserJson(gazeProAnnually.originalJson)
            tools.setAdsEnabled(true)
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

        // The user is subscribed if either subscription exists, even if neither is auto
        // renewing
        mSubscribedToProVersion = (gazeProMonthly != null && verifyDeveloperPayload(gazeProMonthly)
                || gazeProAnnually != null && verifyDeveloperPayload(gazeProAnnually))
        Log.d(
            TAG,
            "User " + (if (mSubscribedToProVersion) "HAS" else "DOES NOT HAVE") + " PRO subscription."
        )
        updateUi()
        setWaitScreen(false)
        Log.d(TAG, "Initial inventory query finished; enabling main UI.")
    }

    override fun receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.")
        try {
            mHelper!!.queryInventoryAsync(mGotInventoryListener)
        } catch (e: IabAsyncInProgressException) {
            complain("Error querying inventory. Another async operation in progress.")
        }
    }

    override fun onClick(dialog: DialogInterface, id: Int) {
        when {
            id == 0 /* First choice item */ -> {
                mSelectedSubscriptionPeriod = mFirstChoiceSku
            }
            id == 1 /* Second choice item */ -> {
                mSelectedSubscriptionPeriod = mSecondChoiceSku
            }
            id == DialogInterface.BUTTON_POSITIVE /* continue button */ -> {
                /* TODO: for security, generate your payload here for verification. See the comments on
                 *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
                 *        an empty string, but on a production app you should carefully generate
                 *        this. */
                val payload = ""
                if (TextUtils.isEmpty(mSelectedSubscriptionPeriod)) {
                    // The user has not changed from the default selection
                    mSelectedSubscriptionPeriod = mFirstChoiceSku
                }
                var oldSkus: MutableList<String?>? = null
                if (!TextUtils.isEmpty(mProVersionSku)
                    && mProVersionSku != mSelectedSubscriptionPeriod
                ) {
                    // The user currently has a valid subscription, any purchase action is going to
                    // replace that subscription
                    oldSkus = ArrayList()
                    oldSkus.add(mProVersionSku)
                }
                setWaitScreen(true)
                Log.d(TAG, "Launching purchase flow for gas subscription.")
                try {
                    mHelper!!.launchPurchaseFlow(
                        this, mSelectedSubscriptionPeriod, IabHelper.ITEM_TYPE_SUBS,
                        oldSkus, RC_REQUEST, mPurchaseFinishedListener, payload
                    )
                } catch (e: IabAsyncInProgressException) {
                    complain("Error launching purchase flow. Another async operation in progress.")
                    setWaitScreen(false)
                }
                // Reset the dialog options
                mSelectedSubscriptionPeriod = ""
                mFirstChoiceSku = ""
                mSecondChoiceSku = ""
            }
            id != DialogInterface.BUTTON_NEGATIVE -> {
                // There are only four buttons, this should not happen
                Log.e(TAG, "Unknown button clicked in subscription dialog: $id")
            }
        }
    }

    // TODO FIXME do we need this code?
    private val resultContract = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), activityResultRegistry
    ) { activityResult: ActivityResult ->
        val resultCode = activityResult.resultCode

        // Pass on the activity result to the helper for handling
        if (!mHelper!!.handleActivityResult(Const.REQUEST_CODE, resultCode, intent)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            proVersionSubscriptionChecker.checkForProVersionSubscription()
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.")
        }
    }


    /**
     * Verifies the developer payload of a purchase.
     */
    private fun verifyDeveloperPayload(p: Purchase): Boolean {
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

    // Callback for when a purchase is finished
    var mPurchaseFinishedListener = OnIabPurchaseFinishedListener { result, purchase ->
        Log.d(TAG, "Purchase finished: $result, purchase: $purchase")

        // if we were disposed of in the meantime, quit.
        if (mHelper == null) return@OnIabPurchaseFinishedListener
        if (result.isFailure) {
            complain("Error purchasing: $result")
            setWaitScreen(false)
            return@OnIabPurchaseFinishedListener
        }
        if (!verifyDeveloperPayload(purchase)) {
            complain("Error purchasing. Authenticity verification failed.")
            setWaitScreen(false)
            return@OnIabPurchaseFinishedListener
        }
        Log.d(TAG, "Purchase successful.")
        if (purchase.sku == Const.GAZE_PRO_MONTHLY_SKU || purchase.sku == Const.GAZE_PRO_ANNUALLY_SKU) {
            // Bought GAZE PRO Subscription
            Log.d(TAG, "Gaze Pro subscription purchased.")
            alert(resources.getString(R.string.thanks_for_upgrading))
            mSubscribedToProVersion = true
            mAutoRenewEnabled = purchase.isAutoRenewing
            mProVersionSku = purchase.sku
            showThankYouScreen()
        }
    }

    public override fun onDestroy() {
        super.onDestroy()

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver)
        }

        // very important:
        Log.d(TAG, "Destroying helper.")
        if (mHelper != null) {
            mHelper!!.disposeWhenFinished()
            mHelper = null
        }
    }

    // updates UI to reflect model
    fun updateUi() {
        if (mGazeProMonthly || mGazeProAnnually) {
            screenThankYou.visibility = View.GONE
            screenFreeUser.visibility = View.GONE
            screenProUserAlready.visibility = View.VISIBLE
        } else {
            screenThankYou.visibility = View.GONE
            screenFreeUser.visibility = View.VISIBLE
            screenProUserAlready.visibility = View.GONE
        }
    }

    // Enables or disables the "please wait" screen.
    private fun setWaitScreen(set: Boolean) {
        waitScreenLayout.visibility = if (set) View.VISIBLE else View.GONE
    }

    private fun showThankYouScreen() {
        waitScreenLayout.visibility = View.GONE
        screenFreeUser.visibility = View.GONE
        screenProUserAlready.visibility = View.GONE
        screenThankYou.visibility = View.VISIBLE
    }

    private fun complain(message: String) {
        Log.e(TAG, "**** Gaze Error: $message")
        alert("Error: $message")
    }

    private fun alert(message: String) {
        Log.d(TAG, "Showing alert dialog: $message")
        val builder = AlertDialog.Builder(this, R.style.GazeTheme)
        builder.setMessage(message)
        builder.setNeutralButton(R.string.ok) { _: DialogInterface?, _: Int -> }
        val dialog = builder.create()
        dialog.show()
    }


}