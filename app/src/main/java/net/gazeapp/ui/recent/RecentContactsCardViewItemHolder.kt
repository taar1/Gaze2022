package net.gazeapp.ui.recent

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import net.gazeapp.R
import net.gazeapp.data.model.*
import net.gazeapp.databinding.ListItemRecentContactsCardBinding
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools

/**
 * Holder for the CARD VIEW BODY
 * Main Profile Pic, overlaying Profile Name, overlaying City and other info
 */
class RecentContactsCardViewItemHolder(
    val activity: Activity,
    val binding: ListItemRecentContactsCardBinding,
    val tools: GazeTools
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val TAG = "RecentContactsCardViewI"
        private const val GRID_GALLERY_TAB = 1
    }

    var contact: ContactWithDetails? = null
    var personal: Personal? = null
    var body: Body? = null
    var works: List<Work>? = null
    var mediaList: List<Media>? = null
    var note: Note? = null

    val mediaTools: MediaTools = MediaTools()


    var cardImage: ImageView = binding.cardImage
    var overlayLayout: LinearLayout = binding.overlayLayout
    var contactName: TextView = binding.contactName
    var additionalInfo: TextView = binding.additionalInfo
    var basicInfo: TextView = binding.basicInfo

    //    var otherInfo: TextView = view.findViewById(R.id.otherInfo)
//    var notesTextView: TextView = view.findViewById(R.id.note)
    var profileButton: com.google.android.material.button.MaterialButton = binding.buttonProfile
    var galleryButton: com.google.android.material.button.MaterialButton = binding.buttonGallery

    var age: String = ""
    var height: String = ""
    var weight: String = ""

    fun updateUI() {
        contact?.let { contactWithDetails ->
            personal = contactWithDetails.personal
            body = contactWithDetails.body
            works = contactWithDetails.work
            mediaList = contactWithDetails.media
            note = contactWithDetails.note

            contactName.text = contactWithDetails.contact.contactName

            additionalInfo.visibility = View.GONE
            contactWithDetails.contact.additionalInfo.let {
                additionalInfo.text = contactWithDetails.contact.additionalInfo
                additionalInfo.visibility = View.VISIBLE
            }

            // Use metric system?
            val useMetricSystem = tools.useMetricSystem()
            Log.d(TAG, "useMetricSystem: $useMetricSystem")

            personal?.age.let { personAge ->
                if (personAge != 0) {
                    age = activity.getString(R.string.years_short, personAge)
                }
            }

            if (personal?.birthdate != null) {
                age = activity.getString(
                    R.string.years_short, tools.getAge(personal?.birthdate)
                )
            }

            body?.height?.let { personHeight ->
                height = tools.convertHeightFromCentimetersToReadableFormat(
                    personHeight, useMetricSystem
                )
            }

            body?.weight?.let { personWeight ->
                weight = tools.convertWeightFromGramsToReadableFormat(
                    personWeight, useMetricSystem
                )
            }

            val ageHeightWeightList = ArrayList<String?>()
            if (age.isNotEmpty()) {
                ageHeightWeightList.add(age)
            }
            if (height.length > 3) {
                ageHeightWeightList.add(height)
            }
            if (weight.length > 3) {
                ageHeightWeightList.add(weight)
            }
            var basicInfoImploded = TextUtils.join(" | ", ageHeightWeightList)

            // If AGE, HEIGHT, WEIGHT not set, display n/a
            if (basicInfoImploded.trim { it <= ' ' }.isEmpty()) {
                basicInfoImploded = activity.resources?.getString(R.string.not_available)
            }
            basicInfo.text = basicInfoImploded

            // WORK
//            otherInfo.text = ""
//            works?.let {
//                for (workObj in it) {
//                    val professionStr = workObj.profession
//                    if (professionStr!!.isNotEmpty()) {
//                        otherInfo.text = professionStr
//                    }
//                    break
//                }
//            }

//            notesTextView.visibility = View.GONE
//            note?.note?.let {
//                notesTextView.text = it
//                notesTextView.visibility = View.VISIBLE
//            }

            // Display CONTACT VIEW
            profileButton.setOnClickListener {
                val action =
                    RecentContactsFragmentDirections.actionNavRecentToContactViewWithViewPagerTabActivity(
                        contactWithDetails.contact.id
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }

            // Display GRID GALLERY VIEW
            galleryButton.setOnClickListener {
                val action =
                    RecentContactsFragmentDirections.actionNavRecentToContactViewWithViewPagerTabActivity(
                        contactWithDetails.contact.id
                    )
                action.position = GRID_GALLERY_TAB

                Navigation.findNavController(binding.root).navigate(action)
            }

            mediaList?.let { mList ->
                for (media in mList) {
                    if (contactWithDetails.contact.mainPicId == media.id) {
                        cardImage.load(media.fullPath) {
                            placeholder(R.drawable.silhouette)
                        }
                        break
                    }
                }
            }
        }
    }
}