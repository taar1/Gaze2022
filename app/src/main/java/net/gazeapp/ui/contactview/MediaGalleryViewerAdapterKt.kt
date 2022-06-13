package net.gazeapp.ui.contactview

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.gazeapp.data.model.Media


class MediaGalleryViewerAdapterKt(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    lateinit var mediaList: List<Media>

    companion object {
        private const val TAG = "MediaGalleryViewerAdapt"
    }

    // TODO FIXME korrekte liste verwenden und item-count
    // TODO FIXME korrekte liste verwenden und item-count
    // TODO FIXME korrekte liste verwenden und item-count

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "YYYYY createFragment: ${position}")

//        return MediaGalleryViewerItem.newInstance(mediaList[position].fullPath)
        return MediaGalleryViewerItem.newInstance(mediaList[0].fullPath)
    }

    override fun getItemCount(): Int {
        return 3
//        return mediaList.size
    }
}