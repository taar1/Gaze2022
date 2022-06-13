package net.gazeapp.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.cardview.widget.CardView;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.android.material.button.MaterialButton;

import net.gazeapp.R;
import net.gazeapp.callbacks.MyMediaListLoadCallback;
import net.gazeapp.data.GazeImage;
import net.gazeapp.data.model.Body;
import net.gazeapp.data.model.Contact;
import net.gazeapp.data.model.Media;
import net.gazeapp.data.model.Personal;
import net.gazeapp.event.MediaAddedEvent;
import net.gazeapp.helpers.Const;
import net.gazeapp.imageslider.ContactFullScreenViewerActivity;
import net.gazeapp.mymediagridgallery.MyMediaGridGalleryActivity;
import net.gazeapp.mymediagridgallery.MyMediaGridViewAdapter;
import net.gazeapp.utilities.FileProvider;
import net.gazeapp.utilities.FreeVersionDialogs;
import net.gazeapp.utilities.GazeTools;
import net.gazeapp.utilities.MediaTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeTabFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public static final String ARG_SCROLL_Y = "ARG_SCROLL_Y";
    public static final int GALLERY_ONE = 1;
    public static final int GALLERY_TWO = 2;
    static final int OPEN_MEDIA_PICKER = 112;  // Request code

    private final Activity activity;

    private View view;
    private Contact contact;
    private Personal mPersonal;
    private Body mBody;

    private ActionMode actionMode;
    private int countSelected;

    private MyMediaGridViewAdapter gridAdapterOne;
    private MyMediaGridViewAdapter gridAdapterTwo;

    public ArrayList<Media> mediaListOne;
    public ArrayList<Media> mediaListTwo;

    MediaTools mediaTools;
    FreeVersionDialogs freeVersionDialogs;

    @BindView(R.id.button_view_profile)
    MaterialButton buttonViewProfile;
    @BindView(R.id.button_edit_profile)
    MaterialButton buttonEditProfile;

    @BindView(R.id.card_image)
    ImageView cardImage;
    @BindView(R.id.contact_name)
    TextView contactName;
    @BindView(R.id.additional_info)
    TextView additionalInfo;
    @BindView(R.id.basicInfo)
    TextView basicInfo;
    @BindView(R.id.note)
    TextView note;
    @BindView(R.id.scroll)
    ScrollView scrollView;

    @BindView(R.id.cardView)
    CardView meCardView;
    @BindView(R.id.my_media_title)
    TextView myMediaTitle;
    @BindView(R.id.card_view_gallery_one)
    CardView cardViewGalleryOne;
    @BindView(R.id.progress_bar_gallery_one)
    ProgressBar progressBarOne;
    @BindView(R.id.recycler_view_gallery_one)
    GridView gridViewGalleryOne;
    @BindView(R.id.vertical_layout_gallery_one)
    LinearLayout verticalLayoutGalleryOne;
    @BindView(R.id.no_photos_text_gallery_one)
    TextView noPhotosGalleryOne;
    @BindView(R.id.button_add_images_gallery_one)
    MaterialButton addImagesGalleryOne;
    @BindView(R.id.button_edit_images_gallery_one)
    MaterialButton editImagesGalleryOne;
    @BindView(R.id.gallery_two_show_button)
    MaterialButton showGalleryTwoButton;
    @BindView(R.id.card_view_gallery_two)
    CardView cardViewGalleryTwo;
    @BindView(R.id.progress_bar_gallery_two)
    ProgressBar progressBarTwo;
    @BindView(R.id.recycler_view_gallery_two)
    GridView gridViewGalleryTwo;
    @BindView(R.id.vertical_layout_gallery_two)
    LinearLayout verticalLayoutGalleryTwo;
    @BindView(R.id.no_photos_text_gallery_two)
    TextView noPhotosGalleryTwo;
    @BindView(R.id.button_add_images_gallery_two)
    MaterialButton addImagesGalleryTwo;
    @BindView(R.id.button_edit_images_gallery_two)
    MaterialButton editImagesGalleryTwo;

    private int activeGalleryNumber;
    private ArrayList<GazeImage> galleryOneImageList;
    private ArrayList<GazeImage> galleryTwoImageList;

    private Handler handlerOne;
    private Handler handlerTwo;
    private Thread threadOne;
    private Thread threadTwo;
//    private final String[] requiredPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    private ShareActionProvider share;
//    private ImageOverlayView overlayView;

    private final GazeTools tools;

    public MeTabFragment() {
        this.activity = getActivity();
        mediaTools = new MediaTools();
        tools = new GazeTools(activity);
    }

    public MeTabFragment(Activity activity) {
        this.activity = activity;

        mediaTools = new MediaTools();
        freeVersionDialogs = new FreeVersionDialogs(activity);
        tools = new GazeTools(activity);
    }

