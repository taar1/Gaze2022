package net.gazeapp.ui.addoredit

import net.gazeapp.data.dto.BodyDto

class AddOrEditBody(
    val f: AddOrEditContactFragment
) {
    fun displayBody(body: BodyDto?) {
        body?.let {
            with(f) {
                eyeColor.setText(it.eyeColor)
                hairColor.setText(it.hairColor)
                hairStyle.setText(it.hairStyle)
                weight.setText(it.weight)
                height.setText(it.height)
                bodyHair.setText(it.bodyHair)
            }
        }
    }
}