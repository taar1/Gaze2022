package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Work

@Parcelize
data class WorkDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var profession: String?,
    var employer: String?,
    var employerAddress: String?,
    var started: String?,
    var ended: String?,
    var workDuration: String?,
    var phone: String?,
    var fax: String?,
    var email: String?,
    var salary: Int = 0,
    var currency: String?,
    var frequency: String?,
    var notes: String?,
    var work: Work
) : Parcelable {
    constructor() : this(
        0,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        "",
        Work(0)
    )
}