//    public static MeTabFragment newInstance(String id) {
//        Bundle args = new Bundle();
//        args.putString("id", id);
//        MeTabFragment f = new MeTabFragment();
//        f.setArguments(args);
//        return f;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_me_tab, container, false);
        ButterKnife.bind(this, view);

        updateUI();
        return view;
    }

    private void updateUI() {
        if (activity instanceof ObservableScrollViewCallbacks) {
            // Scroll to the specified offset after layout
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARG_SCROLL_Y)) {
                final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
                ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
                    @Override
                    public void run() {
                        scrollView.scrollTo(0, scrollY);
                    }
                });
            }

//            // TouchInterceptionViewGroup should be a parent view other than ViewPager.
//            // This is a workaround for the issue #117:
//            // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
//            scrollView.setTouchInterceptionViewGroup(rootViewGroup);
//            scrollView.setScrollViewCallbacks((ObservableScrollViewCallbacks) activity);
//
//            // This line prevents the FLICKERING. Taken from:
//            // https://github.com/florent37/MaterialViewPager/issues/25
//            scrollView.setTouchInterceptionViewGroup((ViewGroup) scrollView.getParent().getParent());
        }

        loadGalleryOne();
        loadGalleryTwo();

//        getMe();
//        if (contact == null) {
//            buttonViewProfile.setEnabled(false);
//            buttonEditProfile.setText(getString(R.string.create_my_profile));
//        } else {
//            // Me Card not used yet
//            // updateMeCardLayout();
//            meCardView.setVisibility(View.GONE);
////            myMediaTitle.setVisibility(View.GONE);
//        }
    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.d(TAG, "MMMMMM onCreateOptionsMenu() 2");
////        getMenuInflater().inflate(R.menu.gridgallery_menu_share_selection_contextual_action_bar, menu);
////
////        MenuItem item = menu.findItem(R.id.menu_item_share);
////
////        share = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
////        share.setOnShareTargetSelectedListener(this);
////
////        return (super.onCreateOptionsMenu(menu));
//
//
//        activity.getMenuInflater().inflate(R.menu.gridgallery_menu_share_selection_contextual_action_bar, menu);
//        MenuItem item = menu.findItem(R.id.menu_item_share);
//
//        // Sharing
//        share = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
//
////        provider = (android.widget.ShareActionProvider) menu.findItem(R.id.menu_item_share).getActionProvider();
//        return super.onCreateOptionsMenu(menu);
//    }


