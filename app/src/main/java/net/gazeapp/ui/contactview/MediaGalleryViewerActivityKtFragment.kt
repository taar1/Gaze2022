package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import net.gazeapp.R
import net.gazeapp.databinding.ImagesliderFullscreenViewBinding

class MediaGalleryViewerActivityKtFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var mediaGalleryNavi: TextView
    private lateinit var viewBinding: ImagesliderFullscreenViewBinding
    private lateinit var mediaGalleryViewerAdapterKt: MediaGalleryViewerAdapterKt

    private var contactId: Int = 0
    private var position: Int = 0
    private var mediaListSize: Int = 0

    companion object {
        private const val TAG = "MediaGalleryViewerActivityKtFragment"

        fun newInstance(contactId: Int, position: Int) =
            MediaGalleryViewerActivityKtFragment().apply {
                arguments = bundleOf(
                    MediaGalleryViewerActivityKt.CONTACT_ID to contactId,
                    MediaGalleryViewerActivityKt.POSITION to position
                )
            }
    }

    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            contactId, Application()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getInt(MediaGalleryViewerActivityKt.CONTACT_ID)?.let {
            contactId = it
        }
        arguments?.getInt(MediaGalleryViewerActivityKt.POSITION)?.let {
            position = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ImagesliderFullscreenViewBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaGalleryViewerAdapterKt = MediaGalleryViewerAdapterKt(requireActivity())

        initViewBindings()

        mediaGalleryNavi.setOnClickListener {
            mediaGalleryNavi.visibility = View.GONE
        }

        viewModel.mediaList.observe(viewLifecycleOwner) { mediaList ->
            mediaGalleryViewerAdapterKt.mediaList = mediaList
            mediaGalleryViewerAdapterKt.notifyDataSetChanged()

            mediaListSize = mediaList.size

            initPaging()
        }

        viewModel.hasMediaFiles.observe(viewLifecycleOwner) { hasMediaFiles ->
            if (hasMediaFiles) {
                viewBinding.nothing.visibility = View.GONE
                viewBinding.btnClose.visibility = View.GONE
                viewPager.visibility = View.VISIBLE
            } else {
                viewBinding.nothing.visibility = View.VISIBLE
                viewBinding.btnClose.visibility = View.VISIBLE
                viewPager.visibility = View.GONE
            }
        }

        viewBinding.btnClose.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initViewBindings() {
        viewPager = viewBinding.pager
        mediaGalleryNavi = viewBinding.galleryNavigationTxt
    }

    private fun initPaging() {
        viewPager.apply {
            adapter = mediaGalleryViewerAdapterKt
            currentItem = position

            registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    mediaGalleryNavi.text =
                        getString(R.string.media_gallery_navigation, position + 1, mediaListSize)
                }
            })
        }
    }

}