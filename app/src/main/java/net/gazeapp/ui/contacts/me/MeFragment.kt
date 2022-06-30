package net.gazeapp.ui.contacts.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.ContactsRecyclerlistFragmentBinding
import net.gazeapp.listeners.OnContactWithDetailsClickListener
import net.gazeapp.ui.contactview.ContactViewWithViewPagerTabActivity
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import javax.inject.Inject

@AndroidEntryPoint
class MeFragment : Fragment(R.layout.layout_me_tab),
    OnContactWithDetailsClickListener {

    private val TAG = "RecentContactsFragment"

    @Inject
    lateinit var gazeTools: GazeTools

    @Inject
    lateinit var mediaTools: MediaTools

    companion object {
        fun newInstance() = MeFragment()
    }

    private val viewModel: MeViewModel by viewModels()

    private lateinit var viewBinding: ContactsRecyclerlistFragmentBinding

    private lateinit var inputMethodManager: InputMethodManager

    private lateinit var myMediaRecyclerGridViewAdapter1: MyMediaRecyclerGridViewAdapter
    private lateinit var myMediaRecyclerGridViewAdapter2: MyMediaRecyclerGridViewAdapter

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

        viewModel.recentContactsWithDetails.observe(viewLifecycleOwner, { recentFullContacts ->
            Log.d(TAG, "XXXXX onViewCreated: recentFullContacts: $recentFullContacts")
            recentFullContacts.apply {
                myMediaRecyclerGridViewAdapter1.contactList = recentFullContacts
                myMediaRecyclerGridViewAdapter1.notifyDataSetChanged()
            }
        })

        viewModel.hasNoEntries.observe(viewLifecycleOwner, { hasNoEntries ->
            if (hasNoEntries) {
                viewBinding.noContactsLayoutGroup.visibility = View.VISIBLE
            } else {
                viewBinding.noContactsLayoutGroup.visibility = View.GONE
            }
        })
    }

    private fun initUi() {
        val view = requireActivity().currentFocus

        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)

        myMediaRecyclerGridViewAdapter1 =
            MyMediaRecyclerGridViewAdapter(this, requireActivity())

        viewBinding.contactsRecyclerView.apply {
            adapter = myMediaRecyclerGridViewAdapter1
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            itemAnimator?.removeDuration = 50
            setHasFixedSize(true)
        }

        // Set Toolbar Title
        viewBinding.toolbar.title = getString(R.string.recently_viewed)

        viewBinding.noContactsLayoutGroup.visibility = View.GONE
    }

    override fun onContactWithDetailsClicked(
        contactWithDetails: ContactWithDetails,
        position: Int
    ) {
        val intent = Intent(activity, ContactViewWithViewPagerTabActivity::class.java)
        intent.putExtra("contactId", contactWithDetails.contact.id)
        requireActivity().startActivity(intent)
    }
}