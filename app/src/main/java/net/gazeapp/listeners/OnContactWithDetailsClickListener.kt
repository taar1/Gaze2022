package net.gazeapp.listeners

import net.gazeapp.data.model.ContactWithDetails

interface OnContactWithDetailsClickListener {
    fun onContactWithDetailsClicked(contact: ContactWithDetails, position: Int)
}