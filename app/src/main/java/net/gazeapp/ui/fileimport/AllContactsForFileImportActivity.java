package net.gazeapp.ui.fileimport;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllContactsForFileImportActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    ArrayList<Uri> filesToImport;

//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.fab)
//    FloatingActionButton fab;

    // TODO FIXME schauen wie das korrekt funktionieren müsste
//    @FragmentById(R.id.fragment)
    AllContactsForFileImportFragment allContactsForFileImportFragment;

//    @BindView(R.id.banner_container)
//    LinearLayout facebookBanner;
//    private AdView adView;
//
//    private GazeTools tools;
//
//    @OnClick(R.id.fab)
//    void clickedFloatingActionButton() {
//        Log.d(TAG, "CLICKED FAB");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_contacts_for_file_import);
//        ButterKnife.bind(this);
//
//        tools = new GazeTools(this);

        filesToImport = getIntent().getParcelableExtra("filesToImport");

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Send URI fileList down to the fragment
        allContactsForFileImportFragment = AllContactsForFileImportFragment.newInstance(filesToImport);
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment, allContactsForFileImportFragment).commit();

        initFacebookAds();
        displayAds();
    }

    private void displayAds() {
//        if (tools.isProUser()) {
//            if (tools.isAdsEnabled()) {
//                adView.loadAd();
//                facebookBanner.setVisibility(View.VISIBLE);
//            } else {
//                facebookBanner.setVisibility(View.GONE);
//            }
//        } else {
//            adView.loadAd();
//            facebookBanner.setVisibility(View.VISIBLE);
//        }
    }

    private void initFacebookAds() {
//        adView = new AdView(this, Const.FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID, AdSize.BANNER_HEIGHT_50);
//        facebookBanner.addView(adView);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (adView != null) {
//            adView.destroy();
//        }
//    }

}
