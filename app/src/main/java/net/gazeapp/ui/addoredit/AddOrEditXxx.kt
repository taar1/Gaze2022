package net.gazeapp.ui.addoredit

import androidx.core.view.isVisible
import net.gazeapp.data.dto.XxxDto

class AddOrEditXxx(
    val f: AddOrEditContactFragment
) {
    fun displayXxx(xxx: XxxDto) {
        with(f) {
            sexualOrientation.setText(xxx.sexualOrientation)
            sexRole.setText(xxx.sexRole)
            safeSex.setText(xxx.safeSex)
            swallowsLoads.setText(xxx.swallowsLoads)
            loadsUpTheBum.setText(xxx.takesLoadsUpTheBum)

            deleteSexualOrientation.isVisible = !xxx.sexualOrientation.isNullOrEmpty()
            deleteSexRole.isVisible = !xxx.sexRole.isNullOrEmpty()
            deleteSafeSex.isVisible = !xxx.safeSex.isNullOrEmpty()
            deleteSwallowsLoads.isVisible = !xxx.swallowsLoads.isNullOrEmpty()
            deleteUpTheBum.isVisible = !xxx.takesLoadsUpTheBum.isNullOrEmpty()
        }
    }
}