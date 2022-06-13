package net.gazeapp.ui.addoredit

import android.view.View

class AddOrEditEthnicity(
    val f: AddOrEditContactFragment
) {
    fun displayEthnicity() {
        f.ethnicity.setText(f.contactWithDetails.ethnicities?.joinToString(", "))
        if (f.ethnicity.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            f.deleteEthnicity.visibility = View.VISIBLE
        }
    }

}