//    @Override
//    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
//        Toast.makeText(activity, intent.getComponent().toString(), Toast.LENGTH_LONG).show();
//
//        return (false);
//    }

    void loadGalleryOne() {
        noPhotosGalleryOne.setVisibility(View.GONE);
        progressBarOne.setVisibility(View.VISIBLE);

        mediaTools.getMyMediaFromInternalStorage(new MyMediaListLoadCallback() {

            @Override
            public void success(ArrayList<GazeImage> mediaList) {
                galleryOneImageList = mediaList;

                int mediaSize = mediaList.size();
                int modulo = mediaSize % Const.GRID_GALLERY_COLUMNS;
                if (modulo > 0) {
                    mediaSize = mediaSize + (Const.GRID_GALLERY_COLUMNS - modulo);
                }

                int galleryRows = mediaSize / Const.GRID_GALLERY_COLUMNS;
                int gridGalleryHeight = galleryRows * Const.GRID_GALLERY_ITEM_HEIGHT; // each row is 490px

                // Adding 11dp to the grid height for some padding on the bottom side.
                float gridGalleryHeightDp = tools.convertDpToPx(11);
                int adjustedGalleryHeight = Math.round(gridGalleryHeight + gridGalleryHeightDp);

                //gridGalleryOuterLayoutOne.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adjustedGalleryHeight));

                if (gridAdapterOne == null) {
                    gridAdapterOne = new MyMediaGridViewAdapter(activity, R.layout.gridgallery_grid_item_layout, mediaList);
                    gridViewGalleryOne.setAdapter(gridAdapterOne);
                    orientationBasedUI(gridAdapterOne, gridViewGalleryOne, getResources().getConfiguration().orientation);
                } else {
                    mediaTools.cleanMyMediaCacheDir();
                    gridAdapterOne.notifyDataSetChanged();
                    /*
                    Some selected images may have been deleted
                    hence update action mode title
                     */
                    if (actionMode != null) {
                        actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));
                    }
                }

                progressBarOne.setVisibility(View.GONE);
                gridViewGalleryOne.setVisibility(View.VISIBLE);
                addImagesGalleryOne.setVisibility(View.VISIBLE);
                editImagesGalleryOne.setVisibility(View.VISIBLE);
            }

            @Override
            public void fail(Exception e) {
                Log.e(TAG, "Error loading the Grid Gallery One Content: " + e.getLocalizedMessage());
                showEmptyGridGalleryOne();
            }

            @Override
            public void empty() {
                Log.w(TAG, "Empty Grid Gallery One. No items.");
                showEmptyGridGalleryOne();
            }
        });

        gridViewGalleryOne.setOnItemClickListener((parent, view, position, id) -> {
            if (countSelected == 0) {
                // Show image full size
//                overlayView = new ImageOverlayView(activity, ImageOverlayView.MediaType.IMAGE);

                // TODO create delete from IMAGE object (me tab galley) (in ImageOverlayView)
                // TODO create delete from IMAGE object (me tab galley) (in ImageOverlayView)
                // TODO create delete from IMAGE object (me tab galley) (in ImageOverlayView)

//                ImageViewer iv = new ImageViewer.Builder(activity, galleryOneImageList)
//                        .setStartPosition(position)
//                        .setFormatter(new ImageViewer.Formatter<GazeImage>() {
//                            @Override
//                            public String format(GazeImage image) {
//                                return Uri.fromFile(new File(image.getPath())).toString();
//                            }
//                        })
//                        .allowSwipeToDismiss(true)
//                        .hideStatusBar(true)
//                        .setImageChangeListener(getImageChangeListener(galleryOneImageList))
//                        .setOverlayView(overlayView)
//                        .show();
//                overlayView.setImageViewer(iv);

//                    FullScreenViewActivity_.intent(activity).position(position).imageList(galleryOneImageList).start();
            } else {
                if (actionMode == null) {
                    actionMode = activity.startActionMode(actionModeCallbackGalleryOne);
                }

                toggleSelectionGalleryOne(position);
                actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));

                Log.d(TAG, "SINGLE CLICK Selection Size: " + getSelectedGalleryOne().size());

                if (countSelected == 0) {
                    actionMode.finish();
                }
            }
        });

        gridViewGalleryOne.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            Log.d(TAG, "LONG CLICK Selection Size: " + getSelectedGalleryOne().size());

            actionMode = activity.startActionMode(actionModeCallbackGalleryOne);

            toggleSelectionGalleryOne(position);
            actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));
            view.setSelected(true);

            return true;
        });

        gridViewGalleryTwo.setOnItemClickListener((parent, view, position, id) -> {
            if (countSelected == 0) {
                // Show image full size
                Intent intent = new Intent(activity, ContactFullScreenViewerActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("imageList", galleryTwoImageList);
                startActivity(intent);
            } else {
                if (actionMode == null) {
                    actionMode = activity.startActionMode(actionModeCallbackGalleryTwo);
                }

                toggleSelectionGalleryTwo(position);
                actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));

                Log.d(TAG, "SINGLE CLICK Selection Size: " + getSelectedGalleryTwo().size());

                if (countSelected == 0) {
                    actionMode.finish();
                }
            }
        });

        gridViewGalleryTwo.setOnItemLongClickListener((parent, view, position, id) -> {
            if (actionMode != null) {
                return false;
            }

            Log.d(TAG, "LONG CLICK Selection Size: " + getSelectedGalleryTwo().size());

            actionMode = activity.startActionMode(actionModeCallbackGalleryTwo);

            toggleSelectionGalleryTwo(position);
            actionMode.setTitle(countSelected + " " + getString(R.string.selected_media_counter));
            view.setSelected(true);

            return true;
        });
    }

    private void showEmptyGridGalleryOne() {
        //gridGalleryOuterLayoutOne.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        if (gridAdapterOne != null) {
            gridAdapterOne.notifyDataSetChanged();
        }

        progressBarOne.setVisibility(View.GONE);
        gridViewGalleryOne.setVisibility(View.GONE);
        noPhotosGalleryOne.setVisibility(View.VISIBLE);
        addImagesGalleryOne.setVisibility(View.VISIBLE);
        editImagesGalleryOne.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.button_add_images_gallery_one)
    void clickedAddImagesGalleryOne() {
        Log.d(TAG, "XXXXX clickedAddImagesGalleryOne()");


        activeGalleryNumber = GALLERY_ONE;
//        Intent intent = new Intent(activity, AlbumSelectActivity.class);
//        if (!GazeTools.isProUser()) {
//            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, GazeTools.getMaxImageSavesFreeVersion());
//        }
//        startActivityForResult(intent, Const.REQUEST_CODE_ADD_IMAGES_TO_GALLERY);


        // TODO FIXME hier wird noch com.github.darsh2:MultipleImageSelect verwendet, das ist aber nicht mehr kompatibel mit GLIDE 4 (a.k.a. lib ist tot)
        // TODO FIXME neuen MULTI MEDIA PICKER API finden und das damit ersetzen....
        // TODO FIXME in MediaTools.java müssen auch anpassungen gemacht werden...

        // TODO FIXME hier wird noch com.github.darsh2:MultipleImageSelect verwendet, das ist aber nicht mehr kompatibel mit GLIDE 4 (a.k.a. lib ist tot)
        // TODO FIXME neuen MULTI MEDIA PICKER API finden und das damit ersetzen....
        // TODO FIXME in MediaTools.java müssen auch anpassungen gemacht werden...

        // TODO FIXME hier wird noch com.github.darsh2:MultipleImageSelect verwendet, das ist aber nicht mehr kompatibel mit GLIDE 4 (a.k.a. lib ist tot)
        // TODO FIXME neuen MULTI MEDIA PICKER API finden und das damit ersetzen....
        // TODO FIXME in MediaTools.java müssen auch anpassungen gemacht werden...


        Intent intent = new Intent(activity, Gallery.class);
        intent.putExtra("GALLERY ONE TITLE", "Select media");
        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
        intent.putExtra("mode", 1);
        if (tools.isFreeUser()) {
            intent.putExtra(Const.INTENT_EXTRA_LIMIT, tools.getMaxImageSavesFreeVersion());
        }

        startActivityForResult(intent, OPEN_MEDIA_PICKER);
    }

    @OnClick(R.id.button_edit_images_gallery_one)
    void clîckedEditImagesGalleryOne() {
        Intent intent = new Intent(activity, MyMediaGridGalleryActivity.class);
        intent.putExtra("galleryNumber", GALLERY_ONE);
        intent.putExtra("imageList", galleryOneImageList);
        intent.putExtra("title", getString(R.string.gallery_one_edit_media));
        startActivityForResult(intent, Const.REQUEST_CODE_EDIT_IMAGES_GALLERY);
    }

    private ShareActionProvider mActionProvider;

    public ActionMode.Callback actionModeCallbackGalleryOne = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.d(TAG, "onCreateActionMode");
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.gridgallery_menu_share_selection_contextual_action_bar, menu);

            // WORKING CODE!!
            // WORKING CODE!!
            // WORKING CODE!!
            // Der Code würde eigentlich funktionieren, jedoch müsste man irgendwie an den ON MENU CLICK LISTENER kommen,
            // damit man erst beim Klick alle selektierten Images ausliest...
            // http://stackoverflow.com/questions/19118051/unable-to-cast-action-provider-to-share-action-provider
