/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gazeapp.ui.contactview;

import static net.gazeapp.R.string.file;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;

import net.gazeapp.R;
import net.gazeapp.callbacks.MediaListLoadCallback;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.ContactDao;
import net.gazeapp.data.dao.MediaDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Media;
import net.gazeapp.event.MediaDeletedEvent;
import net.gazeapp.gridgallery.GridViewAdapter;
import net.gazeapp.helpers.Const;
import net.gazeapp.utilities.FileProvider;
import net.gazeapp.utilities.MediaTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import needle.Needle;

@Deprecated
// use TabGalleryFragmentKt
public class ContactViewGalleryFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private ContactViewWithViewPagerTabActivity activity;
    private Contact mContact;

    private GridViewAdapter gridAdapter;
    private ActionMode actionMode;
    private int countSelected;

    public List<Media> mediaList;

    MediaTools mediaTools;

    @BindView(R.id.fragment_root)
    FrameLayout fragmentRoot;
    //    @BindView(R.id.grid_gallery_view)
    GridView gridGalleryView;
    @BindView(R.id.no_gallery_card)
    CardView noGalleryCardView;
    //    @BindView(R.id.grid_gallery_card_view)
    CardView gridGalleryCardView;
    @BindView(R.id.no_gallery_layout)
    RelativeLayout noGalleryLayout;
    @BindView(R.id.no_gallery)
    TextView noGallery;
    @BindView(R.id.add_image)
    ImageView addImageToGallery;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.scroll)
    ObservableScrollView scrollView;
    @BindView(R.id.gray_layout)
    LinearLayout grayLayout;
    //    @BindView(R.id.grid_gallery_layout)
    LinearLayout gridGalleryLayout;

    //    ImageOverlayView overlayView;
    private ContactDao contactDao;
    private MediaDao mediaDao;


    public ContactViewGalleryFragment() {
    }

    public static ContactViewGalleryFragment newInstance() {
        ContactViewGalleryFragment fragment = new ContactViewGalleryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contactview_tab_gallery, container, false);
        ButterKnife.bind(this, view);

        activity = (ContactViewWithViewPagerTabActivity) getActivity();
        mContact = activity.getContact();

        loadData();
        return view;
    }

    private void loadData() {
        Needle.onBackgroundThread().execute(() -> {
            contactDao = GazeDatabase.Companion.getDatabase(getActivity()).getContactDao();
            mediaDao = GazeDatabase.Companion.getDatabase(getActivity()).getMediaDao();

            updateUI();
        });
    }

    @MainThread
    public void updateUI() {
        // TODO @MainThreadtesten...
        // TODO @MainThreadtesten...
        Needle.onMainThread().execute(() -> {

            progressBar.setVisibility(View.VISIBLE);
            grayLayout.setMinimumHeight(activity.getDisplayHeight(false, true));

            activity.getMediaList(new MediaListLoadCallback() {

                @Override
                public void success(List<Media> ml) {
                    mediaList = ml;

                    noGalleryCardView.setVisibility(View.GONE);
                    gridGalleryLayout.setVisibility(View.VISIBLE);

                    int mediaSize = mediaList.size();
                    int mediaSizeRounded = mediaSize + (3 - (mediaSize % 3));

                    int galleryRows = mediaSizeRounded / 3;
                    int gridGalleryHeight = galleryRows * Const.GRID_GALLERY_ITEM_HEIGHT; // each row is 490px
//                    float gridGalleryHeightFloat = GazeTools.convertDpToPx(activity, gridGalleryHeight);

                    gridGalleryCardView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridGalleryHeight));

                    if (gridAdapter == null) {
                        gridAdapter = new GridViewAdapter(activity, R.layout.gridgallery_grid_item_layout, (ArrayList) mediaList);
                        gridGalleryView.setAdapter(gridAdapter);

                        progressBar.setVisibility(View.GONE);
                        orientationBasedUI(getResources().getConfiguration().orientation);
                    } else {
                        gridAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void fail(Exception e) {
                    // SHOW EMPTY VIEW
                    activity.runOnUiThread(() -> {
                        Log.e(TAG, "Error loading the grid gallery content: " + e.getLocalizedMessage());
                        gridGalleryLayout.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        gridGalleryView.setVisibility(View.GONE);
                        noGalleryCardView.setVisibility(View.VISIBLE);
                        addImageToGallery.setVisibility(View.VISIBLE);
                        noGalleryLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getDisplayHeight(true, false)));
                    });
                }

                @Override
                public void empty() {
                    // SHOW EMPTY VIEW
                    activity.runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        gridGalleryView.setVisibility(View.GONE);
                        noGalleryCardView.setVisibility(View.VISIBLE);
                        noGalleryLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, activity.getDisplayHeight(true, false)));
                    });
                }
            });

            gridGalleryView.setOnItemClickListener((parent, view, position, id) -> {
                if (countSelected == 0) {
                    // Show image full size (Using FrescoImageViewer)
//                    overlayView = new ImageOverlayView(activity, ImageOverlayView.MediaType.MEDIA);

//                    ImageViewer iv = new ImageViewer.Builder(activity, mediaList)
//                            .setStartPosition(position)
//                            .setFormatter((ImageViewer.Formatter<Media>) media -> Uri.fromFile(new File(media.getFullPath())).toString())
//                            .allowSwipeToDismiss(true)
//                            .hideStatusBar(true)
//                            .setImageChangeListener(getImageChangeListener())
//                            .setOverlayView(overlayView)
//                            .show();
//                    overlayView.setImageViewer(iv);


                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
                    // TODO FIXME FullScreenViewActivity kann man löschen. wird wohl noch bei MY GALLERY verwendet.
//                    FullScreenViewActivity_.intent(activity).position(position).mediaList(mediaList).start();
                } else {
                    if (actionMode == null) {
                        actionMode = activity.startActionMode(actionModeCallbackGalleryOne);
                    }

                    toggleSelection(position);
                    actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));

                    Log.d(TAG, "SINGLE CLICK Selection Size: " + getSelected().size());

                    if (countSelected == 0) {
                        actionMode.finish();
                    }
                }
            });

            gridGalleryView.setOnItemLongClickListener((parent, view, position, id) -> {
                if (actionMode != null) {
                    return false;
                }

                Log.d(TAG, "LONG CLICK Selection Size: " + getSelected().size());

                actionMode = activity.startActionMode(actionModeCallbackGalleryOne);

                toggleSelection(position);
                actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));
                view.setSelected(true);

                return true;
            });

