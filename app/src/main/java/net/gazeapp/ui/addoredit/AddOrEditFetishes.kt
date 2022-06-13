package net.gazeapp.ui.addoredit

import android.view.View

class AddOrEditFetishes(
    val f: AddOrEditContactFragment
) {
    fun displayFetishes() {
        f.fetishes.setText(f.contactWithDetails.fetishes?.joinToString(", "))
        if (f.fetishes.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            f.deleteFetishes.visibility = View.VISIBLE
        }
    }

}