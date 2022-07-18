package net.gazeapp.ui.recent

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.ListItemRecentContactsCardBinding
import net.gazeapp.listeners.OnContactWithDetailsClickListener
import net.gazeapp.utilities.GazeTools

class RecentContactsRecyclerListAdapter(
    private val onContactClickListener: OnContactWithDetailsClickListener,
    private val activity: Activity,
    val tools: GazeTools
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "RecentContactsRecyclerL"
    }

    var contactList: List<ContactWithDetails> = mutableListOf()

    private var _binding: ListItemRecentContactsCardBinding? = null
    private val binding get() = _binding!!

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        val itemView: View = LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_item_recent_contacts_card, parent, false)
//        return RecentContactsCardViewItemHolder(activity, itemView, tools)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecentContactsCardViewItemHolder {
        _binding = ListItemRecentContactsCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RecentContactsCardViewItemHolder(activity, binding, tools)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        val contactCardObjectViewHolder = viewHolder as RecentContactsCardViewItemHolder

        contactCardObjectViewHolder.contact = contact
        contactCardObjectViewHolder.itemView.setOnClickListener {
            onContactClickListener.onContactWithDetailsClicked(
                contact,
                position
            )
        }
        contactCardObjectViewHolder.updateUI()
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}