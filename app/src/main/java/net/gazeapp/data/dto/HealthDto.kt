package net.gazeapp.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.gazeapp.data.model.Health

@Parcelize
data class HealthDto(
    var id: Int = 0,
    var contactId: Int = 0,
    var hiv: String?,
    var hivTestDate: String?,
    var hcv: String?,
    var hcvVaccinationDate: String?,
    var hsv1: String?,
    var hsv2: String?,
    var hpv: String?,
    var hpvVaccinationDate: String?,
    var hadCovid19: Int? = 2, // 0=no, 1=yes, 2=unknown
    var covid19InfectionDate: String?,
    var isCovid19Vaccinated: Int? = 2, // 0=no, 1=yes, 2=unknown
    var covid19VaccinationDate: String?,
    var covid19VaccineBrand: String?,
    var health: Health
) : Parcelable {
    constructor(contactId: Int) : this(
        0,
        contactId,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        Health(0)
    )
}