//            String myMediaPath = "/" + Const.GALLERY_MY_MEDIA_PATH + "/" + GALLERY_ONE + "/"; // i.e. /myMedia/1/
//            String internalStoragePath = activity.getFilesDir().getAbsolutePath();
//            File myMediaPathPrivateStorage = new File(internalStoragePath, myMediaPath); // i.e. /myMedia/1/
//            File fileToShare = new File(myMediaPathPrivateStorage, "Screenshot_20170217-165333.png"); // i.e. /myMedia/1/Screenshot_20170217-165333.png
//            File fileToShare2 = new File(myMediaPathPrivateStorage, "Screenshot_20170217-165321.png"); // i.e. /myMedia/1/Screenshot_20170217-165321.png
//
//            Uri contentUri = FileProvider.getUriForFile(activity, "net.gazeapp.MainActivity_", fileToShare);
//            Uri contentUri2 = FileProvider.getUriForFile(activity, "net.gazeapp.MainActivity_", fileToShare2);
//
//            List<Uri> selectedImages = getSelectedImagesAllGalleries();
//            for (Uri uri : selectedImages) {
//                Log.d(TAG, "MMMMM URI: " + uri.getPath());
//            }
//
//            ShareCompat.IntentBuilder shareIntent = ShareCompat.IntentBuilder.from(activity)
//                    .setType(activity.getContentResolver().getType(contentUri))
//                    .addStream(contentUri)
//                    .addStream(contentUri2);
//
//            MenuItem itemShare = menu.add(R.string.share);
//            ShareCompat.configureMenuItem(itemShare, shareIntent);
//            MenuItemCompat.setShowAsAction(itemShare, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
            // WORKING CODE!!
            // WORKING CODE!!
            // WORKING CODE!!

