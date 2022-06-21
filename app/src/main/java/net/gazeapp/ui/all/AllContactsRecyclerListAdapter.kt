package net.gazeapp.ui.all

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.gazeapp.R
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.listeners.OnContactWithDetailsClickListener
import net.gazeapp.utilities.MediaTools

class AllContactsRecyclerListAdapterKt(
    private val onContactClickListener: OnContactWithDetailsClickListener,
    private val activity: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "AllContactsRecyclerList"
    }

    var contactList: List<ContactWithDetails> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_all_contacts, parent, false)

        return AllContactsCardViewItemHolderKt(activity, itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val contact = contactList[position]
        val contactCardObjectViewHolder = viewHolder as AllContactsCardViewItemHolderKt

        contactCardObjectViewHolder.contactWithDetails = contact
        contactCardObjectViewHolder.mediaTools = MediaTools()

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