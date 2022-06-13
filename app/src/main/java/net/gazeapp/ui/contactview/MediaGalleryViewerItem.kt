package net.gazeapp.ui.contactview

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ortiz.touchview.TouchImageView
import net.gazeapp.R
import net.gazeapp.databinding.MediaGalleryViewerItemBinding

class MediaGalleryViewerItem : Fragment(R.layout.media_gallery_viewer_item) {

    private lateinit var touchImage: TouchImageView

    private lateinit var viewBinding: MediaGalleryViewerItemBinding

    private var mediaUrl: String? = null

    companion object {
        private const val TAG = "MediaGalleryViewerItem"
        private const val MEDIA_URL = "mediaUrl"

        fun newInstance(mediaPath: String) = MediaGalleryViewerItem().apply {
            arguments = bundleOf(MEDIA_URL to mediaPath)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getString(MEDIA_URL)?.let {
            mediaUrl = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = MediaGalleryViewerItemBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val outerLayout = viewBinding.outerLayout

        touchImage = viewBinding.touchImage

        touchImage.setOnTouchListener { view, event ->
            var result = true
            //can scroll horizontally checks if there's still a part of the image
            //that can be scrolled until you reach the edge
            if (event.pointerCount >= 2 || view.canScrollHorizontally(1) && view.canScrollHorizontally(
                    -1
                )
            ) {
                //multi-touch event
                result = when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        // Disallow RecyclerView to intercept touch events.
                        outerLayout.requestDisallowInterceptTouchEvent(true)
                        // Disable touch on view
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        // Allow RecyclerView to intercept touch events.
                        outerLayout.requestDisallowInterceptTouchEvent(false)
                        true
                    }
                    else -> true
                }
            }
            result
        }

        Glide.with(requireContext())
            .load(mediaUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.drawable.ic_image_24px)
            .into(touchImage)
    }

}