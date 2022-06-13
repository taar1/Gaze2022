package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Food

@Parcelize
data class FoodDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var foodId: Int = 0,
    var foodStr: String?,
    var isLikesIt: Boolean = true,
    var food: Food
) : Parcelable {
    constructor() : this(
        0,
        0,
        0,
        "",
        true,
        Food(0)
    )
}