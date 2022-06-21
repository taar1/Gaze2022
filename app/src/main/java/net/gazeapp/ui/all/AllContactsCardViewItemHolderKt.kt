package net.gazeapp.ui.all

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.MediaTools

/**
 * Holder for the CARD VIEW BODY
 * Main Profile Pic, overlaying Profile Name, overlaying City and other info
 */
class AllContactsCardViewItemHolderKt(
    val activity: Activity,
    val view: View
) : RecyclerView.ViewHolder(view) {
    companion object {
        private const val TAG = "AllContactsCardViewItem"
    }

    lateinit var contactWithDetails: ContactWithDetails
    lateinit var mediaTools: MediaTools

    var contactImage: ImageView = view.findViewById(R.id.contactImage)
    var contactName: TextView = view.findViewById(R.id.contactName)
    var additionalInfo: TextView = view.findViewById(R.id.additionalInfo)

    fun updateUI() {
        contactName.text = contactWithDetails.contact.contactName

        if (contactWithDetails.contact.additionalInfo?.isNotEmpty() == true) {
            additionalInfo.text = contactWithDetails.contact.additionalInfo
        } else {
            additionalInfo.visibility = View.GONE
        }

        // Change Contact Main Image
        val fileFromInternalStorage =
            mediaTools.getFileFromInternalStorage(
                contactWithDetails.contact,
                Const.GALLERY_PUBLIC
            )

        if (contactWithDetails.contact.contactName.equals(
                Const.FIRST_GAZE_CONTACT_NAME,
                ignoreCase = true
            )
        ) {
            contactImage.load(R.drawable.josh) {
                placeholder(R.drawable.silhouette)
            }
        } else {
            contactImage.load(fileFromInternalStorage) {
                placeholder(R.drawable.silhouette)
            }
        }
    }
}