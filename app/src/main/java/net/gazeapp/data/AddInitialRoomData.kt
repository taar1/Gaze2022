package net.gazeapp.data

import android.content.res.Resources
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.gazeapp.GazeApplication
import net.gazeapp.R
import net.gazeapp.data.dao.*
import net.gazeapp.data.model.*
import net.gazeapp.data.repository.ContactsRepository
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.MediaTools
import net.gazeapp.utilities.SpecificValues
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Add initial data to the Database.
 * - First Gaze contact
 * - A set of (contact) labels
 * - A set of (media) tags
 */
class AddInitialRoomData(db: GazeDatabase) {
    companion object {
        private const val TAG = "AddInitialRoomData"
    }

//    val context: Context = GazeApplication.applicationContext()
//    val Resources.getSystem() = Resources.getSystem()

    var sdf = SimpleDateFormat("dd-M-yyyy", Locale.getDefault())

    private val repository: ContactsRepository = ContactsRepository(db.contactKtDao)

    lateinit var contact: Contact

    private val addressDao: AddressDao = db.addressDao
    private val bodyDao: BodyDao = db.bodyDao
    private val bodyTypeDao: BodyTypeDao = db.bodyTypeDao
    private val sportDao: SportDao = db.sportDao
    private val drugDao: DrugDao = db.drugDao
    private val emailDao: EmailDao = db.emailDao
    private val encounterDao: EncounterDao = db.encounterDao
    private val endowmentDao: EndowmentDao = db.endowmentDao
    private val ethnicityDao: EthnicityDao = db.ethnicityDao
    private val fetishDao: FetishDao = db.fetishDao
    private val foodDao: FoodDao = db.foodDao
    private val healthDao: HealthDao = db.healthDao
    private val hobbyDao: HobbyDao = db.hobbyDao
    private val labelDao: LabelDao = db.labelDao
    private val movieDao: MovieDao = db.movieDao
    private val mediaDao: MediaDao = db.mediaDao
    private val nicknameDao: NicknameDao = db.nicknameDao
    private val noteDao: NoteDao = db.noteDao
    private val personalDao: PersonalDao = db.personalDao
    private val xxxDao: XxxDao = db.xxxDao
    private val socialMediaDao: SocialMediaDao = db.socialMediaDao
    private val phoneDao: PhoneDao = db.phoneDao
    private val workDao: WorkDao = db.workDao
    private val tagDao: TagDao = db.tagDao
    private val websiteDao: WebsiteDao = db.websiteDao

    val mediaTools: MediaTools = MediaTools()

    fun addFirstContactToDatabase() {
        Log.d(TAG, "XXXXX addFirstContactToDatabase()")

        contact = Contact(Const.FIRST_GAZE_CONTACT_NAME)
//        contact.additionalInfo = Resources.getSystem().getString(R.string.first_gaze_contact)
        contact.additionalInfo =
            GazeApplication.getAppContext()?.resources?.getString(R.string.first_gaze_contact)

        CoroutineScope(Dispatchers.IO).launch {
            repository.insertContact(contact).also {
                contact.id = it.toInt()
                Log.d(TAG, "XXXXX FIRST CONTACT ID: $it")

                addressDao.insert(address)
                bodyDao.insert(body)
                endowmentDao.insert(endowment)
                healthDao.insert(health)
                contact.mainPicId = mediaDao.insert(media).toInt()
                repository.updateContact(contact)

                noteDao.insert(note)
                personalDao.insert(personal)
                xxxDao.insert(xxx)
                socialMediaDao.insert(socialMedia)
                ethnicityDao.insert(ethnicity)

                for (bodyType in bodytypes) {
                    bodyTypeDao.insert(bodyType)
                }
                for (sportEntity in sports) {
                    sportDao.insert(sportEntity)
                }
                for (drugEntity in drugs) {
                    drugDao.insert(drugEntity)
                }
                for (emailEntity in emails) {
                    emailDao.insert(emailEntity)
                }
                for (encounterEntity in encounters) {
                    encounterDao.insert(encounterEntity)
                }
                for (fetishEntity in fetishes) {
                    fetishDao.insert(fetishEntity)
                }
                for (foodEntity in foods) {
                    foodDao.insert(foodEntity)
                }
                for (hobbyEntity in hobbies) {
                    hobbyDao.insert(hobbyEntity)
                }
                for (movieEntity in movies) {
                    movieDao.insert(movieEntity)
                }
                for (nicknameEntity in nicknames) {
                    nicknameDao.insert(nicknameEntity)
                }
                for (phoneEntity in phones) {
                    phoneDao.insert(phoneEntity)
                }
                for (workEntity in work) {
                    workDao.insert(workEntity)
                }
                for (websiteEntity in websites) {
                    websiteDao.insert(websiteEntity)
                }

                mediaTools.saveImageFromResourceToInternalStorage(media, R.drawable.josh)
            }
        }
    }

