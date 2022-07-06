package net.gazeapp.ui.fileimport;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import net.gazeapp.R;
import net.gazeapp.data.GazeDatabase;
import net.gazeapp.data.dao.ContactKtDao;
import net.gazeapp.data.model.Contact;
import net.gazeapp.utilities.MediaTools;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class AllContactsForFileImportFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private List<Contact> allContactsFromDatabase;
    private AppCompatActivity parentActivity;

    private AllContactsForFileImportListAdapter allContactsForFileImportListAdapter;
    private ArrayList<Uri> filesToImport;

    MediaTools mediaTools;

//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
//    @BindView(R.id.no_contacts_layout)
//    RelativeLayout noContactsLayout;
//    @BindView(contactsRecyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    public AllContactsForFileImportFragment() {
        mediaTools = new MediaTools();
    }

    public static AllContactsForFileImportFragment newInstance(ArrayList<Uri> filesToImport) {
        AllContactsForFileImportFragment addOrEditContactFragment = new AllContactsForFileImportFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList("filesToImport", filesToImport);
        addOrEditContactFragment.setArguments(args);

        return addOrEditContactFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_or_edit_contact, container, false); // falsch
//        View view = inflater.inflate(R.layout.fragment_all_contacts_for_file_import, container, false);
//        ButterKnife.bind(this, view);

        this.filesToImport = getArguments().getParcelableArrayList("filesToImport");

        // Hide Keyboard initially
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

//        progressBar.setVisibility(View.VISIBLE);

        parentActivity = (AppCompatActivity) getActivity();
//        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));

        allContactsFromDatabase = getContactsDataSet();

        updateUI();

        return view;
    }

    void updateUI() {
//        Needle.onMainThread().execute(() -> {
//            if ((allContactsFromDatabase != null) & (allContactsFromDatabase.size() > 0)) {
//                allContactsForFileImportListAdapter = new AllContactsForFileImportListAdapter(parentActivity, allContactsFromDatabase, mediaTools, filesToImport);
//                recyclerView.setAdapter(allContactsForFileImportListAdapter);
//
//                recyclerView.setVisibility(View.VISIBLE);
//                noContactsLayout.setVisibility(View.GONE);
//                progressBar.setVisibility(View.GONE);
//            } else {
//                noContactsLayout.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.GONE);
//            }
//        });
    }

    /**
     * Getting all Contacts from the Database ordered alphabetically
     *
     * @return List<Contact>
     */
    private List<Contact> getContactsDataSet() {
        ContactKtDao contactDao = GazeDatabase.Companion.getDatabase(getActivity()).getContactKtDao();
        // TODO FIXME
//        return contactDao.getAll();

        return new ArrayList<>();
    }

}