//        updateScrollview();
        });
    }

//    private ImageViewer.OnImageChangeListener getImageChangeListener() {
//        return position -> overlayView.setMedia(mediaList.get(position));
//    }

    @OnClick(R.id.add_image)
    void addImagesView() {
        // TODO FIXME AlbumSelectActivity ersatz....
        // TODO FIXME AlbumSelectActivity ersatz....
        // TODO FIXME AlbumSelectActivity ersatz....
        // TODO FIXME AlbumSelectActivity ersatz....
//        Intent intent = new Intent(activity, AlbumSelectActivity.class);
//        if (!GazeTools.isProUser()) {
//            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, GazeTools.getMaxImageSavesFreeVersion());
//        }
//        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationBasedUI(newConfig.orientation);
    }

    private void orientationBasedUI(int orientation) {
        final WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        if (gridAdapter != null) {
            int size = orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 3 : metrics.widthPixels / 5;
            gridAdapter.setLayoutParams(size);
        }
        gridGalleryView.setNumColumns(orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5);
    }

//    private void updateScrollview() {
//        scrollView.setTouchInterceptionViewGroup(fragmentRoot);
//
//        // Scroll to the specified offset after layout
//        Bundle args = getArguments();
//        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
//            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
//            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
//                @Override
//                public void run() {
//                    scrollView.scrollTo(0, scrollY);
//                }
//            });
//            updateFlexibleSpace(scrollY, view);
//        } else {
//            updateFlexibleSpace(0, view);
//        }
//
//        scrollView.setScrollViewCallbacks(this);
//    }
//
//    @Override
//    protected void updateFlexibleSpace(int scrollY) {
//        // Sometimes scrollable.getCurrentScrollY() and the real scrollY has different values.
//        // As a workaround, we should call scrollVerticallyTo() to make sure that they match.
//        Scrollable s = getScrollable();
//        s.scrollVerticallyTo(scrollY);
//
//        // If scrollable.getCurrentScrollY() and the real scrollY has the same values,
//        // calling scrollVerticallyTo() won't invoke scroll (or onScrollChanged()), so we call it here.
//        // Calling this twice is not a problem as long as updateFlexibleSpace(int, View) has idempotence.
//        updateFlexibleSpace(scrollY, getView());
//    }
//
//    @Override
//    protected void updateFlexibleSpace(int scrollY, View view) {
//        // Also pass this event to parent Activity
//        ContactViewWithViewPagerTabActivity parentActivity = (ContactViewWithViewPagerTabActivity) getActivity();
//        if (parentActivity != null) {
//            parentActivity.onScrollChanged(scrollY, scrollView);
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        mContact = activity.getContact();
    }


    public ActionMode.Callback actionModeCallbackGalleryOne = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.d(TAG, "onCreateActionMode");
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.gridgallery_contextual_action_bar, menu);

            actionMode = mode;
            countSelected = 0;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_item_share:
                    shareFile();
                    mode.finish();
                    return true;
                case R.id.menu_item_delete:
                    deleteFiles();
                    mode.finish();
                    return true;
                case R.id.menu_item_set_mainpic:
                    setMainPic();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (countSelected > 0) {
                deselectAll();
            }
            actionMode = null;
        }
    };

    private void shareFile() {
        Log.d(TAG, "shareFile()");

        ShareCompat.IntentBuilder shareIntent = ShareCompat.IntentBuilder.from(activity);

        List<Uri> uris = getSelectedImages();
        for (Uri uri : uris) {
            Log.d(TAG, "shareFile() URI: " + uri.getPath());
            shareIntent.setType(activity.getContentResolver().getType(uri));
            shareIntent.addStream(uri);
        }

        startActivity(shareIntent.getIntent());
    }

    private void deleteFiles() {
        final List<Media> selectedMedia = new ArrayList<>();
        for (Media media : mediaList) {
            if (media.isSelected()) {
                selectedMedia.add(media);
            }
        }

        String fileOrFiles = "";
        if (selectedMedia.size() > 1) {
            fileOrFiles = getString(R.string.files);
        } else {
            fileOrFiles = getString(file);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.GazeAppTheme);
        builder.setTitle(R.string.are_you_sure);
        builder.setMessage(getString(R.string.delete_selected_files, fileOrFiles));

        builder.setPositiveButton(R.string.delete, (dialog, which) -> {
            int deleteCount = mediaTools.deleteFilesFromPrivateStorage(selectedMedia);
            if (deleteCount == selectedMedia.size()) {
                mediaDao.delete(selectedMedia);
            } else {
//                GazeTools.showMaterialSnackBar(activity, fragmentRoot, getString(R.string.error_could_not_delete_files), SnackBarType.ERROR);
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            // Do nothing, user cancelled.
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setMainPic() {
        // TODO FIXME move logic to viewmodel / room
        // TODO FIXME move logic to viewmodel / room
//        for (Media media : mediaList) {
//            if (media.isSelected()) {
//                mContact.setMainPicFileName(media.getUsedFileName());
//                break;
//            }
//        }

        contactDao.update(mContact);

        gridAdapter.notifyDataSetChanged();

//        GazeTools.showMaterialSnackBar(activity, fragmentRoot, getString(R.string.main_pic_changed), SnackBarType.SUCCESS, 1000);
    }

    @Subscribe
    public void onEvent(MediaDeletedEvent result) {
        if (result.isSucceeded()) {

            List<Media> selectedMediaList = result.getMediaList();
            if (selectedMediaList != null) {
                for (Media m : selectedMediaList) {
                    mediaList.remove(m);
                }
            }

            Media deletedMedia = result.getMedia();
            if (deletedMedia != null) {
                mediaList.remove(deletedMedia);
            }

            // TODO FIXME move logic to viewmodel / room
            // TODO FIXME move logic to viewmodel / room
//            String mainPic = mContact.getMainPicFileName();
//            String nextGeneratedPath = "/" + Const.GALLERY_PATH_PUBLIC + "/" + mContact.getId() + "/";
//            File mainPicFile = new File(nextGeneratedPath, mainPic); // i.e. /public/20/
//
//            // Update the Main Pic. If nothing is left remove the Main Pic.
//            if ((!mainPicFile.exists()) && (selectedMediaList.size() > 0)) {
//                if ((mediaList != null) && (mediaList.size() > 0)) {
//                    mContact.setMainPicFileName(mediaList.get(0).getUsedFileName());
//                } else {
//                    mContact.setMainPicFileName(null);
//                    noGalleryCardView.setVisibility(View.VISIBLE);
//                    gridGalleryLayout.setVisibility(View.GONE);
//                }
//            }

            contactDao.update(mContact);

            mediaTools.cleanCacheDir();
            gridAdapter.notifyDataSetChanged();

//            GazeTools.showMaterialSnackBar(activity, fragmentRoot, getResources().getQuantityString(R.plurals.files_deleted, result.getMediaList().size()), SnackBarType.SUCCESS, 2000);
        } else {
            Log.e(TAG, "Error deleting files from database: " + result.getException().getLocalizedMessage());
//            GazeTools.showMaterialSnackBar(activity, fragmentRoot, getResources().getQuantityString(R.plurals.error_delete_failed, result.getMediaList().size()), SnackBarType.ERROR);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void toggleSelection(int position) {
        mediaList.get(position).setSelected(!mediaList.get(position).isSelected());

        if (mediaList.get(position).isSelected()) {
            countSelected++;
        } else {
            countSelected--;
        }
        gridAdapter.notifyDataSetChanged();
    }


    private void deselectAll() {
        if (mediaList != null) {
            for (int i = 0, l = mediaList.size(); i < l; i++) {
                mediaList.get(i).setSelected(false);
            }
            countSelected = 0;
            gridAdapter.notifyDataSetChanged();
        }
    }

    private List<Media> getSelected() {
        List<Media> selectedImages = new ArrayList<>();
        for (int i = 0, l = mediaList.size(); i < l; i++) {
            if (mediaList.get(i).isSelected()) {
                selectedImages.add(mediaList.get(i));
            }
        }
        return selectedImages;
    }

    private List<Uri> getSelectedImages() {
        Log.d(TAG, "getSelectedImages()");

        List<Uri> uriList = new ArrayList<>();
        for (Media media : mediaList) {
            Log.d(TAG, "getSelectedImages(): " + media.getOriginalFileName() + " / " + media.isSelected());

            File file = new File(media.getFullPath());
            if (file.exists() && media.isSelected()) {
                Uri contentUri = FileProvider.getUriForFile(activity, activity.getPackageName(), file);
                uriList.add(contentUri);
            }
        }
        return uriList;
    }

}