    fun addLabelsToDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            labelDao.insertAll(
                listOf(
                    Label(0, Resources.getSystem().getString(R.string.holidays), Date(), Date()),
                    Label(0, Resources.getSystem().getString(R.string.mykonos), Date(), Date()),
                    Label(0, Resources.getSystem().getString(R.string.from_grindr), Date(), Date()),
                    Label(0, Resources.getSystem().getString(R.string.athlete), Date(), Date()),
                    Label(0, Resources.getSystem().getString(R.string.hunky), Date(), Date())
                )
            )
        }
    }

    fun addTagsToDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            tagDao.insertAll(
                listOf(
                    Tag(0, Resources.getSystem().getString(R.string.face), Date(), Date(), false),
                    Tag(0, Resources.getSystem().getString(R.string.body), Date(), Date(), false),
                    Tag(0, Resources.getSystem().getString(R.string.xxx), Date(), Date(), false),
                    Tag(0, Resources.getSystem().getString(R.string.dick), Date(), Date(), false)
                )
            )
        }
    }

    val address: Address
        get() {
            val adr = Address(contact.id)
            adr.address = "100 New Bond St\nMayfair\nLondon\nW1S 1SP"
            adr.addressType = "Home"
            return adr
        }

    val body: Body
        get() {
            val body = Body(contact.id)
            body.bodyHair = "Hairy"
            body.eyeColor = "Blue"
            body.hairColor = "Brown"
            body.hairStyle = "Short"
            body.height = 179
            body.weight = 75000
            body.gender = "cis-male"
            return body
        }

    val bodytypes: List<BodyType>
        get() {
            val al = mutableListOf<BodyType>()

            val bodytype1 = BodyType(contact.id)
            bodytype1.bodyType = Resources.getSystem().getString(R.string.average)
            al.add(bodytype1)

            val bodytype2 = BodyType(contact.id)
            bodytype2.bodyType = Resources.getSystem().getString(R.string.sporty)
            al.add(bodytype2)
            return al
        }

    val drugs: List<Drug>
        get() {
            val al = mutableListOf<Drug>()

            val drug1 = Drug(contact.id)
            drug1.drug = Resources.getSystem().getString(R.string.alcohol)
            al.add(drug1)

            val drug2 = Drug(contact.id)
            drug2.drug = Resources.getSystem().getString(R.string.cannabis)
            al.add(drug2)
            return al
        }

    val emails: List<Email>
        get() {
            val al = mutableListOf<Email>()

            val email1 = Email(contact.id)
            email1.emailType = "private"
            email1.email = "josh@gaze.net"
            al.add(email1)

            val email2 = Email(contact.id)
            email2.emailType = "business"
            email2.email = "feedback@gaze.net"
            al.add(email2)
            return al
        }

    val encounters: List<Encounter>
        get() {
            val al = mutableListOf<Encounter>()

            if (SpecificValues.SHOW_XRATED) {
                val enc = Encounter(contact.id)
                enc.googleMapsLink = "https://goo.gl/maps/8QQNZ3zJj2q"
                enc.hisRole = Resources.getSystem().getString(R.string.top)
                try {
                    val date = sdf.parse("10-04-2015")
                    enc.meetDate = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                enc.myRole = Resources.getSystem().getString(R.string.bottom)
                enc.remarks = "Had a great time."
                enc.isSafeSex = true
                enc.sureAboutSafeSex = false
                al.add(enc)

                val enc2 = Encounter(contact.id)
                enc2.hisRole = Resources.getSystem().getString(R.string.top)
                try {
                    val date = sdf.parse("17-12-2020")
                    enc2.meetDate = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                enc2.meetLocation = Resources.getSystem().getString(R.string.my_place)
                enc2.myRole = Resources.getSystem().getString(R.string.top)
                enc2.remarks = "Great Netflix and chill! :)"
                enc2.rating = 7
                al.add(enc2)

                val enc3 = Encounter(contact.id)
                enc3.hisRole = Resources.getSystem().getString(R.string.top_and_bottom)
                enc3.meetLocation = "His apartment"
                try {
                    val date = sdf.parse("22-3-2019")
                    enc3.meetDate = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                enc3.myRole = Resources.getSystem().getString(R.string.top_and_bottom)
                enc3.remarks = "That was really good fun!"
                enc3.isSafeSex = false
                enc3.rating = 8
                enc3.myLoadWentTo = "His face"
                enc3.hisLoadWentTo = "Booty"
                al.add(enc3)
            } else {
                val enc = Encounter(contact.id)
                enc.googleMapsLink = "https://goo.gl/maps/8yS3n8irQ5t"
                try {
                    val date = sdf.parse("20-05-2020")
                    enc.meetDate = date
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                enc.myRole = Resources.getSystem().getString(R.string.bottom)
                enc.remarks = "Had a great time."
                enc.isSafeSex = true
                enc.sureAboutSafeSex = false
                al.add(enc)
            }
            return al
        }

    val endowment: Endowment
        get() {
            val endowment = Endowment(contact.id)
            endowment.isCut = 0
            endowment.diameter = 50
            endowment.girth = 157
            endowment.length = 180
            endowment.feelsLike = 200
            return endowment
        }

    val ethnicity: Ethnicity
        get() {
            val ethnicity = Ethnicity(contact.id)
            ethnicity.ethnicity = Resources.getSystem().getString(R.string.caucasian)
            return ethnicity
        }

    val fetishes: List<Fetish>
        get() {
            val al = mutableListOf<Fetish>()

            val fetish = Fetish(contact.id)
            fetish.fetish = Resources.getSystem().getString(R.string.watersports)
            fetish.fetishId = 47
            al.add(fetish)

            val fetish2 = Fetish(contact.id)
            fetish2.fetish = Resources.getSystem().getString(R.string.uniform)
            fetish2.fetishId = 10
            al.add(fetish2)
            return al
        }

    val foods: List<Food>
        get() {
            val al = mutableListOf<Food>()
            val food1 = Food(contact.id)
            food1.food = "Spaghetti"
            food1.isLikesIt = true
            al.add(food1)

            val food2 = Food(contact.id)
            food2.food = "Pizza"
            food2.isLikesIt = false
            al.add(food2)
            return al
        }

    val health: Health
        get() {
            val health = Health(contact.id)
            health.hcv = Resources.getSystem().getString(R.string.negative)
            health.hiv = Resources.getSystem().getString(R.string.poz_undetectable)
            health.hpv = Resources.getSystem().getString(R.string.negative)
            health.hadCovid19 = 0
            health.isCovid19Vaccinated = 1
            health.covid19VaccinationDate = Date()
            return health
        }

    val hobbies: List<Hobby>
        get() {
            val al = mutableListOf<Hobby>()
            val hobby1 = Hobby(contact.id)
            hobby1.hobby = "Volleyball"
            al.add(hobby1)

            val hobby2 = Hobby(contact.id)
            hobby2.hobby = "Gym"
            al.add(hobby2)

            val hobby3 = Hobby(contact.id)
            hobby3.hobby = "Cooking"
            al.add(hobby3)
            return al
        }

    val media: Media
        get() {
            val media = Media(contact.id)
            media.caption = "Josh, my first Gaze contact"
            media.fileType = "image"
            media.originalFileName = "josh.jpg"
            media.usedFileName = "josh.jpg"
            media.path = Environment.getDataDirectory().absolutePath
            media.fullPath = mediaTools.getFullPath(media)
            media.isInPrivateGallery = false
            media.created = Date()
            media.lastMod = Date()
            media.isXRated = false
            media.labels = "Acquaintance, Buddy"
            return media
        }

    val movies: List<Movie>
        get() {
            val al = mutableListOf<Movie>()
            val movie1 = Movie(contact.id)
            movie1.movie = "Independence Day"
            al.add(movie1)

            val movie2 = Movie(contact.id)
            movie2.movie = "Avatar"
            al.add(movie2)

            val movie3 = Movie(contact.id)
            movie3.movie = "Star Trek"
            al.add(movie3)
            return al
        }

    val nicknames: List<Nickname>
        get() {
            val al = mutableListOf<Nickname>()
            val nick1 = Nickname(contact.id)
            nick1.nickname = "Joshua"
            al.add(nick1)

            val nick2 = Nickname(contact.id)
            nick2.nickname = "Jay"
            al.add(nick2)
            return al
        }

    val note: Note
        get() {
            val note = Note(contact.id)
            note.note = Resources.getSystem().getString(R.string.first_gaze_contact_note)
            return note
        }

    val personal: Personal
        get() {
            val personal = Personal(contact.id)
            try {
                val date = sdf.parse("02-11-1978")
                personal.birthdate = date
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            personal.drinking = Resources.getSystem().getString(R.string.socially)
            personal.effeminate = 15
            personal.politicalView = "Liberal"
            personal.relationshipStatus = Resources.getSystem().getString(R.string.in_relationship)
            personal.religion = Resources.getSystem().getString(R.string.atheist)
            personal.smoking = Resources.getSystem().getString(R.string.no)
            personal.isOut = 1
            return personal
        }

    val phones: List<Phone>
        get() {
            val al = mutableListOf<Phone>()
            val p1 = Phone(contact.id)
            p1.phoneNumber = "+44 20 7000 7700"
            p1.phoneNumberType = "Private"
            al.add(p1)

            val p2 = Phone(contact.id)
            p2.phoneNumber = "+44 (0)7742 200 740"
            p2.phoneNumberType = "Work"
            al.add(p2)
            return al
        }

    val work: List<Work>
        get() {
            val al = mutableListOf<Work>()
            val p1 = Work(contact.id)
            p1.employer = "HSBC"
            p1.employerAddress = "165 Fleet St\nLondon"
            p1.profession = "Consultant"
            p1.phone = "+44 345 740 4404"
            p1.email = "info@hsbc.com"
            p1.salary = 110000
            p1.frequency = Resources.getSystem().getString(R.string.per_year)
            p1.currency = "GBP"
            try {
                val startDate = sdf.parse("01-01-2001")
                p1.started = startDate
                val endDate = sdf.parse("31-10-2008")
                p1.ended = endDate
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            al.add(p1)

            val p2 = Work(contact.id)
            p2.employer = "BBC"
            p2.employerAddress = "Broadcasting House\nPortland Place\nLondon\nW1A 1AA".trimIndent()
            p2.profession = "Project Manager"
            p2.email = "info@bbc.co.uk"
            try {
                val startDate = sdf.parse("01-11-2008")
                p2.started = startDate
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            al.add(p2)
            return al
        }

    val xxx: Xxx
        get() {
            val xxx = Xxx(contact.id)
            xxx.safeSex = Resources.getSystem().getString(R.string.safe_only)
            xxx.sexperience = 8
            xxx.sexRole = Resources.getSystem().getString(R.string.versatile)
            xxx.sexualOrientation = Resources.getSystem().getString(R.string.gay)
            xxx.swallowsLoads = 1
            xxx.takesLoadsUpTheBum = 2
            return xxx
        }

    val socialMedia: SocialMedia
        get() {
            val sm = SocialMedia(contact.id)

            // MESSENGER
            sm.lineMessenger = "josh_lineMessenger"
            sm.skype = "joshSkype"

            // DATING
            sm.adam4adam = "https://www.adam4adam.com/profile/view/Josh"
            sm.gaycom = "https://www.gay.com/profiles/josh"
            sm.manhunt = "http://www.manhunt.net/profile/josh"
            sm.planetRomeo = "https://www.planetromeo.com/josh"
            sm.dudesnude = "josh"


            // SOCIAL MEDIA
            sm.facebook = "https://www.facebook.com/josh.gazeman"
            sm.instagram = "https://www.instagram.com/josh"
            sm.linkedIn = "https://www.linkedin.com/in/josh"
            sm.twitter = "https://twitter.com/josh"
            sm.youtube = "https://www.youtube.com/user/joshschannel"

            // GAMING
            sm.nintendoNetwork = "itsJosh"
            return sm
        }

    val sports: List<Sport>
        get() {
            val al = mutableListOf<Sport>()
            val p1 = Sport(contact.id)
            p1.sport = "Football"
            al.add(p1)

            val p2 = Sport(contact.id)
            p2.sport = "Gym"
            al.add(p2)

            val p3 = Sport(contact.id)
            p3.sport = "Mountain biking"
            al.add(p3)

            val p4 = Sport(contact.id)
            p4.sport = "Swimming"
            al.add(p4)
            return al
        }


    val websites: List<Website>
        get() {
            val al = mutableListOf<Website>()
            val website1 = Website(contact.id)
            website1.website = "https://www.iamjosh.com"
            website1.description = "Blog"
            al.add(website1)

            val website2 = Website(contact.id)
            website2.website = "https://www.hsbc.co.uk"
            website2.description = "Ex-Employer's Website"
            al.add(website2)
            return al
        }

}