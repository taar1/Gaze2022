package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactWithDetails(
    @Embedded var contact: Contact,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var addresses: List<Address>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var body: Body?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var bodyTypes: List<BodyType>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var children: List<Child>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var clubs: List<Club>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var drugs: List<Drug>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var emails: List<Email>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var encounters: List<Encounter>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var endowment: Endowment?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var ethnicities: List<Ethnicity>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var fetishes: List<Fetish>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var foods: List<Food>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var health: Health?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var hobbies: List<Hobby>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var media: List<Media>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var movies: List<Movie>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var nicknames: List<Nickname>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var note: Note?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var personal: Personal?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var phones: List<Phone>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var socialMedia: SocialMedia?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var sports: List<Sport>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var websites: List<Website>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var work: List<Work>?,
    @Relation(
        parentColumn = PersistentObject.ID,
        entityColumn = PersistentObject.CONTACT_ID_FIELD_NAME
    )
    var xxx: Xxx?

) : Parcelable {
    constructor(contact: Contact) : this(
        contact,
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
        null
    )
}