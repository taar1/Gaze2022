package net.gazeapp.ui.recent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.ContactsRecyclerlistFragmentBinding
import net.gazeapp.listeners.OnContactWithDetailsClickListener
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import javax.inject.Inject

@AndroidEntryPoint
class RecentContactsFragment : Fragment(R.layout.contacts_recyclerlist_fragment),
    OnContactWithDetailsClickListener {

    @Inject
    lateinit var gazeTools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    private val viewModel: RecentContactsViewModel by viewModels()

    private lateinit var viewBinding: ContactsRecyclerlistFragmentBinding

    private lateinit var inputMethodManager: InputMethodManager

    private lateinit var recentContactsCardViewRecyclerAdapter: RecentContactsRecyclerListAdapter

    private lateinit var navController: NavController
    private lateinit var fab: FloatingActionButton

    companion object {
        private const val TAG = "RecentContactsFragment"

        fun newInstance() = RecentContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactsRecyclerlistFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initNavController(view)

        viewModel.recentContactsWithDetails.observe(viewLifecycleOwner) { recentFullContacts ->
            Log.d(TAG, "XXXXX onViewCreated: recentFullContacts: $recentFullContacts")

            isEmptyContactList(false)
            recentFullContacts.apply {
                recentContactsCardViewRecyclerAdapter.contactList = recentFullContacts
                recentContactsCardViewRecyclerAdapter.notifyDataSetChanged()
            }
        }

        viewModel.hasNoEntries.observe(viewLifecycleOwner) { hasNoEntries ->
            Log.d(TAG, "XXXXX onViewCreated: hasNoEntries: $hasNoEntries")
            isEmptyContactList(hasNoEntries)
        }
    }

    private fun isEmptyContactList(isEmptyList: Boolean) {
        if (isEmptyList) {
            viewBinding.noContactsLayoutGroup.visibility = View.VISIBLE
            viewBinding.contactsRecyclerView.visibility = View.GONE
        } else {
            viewBinding.noContactsLayoutGroup.visibility = View.GONE
            viewBinding.contactsRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun initUi() {
        val view = requireActivity().currentFocus

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

        recentContactsCardViewRecyclerAdapter =
            RecentContactsRecyclerListAdapter(this, requireActivity())

        viewBinding.contactsRecyclerView.apply {
            adapter = recentContactsCardViewRecyclerAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Set Toolbar Title
        viewBinding.toolbar.title = getString(R.string.recently_viewed)

        viewBinding.noContactsLayoutGroup.visibility = View.GONE

        viewBinding.floatingButton.setOnClickListener {
            onAddNewContactButtonClicked()
        }
    }

    private fun initNavController(view: View) {
        navController = Navigation.findNavController(view)
    }

    override fun onContactWithDetailsClicked(
        contact: ContactWithDetails, position: Int
    ) {
        // TODO FIXME
//        val action =
//            RecentContactsFragmentDirections.actionNavRecentToContactViewWithViewPagerTabActivity(
//                contact.contact.id
//            )
//        navController.navigate(action)
    }

    fun onAddNewContactButtonClicked() {
        // TODO FIXME

//        val action =
//            RecentContactsFragmentDirections.actionNavRecentToAddOrEditContactFragment(0)
//        navController.navigate(action)
    }
}