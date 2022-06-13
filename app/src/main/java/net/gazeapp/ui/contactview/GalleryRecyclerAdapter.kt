package net.gazeapp.ui.contactview

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.gazeapp.R
import net.gazeapp.data.model.Media
import net.gazeapp.listeners.OnMediaClickListener

class GalleryRecyclerAdapter(
    private val onMediaClickListener: OnMediaClickListener,
    private val activity: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "GalleryGridRecyclerAdapter"
    }

    var mediaList: List<Media> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_view_gallery_image_item, parent, false)
        return GalleryMediaItemHolder(activity, itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val media = mediaList[position]
        val contactCardObjectViewHolder = viewHolder as GalleryMediaItemHolder
        contactCardObjectViewHolder.media = media

        contactCardObjectViewHolder.itemView.setOnClickListener {
            Log.d(TAG, "YYYYY VVVVV onBindViewHolder: ")
            onMediaClickListener.onMediaClicked(
                mediaList, position
            )
        }
        contactCardObjectViewHolder.updateUI()
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }
}