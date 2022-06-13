package net.gazeapp.ui.addoredit

import net.gazeapp.data.dto.EndowmentDto

class AddOrEditEndowment(
    val f: AddOrEditContactFragment
) {
    fun displayEndowment(endowmentDto: EndowmentDto) {
        // TODO input listeners
        with(f) {
            length.setText(endowmentDto.length)
            girth.setText(endowmentDto.girth)
            diameter.setText(endowmentDto.diameter)
            feelsLike.setText(endowmentDto.feelsLike)
            cutOrUncut.text = endowmentDto.isCut
            cutOrUncutUnknownCheckbox.isChecked = endowmentDto.isCutUnknownCheckbox
        }
    }
}