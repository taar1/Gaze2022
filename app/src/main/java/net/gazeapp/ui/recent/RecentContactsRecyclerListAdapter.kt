package net.gazeapp.ui.recent

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recent_contacts_card, parent, false)
        return RecentContactsCardViewItemHolder(activity, itemView, tools)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        val contactCardObjectViewHolder = viewHolder as RecentContactsCardViewItemHolder

        contactCardObjectViewHolder.contact = contact
//        contactCardObjectViewHolder.mediaTools = MediaTools()
//        contactCardObjectViewHolder.gazeTools = GazeTools(activity)

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