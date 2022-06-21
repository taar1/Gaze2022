package net.gazeapp.ui.contactview

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import net.gazeapp.R
import net.gazeapp.data.model.Media

/**
 * Media Holder class for the grid galleries
 */
class GalleryMediaItemHolder(
    val activity: Activity, val view: View
) : RecyclerView.ViewHolder(view) {

    var media: Media? = null
    var mediaImage: ImageView = view.findViewById(R.id.image_view_select)

    fun updateUI() {
        mediaImage.load(media?.fullPath) {
            crossfade(true)
            placeholder(R.drawable.silhouette)
        }
    }
}