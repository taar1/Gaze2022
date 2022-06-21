package net.gazeapp.ui.contactview

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ShareCompat.IntentBuilder
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.R.string
import net.gazeapp.data.model.Media
import net.gazeapp.databinding.ContactviewTabGalleryKtBinding
import net.gazeapp.helpers.SnackBarType
import net.gazeapp.listeners.OnMediaClickListener
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class TabGalleryFragmentKt : Fragment(R.layout.contactview_tab_gallery_kt), OnMediaClickListener {

    @Inject
    lateinit var tools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    private lateinit var viewBinding: ContactviewTabGalleryKtBinding
    private lateinit var fragmentView: View

    private var countSelected = 0

    private var contactId: Int = 0

    lateinit var galleryRecyclerAdapter: GalleryRecyclerAdapter

    private var actionMode: ActionMode? = null

    private var mediaList: MutableList<Media>? = null

    lateinit var outerLayout: ConstraintLayout
    lateinit var galleryRecyclerView: RecyclerView
    lateinit var noGalleryCardView: CardView
    lateinit var galleryCardView: CardView
    lateinit var noGalleryLayout: ConstraintLayout
    lateinit var noGallery: TextView
    lateinit var addImageToGallery: ImageView
    lateinit var progressBar: ProgressBar

    companion object {
        private const val TAG = "ContactViewGalleryFragmentKt"
        private const val CONTACT_ID = "contactId"
        private const val POSITION = "position"
        private const val SPAN_COUNT = 3

        fun newInstance(contactId: Int) = TabGalleryFragmentKt().apply {
            arguments = bundleOf(CONTACT_ID to contactId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getInt(CONTACT_ID)?.let {
            contactId = it
        }
    }

    val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            contactId, Application()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactviewTabGalleryKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()
        initUi()

        this.fragmentView = view

        viewModel.mediaList.observe(viewLifecycleOwner, { mediaList ->
            Log.d(TAG, "YYYYY onViewCreated: mediaList: $mediaList")

            galleryRecyclerAdapter.mediaList = mediaList
            galleryRecyclerAdapter.notifyDataSetChanged()
        })

        viewModel.hasMediaFiles.observe(viewLifecycleOwner, { hasMediaFiles ->
            if (hasMediaFiles) {
                noGalleryCardView.visibility = View.GONE
                galleryCardView.visibility = View.VISIBLE
            } else {
                noGalleryCardView.visibility = View.VISIBLE
                galleryCardView.visibility = View.GONE
            }

            progressBar.visibility = View.GONE
        })
    }

//    private fun updateGalleryUI(mediaList: List<Media>) {
//        noGalleryCardView.visibility = View.GONE
//        gridGalleryCardView.visibility = View.VISIBLE
//
//        val mediaSize = mediaList.size
//        val mediaSizeRounded = mediaSize + (3 - mediaSize % 3)
//        val galleryRows = mediaSizeRounded / 3
//        val gridGalleryHeight = galleryRows * Const.GRID_GALLERY_ITEM_HEIGHT // each row is 490px
//        val gridGalleryHeightFloat = GazeTools.convertDpToPx(activity, gridGalleryHeight.toFloat())
//    }

    private fun initViewBindings() {
        outerLayout = viewBinding.outerLayout
        galleryRecyclerView = viewBinding.recyclerView
        noGalleryCardView = viewBinding.noGalleryCard
        galleryCardView = viewBinding.galleryCardView
        noGalleryLayout = viewBinding.noGalleryLayout
        noGallery = viewBinding.noGallery
        addImageToGallery = viewBinding.addImage
        progressBar = viewBinding.progressBar
    }

    private fun initUi() {
        progressBar.visibility = View.VISIBLE

        galleryRecyclerAdapter = GalleryRecyclerAdapter(this, requireActivity())

        galleryRecyclerView.apply {
            adapter = galleryRecyclerAdapter
            layoutManager = GridLayoutManager(activity, SPAN_COUNT)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

//        val gridGalleryHeight = galleryRows * Const.GRID_GALLERY_ITEM_HEIGHT // each row is 490px
//        gridGalleryCardView.layoutParams = LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            gridGalleryHeight
//        )
//
//        gridGalleryView.onItemClickListener =
//            OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
//                if (countSelected == 0) {
//                    // Show image full size (Using FrescoImageViewer)
//                    overlayView = ImageOverlayView(activity, ImageOverlayView.MediaType.MEDIA)
//                    val iv = ImageViewer.Builder<Any?>(activity, mediaList)
//                        .setStartPosition(position)
//                        .setFormatter(ImageViewer.Formatter<Media> { (_, _, _, _, _, _, fullPath) ->
//                            Uri.fromFile(
//                                File(
//                                    fullPath
//                                )
//                            ).toString()
//                        })
//                        .allowSwipeToDismiss(true)
//                        .hideStatusBar(true)
//                        .setImageChangeListener(imageChangeListener)
//                        .setOverlayView(overlayView)
//                        .show()
//                    overlayView.setImageViewer(iv)
//
//
//                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
//                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
//                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
//                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
//                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
////                    FullScreenViewActivity_.intent(activity).position(position).mediaList(mediaList).start();
//                } else {
//                    if (actionMode == null) {
//                        actionMode = activity.startActionMode(actionModeCallbackGalleryOne)
//                    }
//                    toggleSelection(position)
//                    actionMode.title =
//                        countSelected.toString() + " " + getString(string.selected_media_counter)
//                    Log.d(TAG, "SINGLE CLICK Selection Size: " + selected.size)
//                    if (countSelected == 0) {
//                        actionMode.finish()
//                    }
//                }
//            }
//        gridGalleryView.onItemLongClickListener =
//            OnItemLongClickListener { parent: AdapterView<*>?, view: View, position: Int, id: Long ->
//                if (actionMode != null) {
//                    return@setOnItemLongClickListener false
//                }
//                Log.d(TAG, "LONG CLICK Selection Size: " + selected.size)
//                actionMode = activity.startActionMode(actionModeCallbackGalleryOne)
//                toggleSelection(position)
//                actionMode.title =
//                    countSelected.toString() + " " + getString(string.selected_media_counter)
//                view.isSelected = true
//                true
//            }

//        updateScrollview();
    }

//    private val imageChangeListener: OnImageChangeListener
//        private get() = OnImageChangeListener { position: Int -> overlayView.setMedia(mediaList!![position]) }

    fun addImagesView() {
        // TODO FIXME click on add image...
        // TODO FIXME click on add image...
        // TODO FIXME click on add image...
//        Intent intent = new Intent(activity, AlbumSelectActivity.class);
//        if (!GazeTools.isProUser()) {
//            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, GazeTools.getMaxImageSavesFreeVersion());
//        }
//        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        orientationBasedUI(newConfig.orientation)
//    }

//    private fun orientationBasedUI(orientation: Int) {
//        val windowManager = activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//        val metrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(metrics)
//        if (gridAdapter != null) {
//            val size =
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) metrics.widthPixels / 3 else metrics.widthPixels / 5
//            gridAdapter.setLayoutParams(size)
//        }
//        gridGalleryView.numColumns =
//            if (orientation == Configuration.ORIENTATION_PORTRAIT) 3 else 5
//    }
//
//    var actionModeCallbackGalleryOne: ActionMode.Callback = object : ActionMode.Callback {
//        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
//            Log.d(TAG, "onCreateActionMode")
//            val menuInflater = mode.menuInflater
//            menuInflater.inflate(R.menu.gridgallery_contextual_action_bar, menu)
//            actionMode = mode
//            countSelected = 0
//            return true
//        }
//
//        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
//            return false
//        }
//
//        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
//            return when (item.itemId) {
//                R.id.menu_item_share -> {
//                    shareFile()
//                    mode.finish()
//                    true
//                }
//                R.id.menu_item_delete -> {
//                    deleteFiles()
//                    mode.finish()
//                    true
//                }
//                R.id.menu_item_set_mainpic -> {
//                    setMainPic()
//                    mode.finish()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        override fun onDestroyActionMode(mode: ActionMode) {
//            if (countSelected > 0) {
//                deselectAll()
//            }
//            actionMode = null
//        }
//    }

    private fun shareFile() {
        Log.d(TAG, "shareFile()")
        val shareIntent = IntentBuilder.from(requireActivity())
        val uris = selectedImages
        for (uri in uris) {
            Log.d(TAG, "shareFile() URI: " + uri.path)
            shareIntent.setType(requireActivity().contentResolver.getType(uri))
            shareIntent.addStream(uri)
        }
        startActivity(shareIntent.intent)
    }

    private fun deleteFiles() {
        val selectedMedia: MutableList<Media> = ArrayList()
        for (media in mediaList!!) {
            if (media.isSelected) {
                selectedMedia.add(media)
            }
        }
        var fileOrFiles: String? = ""
        if (selectedMedia.size > 1) {
            fileOrFiles = getString(string.files)
        } else {
            fileOrFiles = getString(string.file)
        }

        val builder = AlertDialog.Builder(activity, R.style.GazeTheme)
        builder.setTitle(string.are_you_sure)
        builder.setMessage(getString(string.delete_selected_files, fileOrFiles))
        builder.setPositiveButton(string.delete) { dialog: DialogInterface?, which: Int ->
            val deleteCount = mediaTools.deleteFilesFromPrivateStorage(selectedMedia)
            if (deleteCount == selectedMedia.size) {
                // TODO delete in viewmodel...
                //mediaDao.delete(selectedMedia)
            } else {
                tools.showMaterialSnackBar(
                    outerLayout, getString(string.error_could_not_delete_files), SnackBarType.ERROR
                )
            }
        }
        builder.setNegativeButton(string.cancel) { dialog: DialogInterface?, which: Int -> }
        val dialog = builder.create()
        dialog.show()
    }

//    private fun setMainPic() {
//        for ((_, _, isSelected, usedFileName) in mediaList!!) {
//            if (isSelected) {
//                mContact.mainPicFileName = usedFileName
//                break
//            }
//        }
//        contactDao.update(mContact)
//        gridAdapter.notifyDataSetChanged()
//
//        GazeTools.showMaterialSnackBar(
//            activity,
//            fragmentRoot,
//            getString(string.main_pic_changed),
//            SnackBarType.SUCCESS,
//            1000
//        )
//    }

//    @Subscribe
//    fun onEvent(result: MediaDeletedEvent) {
//        if (result.isSucceeded) {
//            val selectedMediaList = result.mediaList
//            if (selectedMediaList != null) {
//                for (m in selectedMediaList) {
//                    mediaList.remove(m)
//                }
//            }
//            val deletedMedia = result.media
//            if (deletedMedia != null) {
//                mediaList.remove(deletedMedia)
//            }
//            val mainPic = mContact.mainPicFileName
//            val nextGeneratedPath = "/" + Const.GALLERY_PATH_PUBLIC + "/" + mContact.id + "/"
//            val mainPicFile = File(nextGeneratedPath, mainPic) // i.e. /public/20/
//
//            // Update the Main Pic. If nothing is left remove the Main Pic.
//            if (!mainPicFile.exists() && selectedMediaList.size > 0) {
//                if (mediaList != null && mediaList.size > 0) {
//                    mContact.mainPicFileName = mediaList!![0].usedFileName
//                } else {
//                    mContact.mainPicFileName = null
//                    noGalleryCardView.visibility = View.VISIBLE
//                    gridGalleryCardView.visibility = View.GONE
//                }
//            }
//
//            // TODO FIXME update contact in viewmodel...
//            //contactDao.update(mContact)
//            mediaTools.cleanCacheDir()
//            galleryRecyclerAdapter.notifyDataSetChanged()
//            GazeTools.showMaterialSnackBar(
//                activity,
//                fragmentRoot,
//                resources.getQuantityString(R.plurals.files_deleted, result.mediaList.size),
//                SnackBarType.SUCCESS,
//                2000
//            )
//        } else {
//            Log.e(TAG, "Error deleting files from database: " + result.exception.localizedMessage)
//            GazeTools.showMaterialSnackBar(
//                activity,
//                fragmentRoot,
//                resources.getQuantityString(
//                    R.plurals.error_delete_failed,
//                    result.mediaList.size
//                ),
//                SnackBarType.ERROR
//            )
//        }
//    }

    private fun toggleSelection(position: Int) {
        mediaList!![position].isSelected = !mediaList!![position].isSelected
        if (mediaList!![position].isSelected) {
            countSelected++
        } else {
            countSelected--
        }
        galleryRecyclerAdapter.notifyDataSetChanged()
    }

    private fun deselectAll() {
        if (mediaList != null) {
            var i = 0
            val l = mediaList!!.size
            while (i < l) {
                mediaList!![i].isSelected = false
                i++
            }
            countSelected = 0
            galleryRecyclerAdapter.notifyDataSetChanged()
        }
    }

    private val selected: List<Media>
        private get() {
            val selectedImages: MutableList<Media> = ArrayList()
            var i = 0
            val l = mediaList!!.size
            while (i < l) {
                if (mediaList!![i].isSelected) {
                    selectedImages.add(mediaList!![i])
                }
                i++
            }
            return selectedImages
        }

    private val selectedImages: List<Uri>
        get() {
            Log.d(TAG, "getSelectedImages()")
            val uriList: MutableList<Uri> = ArrayList()
            for ((_, _, isSelected, _, originalFileName, _, fullPath) in mediaList!!) {
                Log.d(TAG, "getSelectedImages(): $originalFileName / $isSelected")
                val file = File(fullPath)
                if (file.exists() && isSelected) {
                    val contentUri = FileProvider.getUriForFile(
                        requireActivity(), requireActivity().packageName, file
                    )
                    uriList.add(contentUri)
                }
            }
            return uriList
        }


    override fun onMediaClicked(mediaList: List<Media>, position: Int) {
        // TODO.... genau schauen was man am besten zur galerie übergeben soll...
        Log.d(TAG, "YYYYY onMediaClicked: ${mediaList.size}")
//        FullScreenViewActivity.intent(activity).position(position).mediaList(mediaList).start()

        val intent = Intent(requireContext(), MediaGalleryViewerActivityKt::class.java)
        intent.putExtra(CONTACT_ID, contactId)
        intent.putExtra(POSITION, position)
        startActivity(intent)
    }

}