//            share = new ShareActionProvider(activity);
//            share.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
//                @Override
//                public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
//                    Log.d(TAG, "HHHHHHH");
//                    return false;
//                }
//            });

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
                    shareFromBothGalleries();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (countSelected > 0) {
                deselectAllGalleryOne();
                deselectAllGalleryTwo();
            }
            actionMode = null;
        }
    };

    public ActionMode.Callback actionModeCallbackGalleryTwo = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Log.d(TAG, "onCreateActionMode");
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.gridgallery_menu_share_selection_contextual_action_bar, menu);

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
                    shareFromBothGalleries();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (countSelected > 0) {
                deselectAllGalleryOne();
                deselectAllGalleryTwo();
            }
            actionMode = null;
        }
    };

    private void shareFromBothGalleries() {
        Log.d(TAG, "shareFromBothGalleries()");

        ShareCompat.IntentBuilder shareIntent = ShareCompat.IntentBuilder.from(activity);

        List<Uri> uris = getSelectedImagesAllGalleries();
        Log.d(TAG, "shareFromBothGalleries() uris size: " + uris.size());

        for (Uri uri : uris) {
            Log.d(TAG, "shareFromBothGalleries() URI: " + uri.getPath());
            shareIntent.setType(activity.getContentResolver().getType(uri));
            shareIntent.addStream(uri);
        }

        startActivity(shareIntent.getIntent());
    }

    private void toggleSelectionGalleryOne(int position) {
        // TODO
//        galleryOneImageList.get(position).isSelected = !galleryOneImageList.get(position).isSelected;
//
//        if (galleryOneImageList.get(position).isSelected) {
//            countSelected++;
//        } else {
//            countSelected--;
//        }
//        gridAdapterOne.notifyDataSetChanged();
    }

    private void toggleSelectionGalleryTwo(int position) {
        // TODO
//        galleryTwoImageList.get(position).isSelected = !galleryTwoImageList.get(position).isSelected;
//
//        if (galleryTwoImageList.get(position).isSelected) {
//            countSelected++;
//        } else {
//            countSelected--;
//        }
//        gridAdapterTwo.notifyDataSetChanged();
    }

    private void deselectAllGalleryOne() {
        // TODO
//        if (galleryOneImageList != null) {
//            for (int i = 0, l = galleryOneImageList.size(); i < l; i++) {
//                galleryOneImageList.get(i).isSelected = false;
//            }
//            countSelected = 0;
//            if (gridAdapterOne != null) {
//                gridAdapterOne.notifyDataSetChanged();
//            }
//        }
    }

    private void deselectAllGalleryTwo() {
        // TODO
//        if (galleryTwoImageList != null) {
//            for (int i = 0, l = galleryTwoImageList.size(); i < l; i++) {
//                galleryTwoImageList.get(i).isSelected = false;
//            }
//            countSelected = 0;
//            if (gridAdapterTwo != null) {
//                gridAdapterTwo.notifyDataSetChanged();
//            }
//        }

    }

    private List<GazeImage> getSelectedGalleryOne() {
        List<GazeImage> selectedImages = new ArrayList<>();

        // TODO
//        for (int i = 0, l = galleryOneImageList.size(); i < l; i++) {
//            if (galleryOneImageList.get(i).isSelected) {
//                selectedImages.add(galleryOneImageList.get(i));
//            }
//        }
        return selectedImages;
    }

    private List<GazeImage> getSelectedGalleryTwo() {
        List<GazeImage> selectedImages = new ArrayList<>();

        for (int i = 0, l = galleryTwoImageList.size(); i < l; i++) {
            if (galleryTwoImageList.get(i).isSelected()) {
                selectedImages.add(galleryTwoImageList.get(i));
            }
        }
        return selectedImages;
    }


    private List<Uri> getSelectedImagesGalleryOne() {
        Log.d(TAG, "getSelectedImagesGalleryOne()");
        List<Uri> uriList = new ArrayList<>();

        if (galleryOneImageList != null) {
            for (GazeImage image : galleryOneImageList) {
                Log.d(TAG, "getSelectedImagesGalleryOne image: " + image.getName() + " / " + image.isSelected());

                File file = new File(image.getPath());
                if (file.exists() && image.isSelected()) {
                    Uri contentUri = FileProvider.getUriForFile(activity, activity.getPackageName(), file);
                    uriList.add(contentUri);
                }
            }
        }

        return uriList;
    }

    private List<Uri> getSelectedImagesGalleryTwo() {
        Log.d(TAG, "getSelectedImagesGalleryTwo()");
        List<Uri> uriList = new ArrayList<>();

        if (galleryTwoImageList != null) {
            for (GazeImage image : galleryTwoImageList) {
                Log.d(TAG, "getSelectedImagesGalleryTwo image: " + image.getName() + " / " + image.isSelected());

                File file = new File(image.getPath());
                if (file.exists() && image.isSelected()) {
                    Uri contentUri = FileProvider.getUriForFile(activity, activity.getPackageName(), file);
                    uriList.add(contentUri);
                }
            }
        }
        return uriList;
    }

    private List<Uri> getSelectedImagesAllGalleries() {
        List<Uri> selectedImages = new ArrayList<>();
        selectedImages.addAll(getSelectedImagesGalleryOne());
        selectedImages.addAll(getSelectedImagesGalleryTwo());
        return selectedImages;
    }

    private void loadImages() {
        abortLoading();

        ImageLoaderRunnableOne runnable = new ImageLoaderRunnableOne();
        threadOne = new Thread(runnable);
        threadOne.start();
    }

    private void abortLoading() {
        if (threadOne == null) {
            return;
        }

        if (threadOne.isAlive()) {
            threadOne.interrupt();
            try {
                threadOne.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void loadGalleryTwo() {
        noPhotosGalleryTwo.setVisibility(View.GONE);
        progressBarTwo.setVisibility(View.VISIBLE);

        // TODO
//        mediaTools.getMyMediaFromInternalStorage(GALLERY_TWO, new MyMediaListLoadCallback() {
//
//            @Override
//            public void success(ArrayList<Image> mediaList) {
//
//                galleryTwoImageList = mediaList;
//
//                int mediaSize = mediaList.size();
//                int modulo = mediaSize % Const.GRID_GALLERY_COLUMNS;
//                if (modulo > 0) {
//                    mediaSize = mediaSize + (Const.GRID_GALLERY_COLUMNS - modulo);
//                }
//
//                int galleryRows = mediaSize / Const.GRID_GALLERY_COLUMNS;
//                int gridGalleryHeight = galleryRows * Const.GRID_GALLERY_ITEM_HEIGHT; // each row is 490px
//
//                // Adding 11dp to the grid height for some padding on the bottom side.
//                float gridGalleryHeightDp = GazeTools.convertDpToPx(activity, 11);
//                int adjustedGalleryHeight = Math.round(gridGalleryHeight + gridGalleryHeightDp);
//
//                gridGalleryOuterLayoutTwo.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, adjustedGalleryHeight));
//
//                gridAdapterTwo = new MyMediaGridViewAdapter(activity, R.layout.gridgallery_grid_item_layout, mediaList);
//                gridViewGalleryTwo.setAdapter(gridAdapterTwo);
//                orientationBasedUI(gridAdapterTwo, gridViewGalleryTwo, getResources().getConfiguration().orientation);
//
//                mediaTools.cleanMyMediaCacheDir();
//                gridAdapterTwo.notifyDataSetChanged();
//
//                progressBarTwo.setVisibility(View.GONE);
//                gridViewGalleryTwo.setVisibility(View.VISIBLE);
//                addImagesGalleryTwo.setVisibility(View.VISIBLE);
//                editImagesGalleryTwo.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void fail(Exception e) {
//                Log.e(TAG, "Error loading the Grid Gallery Two Content: " + e.getLocalizedMessage());
//                showEmptyGridGalleryTwo();
//            }
//
//            @Override
//            public void empty() {
//                Log.w(TAG, "Empty Grid Gallery Two. No items.");
//                showEmptyGridGalleryTwo();
//            }
//        });
    }

    @OnClick(R.id.gallery_two_show_button)
    void toggleGalleryTwoVisibility() {
        int newVisibility;
        if (cardViewGalleryTwo.getVisibility() == View.VISIBLE) {
            newVisibility = View.GONE;
            showGalleryTwoButton.setText(getString(R.string.show_second_gallery));
            deselectAllGalleryTwo();
        } else {
            newVisibility = View.VISIBLE;
            showGalleryTwoButton.setText(getString(R.string.hide_second_gallery));
        }

        cardViewGalleryTwo.setVisibility(newVisibility);
    }

    @OnClick(R.id.button_add_images_gallery_two)
    void clickedAddImagesGalleryTwo() {
        Log.d(TAG, "TODO: clickedAddImagesGalleryTwo()");

//        activeGalleryNumber = GALLERY_TWO;
//        Intent intent = new Intent(activity, AlbumSelectActivity.class);
//        if (!GazeTools.isProUser()) {
//            intent.putExtra(Constants.INTENT_EXTRA_LIMIT, Const.FREE_USER_MAXIMUM_IMAGES_MY_MEDIA_GALLERY);
//        }
//        startActivityForResult(intent, Const.REQUEST_CODE_ADD_IMAGES_TO_GALLERY);
    }


    private void showEmptyGridGalleryTwo() {
        //gridGalleryOuterLayoutTwo.setLayoutParams(new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
        if (gridAdapterTwo != null) {
            gridAdapterTwo.notifyDataSetChanged();
        }

        progressBarTwo.setVisibility(View.GONE);
        gridViewGalleryTwo.setVisibility(View.GONE);
        noPhotosGalleryTwo.setVisibility(View.VISIBLE);
        addImagesGalleryTwo.setVisibility(View.VISIBLE);
        editImagesGalleryTwo.setVisibility(View.VISIBLE);
    }

    @Subscribe
    public void onMediaAddedEvent(MediaAddedEvent result) {
        // TODO
//        // New Images selected to add to the "My Media" gallery 1 or 2
//        List<Image> images = result.getImages();
//
//        if ((images != null) && (images.size() > 0)) {
//            Log.d(TAG, "MediaAddedEvent received with " + images.size() + " new images.");
//
//            if (galleryOneImageList == null) {
//                galleryOneImageList = new ArrayList<>();
//            }
//            if (galleryTwoImageList == null) {
//                galleryTwoImageList = new ArrayList<>();
//            }
//
//            int successCount = 0;
//            boolean pass;
//
//            if (activeGalleryNumber == GALLERY_ONE) {
//                for (Image image : images) {
//                    pass = galleryOneImageList.size() < Const.FREE_USER_MAXIMUM_IMAGES_MY_MEDIA_GALLERY;
//                    if (GazeTools.isProUser()) {
//                        pass = true;
//                    }
//
//                    if (pass) {
//                        boolean copySuccess = mediaTools.copyMyMediaFileToPrivateStorage(image, activeGalleryNumber);
//                        if (copySuccess) {
//                            successCount++;
//                            galleryOneImageList.add(image);
//                        }
//                    } else {
//                        freeVersionDialogs.showMyMediaImageSavesLimitReached();
//                        break;
//                    }
//                }
//            } else if (activeGalleryNumber == GALLERY_TWO) {
//                for (Image image : images) {
//                    pass = galleryTwoImageList.size() < Const.FREE_USER_MAXIMUM_IMAGES_MY_MEDIA_GALLERY;
//                    if (GazeTools.isProUser()) {
//                        pass = true;
//                    }
//
//                    if (pass) {
//                        boolean copySuccess = mediaTools.copyMyMediaFileToPrivateStorage(image, activeGalleryNumber);
//                        if (copySuccess) {
//                            successCount++;
//                            galleryTwoImageList.add(image);
//                        }
//                    } else {
//                        freeVersionDialogs.showMyMediaImageSavesLimitReached();
//                        break;
//                    }
//                }
//            }
//
//            if (successCount > 0) {
//                String snackBarSuccessMessage = getResources().getString(R.string.success_copied_files_to_my_media_gallery, successCount, (activeGalleryNumber == 1 ? getResources().getString(R.string.one) : getResources().getString(R.string.two)));
//                GazeTools.showMaterialSnackBar(activity, scrollView, snackBarSuccessMessage, SnackBarType.SUCCESS);
//            }
//        }
//
//        updateUI();
    }

    @OnClick(R.id.button_edit_images_gallery_two)
    void clîckedEditImagesGalleryTwo() {
        Intent intent = new Intent(activity, MyMediaGridGalleryActivity.class);
        intent.putExtra("galleryNumber", GALLERY_TWO);
        intent.putExtra("imageList", galleryTwoImageList);
        intent.putExtra("title", getString(R.string.gallery_two_edit_media));
        startActivityForResult(intent, Const.REQUEST_CODE_EDIT_IMAGES_GALLERY);
    }

//    private ImageViewer.OnImageChangeListener getImageChangeListener(final ArrayList<GazeImage> mediaList) {
//        return new ImageViewer.OnImageChangeListener() {
//            @Override
//            public void onImageChange(int position) {
//                overlayView.setImage(mediaList.get(position));
//            }
//        };
//    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void showExplanationDialog() {
        try {
            if (!tools.hasSeenMeTabExplanationDialog()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.GazeAppTheme);
                builder.setTitle(R.string.me_tab_explanation_dialog_title);
                builder.setMessage(getActivity().getString(R.string.me_tab_explanation_dialog_text_pre));
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "CLICK BUTTON");
                        tools.setHasSeenMeTabExplanationDialog(true);
                    }

                });
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } catch (NullPointerException e) {
            // Just ignore the error. The popup is not that important.
            Log.e(TAG, "Checking myPrefs.hasSeenMeTabExplanationDialog() has failed: " + e.getLocalizedMessage());
        }
    }

    private void orientationBasedUI(MyMediaGridViewAdapter gridAdapter, GridView gridViewGallery, int orientation) {
        final WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);

        if (gridAdapter != null) {
            int size = orientation == Configuration.ORIENTATION_PORTRAIT ? metrics.widthPixels / 3 : metrics.widthPixels / 5;
            gridAdapter.setLayoutParams(size);
        }
        gridViewGallery.setNumColumns(orientation == Configuration.ORIENTATION_PORTRAIT ? 3 : 5);
    }

    @OnClick(R.id.button_view_profile)
    void clickedButtonViewProfile() {
        // TODO
        //ContactViewWithViewPagerTabActivity_.intent(activity).contactId(contact.getId()).start();
    }

    @OnClick(R.id.button_edit_profile)
    void clickedButtonEdit() {
        getMe();
        Log.d(TAG, "Creating a new ME Contact");
        if (contact == null) {
            contact = new Contact(getString(R.string.me));
            contact.setMe(true);
            contact.setLastMod(new Date());
            contact.setCreated(new Date());

            // TODO
            //AddContactActivity_.intent(activity).contact(contact).startForResult(Const.REQUEST_CODE);
        } else {
            Log.d(TAG, "Editing the Me Contact");

            // TODO
            //EditContactActivity_.intent(activity).contact(contact).startForResult(Const.REQUEST_CODE);
        }
    }

    private void updateMeCardLayout() {
        String age = null;
        String height = null;
        String weight = null;

        // Use metric system?
        boolean useMetricSystem = tools.useMetricSystem();
        Log.d(TAG, "useMetricSystem: " + useMetricSystem);

        if (mPersonal != null) {
            if (mPersonal.getAge() != 0) {
                age = activity.getString(R.string.years_short, mPersonal.getAge());
            }

            // If birthdate is known calculate the age from this
            try {
                age = activity.getString(R.string.years_short, tools.getAge(mPersonal.getBirthdate()));
            } catch (Exception ne) {
                Log.w(TAG, "AGE for " + contact.getContactName() + " is NULL");
            }

            try {
                height = tools.convertHeightFromCentimetersToReadableFormat(mBody.getHeight(), useMetricSystem);
            } catch (Exception ne) {
                Log.w(TAG, "HEIGHT for " + contact.getContactName() + " is NULL");
            }

            try {
                weight = tools.convertWeightFromGramsToReadableFormat(mBody.getWeight(), useMetricSystem);
            } catch (Exception ne) {
                Log.w(TAG, "WEIGHT for " + contact.getContactName() + " is NULL");
            }
        }

        ArrayList<String> ageHeightWeightList = new ArrayList<>();
        if ((age != null) && (age.length() > 0)) {
            ageHeightWeightList.add(age);
        }
        if ((height != null) && (height.length() > 3)) {
            ageHeightWeightList.add(height);
        }
        if ((weight != null) && (weight.length() > 3)) {
            ageHeightWeightList.add(weight);
        }
        String basicInfoImploded = TextUtils.join(" / ", ageHeightWeightList);

        // If AGE, HEIGHT, WEIGHT not set, display n/a
        if (basicInfoImploded.trim().length() < 1) {
            basicInfoImploded = activity.getResources().getString(R.string.not_available);
        }
        basicInfo.setText(basicInfoImploded);

        /*
         * ENDOWMENT / ROLE
         */
        ArrayList<String> endowmentRoleList = new ArrayList<>();
        String endowmentLength = null;

        // TODO get endowment, xxx, note etc...
//        EndowmentEntity endowment = contact.getEndowment();
//        if (endowment != null) {
//            endowmentLength = GazeTools.convertFromMillimetersToReadableFormat(endowment.getLength(), useMetricSystem);
//        }
//        if ((endowmentLength != null) && (endowmentLength.length() > 3)) {
//            endowmentRoleList.add(endowmentLength);
//        }
//
//        Xxx xxx = contact.getXxx();
//        if (xxx != null) {
//            if ((xxx.getSexRole() != null) && (xxx.getSexRole().length() > 0)) {
//                endowmentRoleList.add(xxx.getSexRole());
//            }
//        }
//
//        // If no ENDOWMENT or ROLE data, then display n/a
//        String xxxInfoImploded = TextUtils.join(" / ", endowmentRoleList);
//        if (xxxInfoImploded.trim().length() < 3) {
//            xxxInfoImploded = activity.getResources().getString(R.string.not_available);
//        }
//        xxxInfo.setText(xxxInfoImploded);
//
//        Note myNotes = contact.getNote();
//        if ((myNotes != null) && (myNotes.getNote().length() > 0)) {
//            note.setText(myNotes.getNote());
//        } else {
//            note.setVisibility(View.GONE);
//        }
//
//        contactName.setText(contact.getContactName());
//        additionalInfo.setText(contact.getAdditionalInfo());
//
//        File fileFromInternalStorage = mediaTools.getFileFromInternalStorage(contact, Const.GALLERY_PUBLIC);
//        Glide.with(activity).load(fileFromInternalStorage).placeholder(R.drawable.silhouette).into(cardImage);
    }

    private void getMe() {
        // TODO get contact through contactDAO
//        try {
//            contact = contactService.getMe();
//            mPersonal = contact.getPersonal();
//            mBody = contact.getBody();
//        } catch (Exception | ItemNotFoundException e) {
//            // No ME Contact created yet.
//            contact = null;
//            Log.e(TAG, "Exception: " + e.getLocalizedMessage());
//        }
    }

    private class ImageLoaderRunnableOne implements Runnable {
        @Override
        public void run() {
            // TODO
//            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//
//            Message message;
//            if (gridAdapterOne == null) {
//                message = handlerOne.obtainMessage();
//                /*
//                If the adapter is null, this is first time this activity's view is
//                being shown, hence send FETCH_STARTED message to show progress bar
//                while images are loaded from phone
//                 */
//                message.what = Constants.FETCH_STARTED;
//                message.sendToTarget();
//            }
//
//            if (Thread.interrupted()) {
//                return;
//            }
//
//
//            File file;
//            HashSet<Long> selectedImages = new HashSet<>();
//            if (galleryOneImageList != null) {
//                Image image;
//                for (int i = 0, l = galleryOneImageList.size(); i < l; i++) {
//                    image = galleryOneImageList.get(i);
//                    file = new File(image.path);
//                    if (file.exists() && image.isSelected) {
//                        selectedImages.add(image.id);
//                    }
//                }
//            }
//
//            if ((galleryOneImageList == null) || (galleryOneImageList.size() == 0)) {
//                message = handlerOne.obtainMessage();
//                message.what = Const.ERROR;
//                message.sendToTarget();
//                return;
//            }
//
//            /*
//            In case this runnable is executed to onChange calling loadImages,
//            using countSelected variable can result in a race condition. To avoid that,
//            tempCountSelected keeps track of number of selected images. On handling
//            FETCH_COMPLETED message, countSelected is assigned value of tempCountSelected.
//             */
//            int tempCountSelected = 0;
//            ArrayList<Image> temp = new ArrayList<>(galleryOneImageList.size());
//
//            for (Image imageItem : galleryOneImageList) {
//                boolean isSelected = selectedImages.contains(imageItem.id);
//                if (isSelected) {
//                    tempCountSelected++;
//                }
//
//                file = new File(imageItem.path);
//                if (file.exists()) {
//                    temp.add(imageItem);
//                }
//            }
//
//            if (galleryOneImageList == null) {
//                galleryOneImageList = new ArrayList<>();
//            }
//
//            galleryOneImageList.clear();
//            galleryOneImageList.addAll(temp);
//
//            message = handlerOne.obtainMessage();
//            message.what = Constants.FETCH_COMPLETED;
//            message.arg1 = tempCountSelected;
//            message.sendToTarget();
//
//            Thread.interrupted();
        }

    }
}
