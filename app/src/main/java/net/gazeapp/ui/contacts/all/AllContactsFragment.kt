package net.gazeapp.ui.contacts.all

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
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.ContactsRecyclerlistFragmentBinding
import net.gazeapp.listeners.OnContactWithDetailsClickListener
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import javax.inject.Inject

@AndroidEntryPoint
class AllContactsFragment : Fragment(R.layout.contacts_recyclerlist_fragment),
    OnContactWithDetailsClickListener {

    private val TAG = "AllContactsFragmentKt"

    @Inject
    lateinit var gazeTools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    companion object {
        fun newInstance() = AllContactsFragment()
    }

    private val viewModel: AllContactsViewModel by viewModels()

    private lateinit var viewBinding: ContactsRecyclerlistFragmentBinding

    private lateinit var inputMethodManager: InputMethodManager

    private lateinit var allContactsCardViewRecyclerAdapter: AllContactsRecyclerListAdapterKt

    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactsRecyclerlistFragmentBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNavController(view)
        initUi()

//        viewModel.allContacts.observe(viewLifecycleOwner, { allContacts ->
//            Log.d(TAG, "XXXXX onViewCreated: allContacts: $allContacts")
//            allContacts.apply {
//                allContactsCardViewRecyclerAdapter.contactList = allContacts
//                allContactsCardViewRecyclerAdapter.notifyDataSetChanged()
//            }
//        })

        viewModel.allContactsWithDetails.observe(viewLifecycleOwner) { allFullContacts ->
            Log.d(TAG, "XXXXX onViewCreated: allContactsWithDetails: $allFullContacts")
            allFullContacts.apply {
                allContactsCardViewRecyclerAdapter.contactList = allFullContacts
                allContactsCardViewRecyclerAdapter.notifyDataSetChanged()
            }
        }

        viewModel.hasNoEntries.observe(viewLifecycleOwner) { hasNoEntries ->
            if (hasNoEntries) {
                viewBinding.noContactsLayoutGroup.visibility = View.VISIBLE
            } else {
                viewBinding.noContactsLayoutGroup.visibility = View.GONE
            }
        }
    }

    private fun initUi() {
        val view = requireActivity().currentFocus

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

        allContactsCardViewRecyclerAdapter =
            AllContactsRecyclerListAdapterKt(this, requireActivity())

        viewBinding.contactsRecyclerView.apply {
            adapter = allContactsCardViewRecyclerAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Set Toolbar Title
        viewBinding.toolbar.title = getString(R.string.all)

        viewBinding.noContactsLayoutGroup.visibility = View.GONE
    }

    private fun initNavController(view: View) {
        navController = Navigation.findNavController(view)
    }

    override fun onContactWithDetailsClicked(
        contact: ContactWithDetails, position: Int
    ) {

        // TODO go to detail view
//        val action =
//            AllContactsFragmentDirections.actionNavAllToContactViewWithViewPagerTabActivity(
//                contact.contact.id
//            )
//        navController.navigate(action)
    }
}