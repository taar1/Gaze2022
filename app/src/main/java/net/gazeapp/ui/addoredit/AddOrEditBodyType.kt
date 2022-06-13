package net.gazeapp.ui.addoredit

import android.view.View
import net.gazeapp.data.dto.BodyTypeDto
import net.gazeapp.dialog.BodyTypeSelectionCustomDialog

class AddOrEditBodyType(
    val f: AddOrEditContactFragment
) {
    fun displayBodyTypeList(bodyTypeList: List<BodyTypeDto>?) {
        f.bodyTypesArrayList = bodyTypeList
        f.bodyType.setText(bodyTypeList?.map {
            it.bodyTypeStr
        }?.joinToString { ", " })

        if (f.bodyType.text.toString().trim { it <= ' ' }.isNotEmpty()) {
            f.deleteBodyType.visibility = View.VISIBLE
        } else {
            f.deleteBodyType.visibility = View.INVISIBLE
        }
    }

    fun bodyTypeClick() {
        // TODO FIXME methode auskommentiert aktuell...
        // TODO FIXME methode auskommentiert aktuell...
        val bodyTypes = f.tools.readJsonFileFromRawFolder(
            "bodytypes", true
        )
        val preselectedBodyTypeList = ArrayList<BodyTypeDto>()
        for ((key, value) in bodyTypes) {
            val bodytypeDto = BodyTypeDto()
            bodytypeDto.bodytypeId = key
            bodytypeDto.bodyTypeStr = value

            // Check from the previous selection which bodytypes have been selected
            f.bodyTypesArrayList?.let {
                for ((_, _, bodytypeId) in it) {
                    if (bodytypeId == bodytypeDto.bodytypeId) {
                        bodytypeDto.isSelected = true
                    }
                }
            }
            preselectedBodyTypeList.add(bodytypeDto)
        }

        BodyTypeSelectionCustomDialog(
            f.requireActivity(), f.viewModel, f.viewBinding.scrollView, preselectedBodyTypeList
        ).show()
    }

    fun deleteBodyTypeClicked() {
        f.bodyTypesArrayList = null
        f.bodyType.setText("")
        f.updateContactInDatabase()
    }
}