package net.gazeapp.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AlertDialog
import needle.Needle
import net.gazeapp.R
import net.gazeapp.billing.UpgradingActivity
import net.gazeapp.callbacks.FreeVersionDialogsCallback
import net.gazeapp.data.GazeDatabase.Companion.getDatabase
import net.gazeapp.data.dao.ContactDao
import net.gazeapp.helpers.Const
import net.gazeapp.helpers.Preferences
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.SpecificValues
import javax.inject.Inject

class FreeVersionDialogs(val context: Context) {

    @Inject
    lateinit var tools: GazeTools

    private val contactDao: ContactDao = getDatabase(context).contactDao

    companion object {
        private const val TAG = "FreeVersionDialogs"
    }

    fun decideWhichMessageToShow(callback: FreeVersionDialogsCallback) {
        val hasSeenOverTwentyContactsAddedDialog = tools.preferences.getBoolean(
            Preferences.HAS_SEEN_OVER_TWENTY_CONTACTS_ADDED_DIALOG,
            Preferences.HAS_SEEN_OVER_TWENTY_CONTACTS_ADDED_DIALOG_DEFAULT_VALUE
        )

        // Only show this for FREE Users...
        if (tools.isFreeUser()) {
            // TODO coroutines....
            Needle.onBackgroundThread().execute {
                var stopProceeding = false
                val contactsCount = contactDao.countContacts()
                if (!hasSeenOverTwentyContactsAddedDialog) {
                    for (cCount in Const.TWENTY_CONTACTS_HOOKS) {
                        if (contactsCount == cCount) {
                            showOver20ContactsAddedDialog(callback)
                            stopProceeding = true
                            break
                        }
                    }
                }
                if (!stopProceeding) {
                    if (contactsCount >= tools.maxContactSavesFreeVersion) {
                        showFreeVersionLimitsReached(callback)
                    } else {
                        callback.doNothingJustContinue()
                    }
                }
            }
        } else {
            callback.doNothingJustContinue()
        }
    }

    private fun showOver20ContactsAddedDialog(callback: FreeVersionDialogsCallback) {
        Log.d(TAG, "Show Dialog: OVER 20 CONTACTS ADDED")
        val builder = AlertDialog.Builder(
            context
        )
        builder.setTitle(R.string.over_twenty_contacts_dialog_title)
        builder.setMessage(R.string.over_twenty_contacts_dialog_text)
        builder.setPositiveButton(R.string.rate_gaze) { _, _ -> // GO TO GOOGLE PLAY
            tools.putBoolean(Preferences.HAS_SEEN_OVER_TWENTY_CONTACTS_ADDED_DIALOG, true)
            goToGooglePlay()
        }
        builder.setNeutralButton(R.string.remind_me_later) { _, _ -> // Remind the User again at a later Point
            tools.putBoolean(Preferences.HAS_SEEN_OVER_TWENTY_CONTACTS_ADDED_DIALOG, false)
            callback.clickedRateAppDialogAway()
        }
        builder.setNegativeButton(R.string.no_thanks) { _, _ -> // Do not remind User anymore. Do nothing and close dialog
            tools.putBoolean(Preferences.HAS_SEEN_OVER_TWENTY_CONTACTS_ADDED_DIALOG, true)
            callback.clickedRateAppDialogAway()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun goToGooglePlay() {
        val appUri = Uri.parse(SpecificValues.APP_DOWNLOAD_LINK)
        val intentRateApp = Intent(Intent.ACTION_VIEW, appUri)
        if (intentRateApp.resolveActivity(context.packageManager) != null) {
            context.startActivity(intentRateApp)
        }
    }

    fun showFreeVersionLimitsReached(callback: FreeVersionDialogsCallback) {
        Log.d(TAG, "Show Dialog: FREE VERSION LIMIT REACHED")
        val dialogText = context.getString(
            R.string.free_version_limits_reached_text,
            tools.maxImageSavesFreeVersion
        )
        val builder = AlertDialog.Builder(
            context
        )
        builder.setTitle(R.string.free_version_limits_reached_title)
        builder.setMessage(dialogText)
        builder.setPositiveButton(R.string.upgrade_to_pro) { _, _ ->
            val intent = Intent(context, UpgradingActivity::class.java)
            context.startActivity(intent)
        }
        builder.setNegativeButton(R.string.keep_free_version) { _, _ -> // Close Dialog. Return to net.gazeapp.MainActivity
            callback.clickedKeepFreeVersion()
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    fun showImageSavesPerContactLimitReached() {
        val dialogText = context.getString(
            R.string.free_version_max_images_per_contact_limit_reached_text,
            tools.maxImageSavesFreeVersion
        )
        val builder = AlertDialog.Builder(
            context
        )
        builder.setTitle(R.string.free_version_max_images_per_contact_limit_reached_title)
        builder.setMessage(dialogText)
        builder.setPositiveButton(R.string.upgrade_to_pro) { _, _ ->
            val intent = Intent(context, UpgradingActivity::class.java)
            context.startActivity(intent)
        }
        builder.setNegativeButton(R.string.got_it) { dialog, which ->
            // Close Dialog.
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

    fun showMyMediaImageSavesLimitReached() {
        val builder = AlertDialog.Builder(
            context
        )
        builder.setTitle(R.string.free_version_max_images_per_contact_limit_reached_title)
        builder.setMessage(R.string.free_version_max_image_saves_limit_my_media_reached_text)
        builder.setPositiveButton(R.string.upgrade_to_pro) { _, _ ->
            val intent = Intent(context, UpgradingActivity::class.java)
            context.startActivity(intent)
        }
        builder.setNegativeButton(R.string.got_it) { _, _ ->
            // Close Dialog.
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

}