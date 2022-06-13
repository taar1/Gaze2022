package net.gazeapp.ui.contactview

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.gazeapp.R
import net.gazeapp.data.model.Media

/**
 * Media Holder class for the grid galleries
 */
class GalleryMediaItemHolder(
    val activity: Activity, val view: View
) : RecyclerView.ViewHolder(view) {

    companion object {
        private const val TAG = "GalleryMediaItemHolder"
    }

    var media: Media? = null

    var mediaImage: ImageView = view.findViewById(R.id.image_view_select)

    fun updateUI() {
        Log.d(TAG, "YYYYY GalleryMediaItemHolder updateUI: ${media?.fullPath}")

        activity.let {
            Glide.with(it).load(media?.fullPath)
                .placeholder(R.drawable.silhouette).into(
                    mediaImage
                )
        }


    }
}