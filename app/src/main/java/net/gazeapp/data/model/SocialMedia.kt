package net.gazeapp.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    indices = [Index(PersistentObject.CONTACT_ID_FIELD_NAME)],
    foreignKeys = [ForeignKey(
        entity = Contact::class,
        parentColumns = [PersistentObject.ID],
        childColumns = [PersistentObject.CONTACT_ID_FIELD_NAME],
        onDelete = ForeignKey.CASCADE
    )]
)
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
data class SocialMedia(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PersistentObject.ID)
    var id: Int = 0,

    @ColumnInfo(name = PersistentObject.CONTACT_ID_FIELD_NAME)
    var contactId: Int = 0,
    var facebook // https://www.facebook.com/peter.smith
    : String?,
    var twitter // https://twitter.com/ZuckerFinder
    : String?,
    var instagram // https://www.instagram.com/taar1
    : String?,
    var pinterest // https://www.pinterest.com/levato/
    : String?,
    var linkedIn // https://www.linkedin.com/in/markusliechti
    : String?,
    var xing // https://www.xing.com/profile/Andreas_Kaufmann36
    : String?,
    var viber // (only phone number)
    : String?,
    var whatsapp // (only phone number)
    : String?,
    var tumblr // http://roganrichards.tumblr.com/
    : String?,
    var grindr // (only username)
    : String?,
    var scruff // (only username)
    : String?,
    var planetRomeo // https://www.planetromeo.com/ciril_c
    : String?,
    var manhunt // http://www.manhunt.net/profile/br0tha
    : String?,
    var adam4adam // https://www.adam4adam.com/profile/view/Taar1
    : String?,
    var gaydar // (only username)
    : String?,
    var recon // (only username)
    : String?,
    var bbrt // (only username)
    : String?,
    var hornet // (only username)
    : String?,
    var lineMessenger // (only username)
    : String?,
    var weChat // (only username)
    : String?,
    var snapChat // (only username)
    : String?,
    var jackd // (only username)
    : String?,
    var boyahoy // (only username)
    : String?,
    var growlr // (only username)
    : String?,
    var gaycom // (only username)
    : String?,
    var tinder // (only username)
    : String?,
    var skype // (only username)
    : String?,
    var youtube: String?,
    var redtube: String?,
    var xtube: String?,
    var vimeo: String?,
    var pornhub: String?,
    var xhamster: String?,
    var dudesnude: String?,
    var nintendoNetwork: String?,
    var parler: String?,
    var playstationNetwork: String?,
    var xboxGamertag: String?,
    var miiverse: String?,
    var flickr: String?,
    var appleId: String?,
    var spotify: String?,
    var maleForce: String?,
    var daddyHunt: String?,
    var clubhouse: String?,
    var telegram: String?,
    var threema: String?,
    var signal: String?,
    var taimi: String?,
    var tiktok: String?,
    var angle: String?

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