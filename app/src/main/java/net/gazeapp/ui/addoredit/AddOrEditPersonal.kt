package net.gazeapp.ui.addoredit

import android.view.View
import net.gazeapp.R

class AddOrEditPersonal(
    val f: AddOrEditContactFragment
) {
    fun displayAge() {
        f.contactWithDetails.personal?.age.let {
            if (f.contactWithDetails.personal!!.age > 0) {
                f.birthdate.setText("")
                f.age.setText(f.contactWithDetails.personal!!.age.toString())
                f.age.setHint(R.string.age)
                if (f.age.text!!.isNotEmpty()) {
                    f.deleteAge.visibility = View.VISIBLE
                }
            }
        }
    }

    fun displayRelationship() {
        f.relationshipStatus.setText(f.contactWithDetails.personal?.relationshipStatus)
        if (f.relationshipStatus.text!!.isNotEmpty()) {
            f.deleteRelationshipStatus.visibility = View.VISIBLE
        }
    }

    // TODO FIXME
    // TODO FIXME
    // TODO FIXME
    // TODO FIXME
    // TODO FIXME
    // TODO FIXME
    fun displayChildren() {
//        f.hasChildren.setText(f.contactWithDetails.personal?.children)
//        if (f.hasChildren.text!!.isNotEmpty()) {
//            f.deleteChildren.visibility = View.VISIBLE
//        }
    }

    fun displayEffeminate() {
        var eff = 0
        f.contactWithDetails.personal?.effeminate?.let { eff = it }
        f.effeminateSlider.progress = eff

        // TODO schöneren code irgendwie hinkriegen?
        if (eff > 0) {
            f.effeminateUnknown.isChecked = false
            f.effeminateSlider.isEnabled = true
        } else {
            f.effeminateUnknown.isChecked = true
            f.effeminateSlider.isEnabled = false
        }
    }

    fun displayBirthdate() {
        f.contactWithDetails.personal?.birthdate.let {
            f.birthdate.setText(
                f.tools.formatDateToPhoneLocale(
                    f.dateFormatter.format(it)
                )
            )
            f.age.hint = f.tools.getAge(it!!).toString()
            if (f.birthdate.text?.isNotEmpty() == true) {
                f.deleteBirthdate.visibility = View.VISIBLE
            }
        }
    }
}