package net.gazeapp.ui.addoredit

import android.view.View

class AddOrEditHealth(
    val f: AddOrEditContactFragment
) {
    companion object {
        private const val TAG = "AddOrEditWebsite"
    }

    fun displayHealth() {
        f.contactWithDetails.health?.let { health ->

            if (health.hiv.isNullOrEmpty()) {
                f.hiv.setText(health.hiv)
                f.deleteHiv.visibility = View.VISIBLE
            }

            if (health.hcv.isNullOrEmpty()) {
                f.hcv.setText(health.hcv)
                f.deleteHcv.visibility = View.VISIBLE
            }
        }
    }
}