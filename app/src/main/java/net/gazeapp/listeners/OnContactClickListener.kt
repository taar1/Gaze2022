package net.gazeapp.listeners

import net.gazeapp.data.model.Contact

interface OnContactClickListener {
    fun onContactClicked(contact: Contact, position: Int)
}