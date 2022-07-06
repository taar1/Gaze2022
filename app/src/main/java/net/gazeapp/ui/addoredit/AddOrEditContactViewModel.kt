package net.gazeapp.ui.addoredit

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.gazeapp.R
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.dto.*
import net.gazeapp.data.model.*
import net.gazeapp.data.repository.*
import net.gazeapp.utilities.GazeTools
import java.util.*
import javax.inject.Inject

class ContactViewModelFactory(val contactId: Int, val app: Application) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddOrEditContactViewModel(contactId, app) as T
    }
}

class AddOrEditContactViewModel constructor(val contactId: Int, val app: Application) :
    AndroidViewModel(app) {

    @Inject
    lateinit var tools: GazeTools

    private val contactRepo: ContactsRepository =
        ContactsRepository(GazeDatabase.getDatabase(app).contactKtDao)
    private val mediaRepo: MediaRepository = MediaRepository(GazeDatabase.getDatabase(app))
    private val addressRepo: AddressRepository = AddressRepository(GazeDatabase.getDatabase(app))
    private val childrenRepo: ChildrenRepository = ChildrenRepository(GazeDatabase.getDatabase(app))
    private val clubRepo: ClubRepository = ClubRepository(GazeDatabase.getDatabase(app))
    private val bodyTypeRepo: BodyTypeRepository = BodyTypeRepository(GazeDatabase.getDatabase(app))
    private val notesRepo: NotesRepository = NotesRepository(GazeDatabase.getDatabase(app))
    private val nicknamesRepo: NicknamesRepository =
        NicknamesRepository(GazeDatabase.getDatabase(app))
    private val phoneRepository: PhoneRepository = PhoneRepository(GazeDatabase.getDatabase(app))
    private val websitesRepository: WebsitesRepository =
        WebsitesRepository(GazeDatabase.getDatabase(app))
    private val personalRepository: PersonalRepository =
        PersonalRepository(GazeDatabase.getDatabase(app))

    companion object {
        private const val TAG = "AddOrEditContactViewMod"
    }

    private val _contactWithDetails = MutableLiveData<ContactWithDetails>()
    val contactWithDetails: LiveData<ContactWithDetails>
        get() = _contactWithDetails

    private val _addressList = MutableLiveData<List<AddressDto>>()
    val addressList: LiveData<List<AddressDto>>
        get() = _addressList


    private val _addressAList = MutableLiveData<List<Address>>()
    val addressAList: LiveData<List<Address>>
        get() = _addressAList


    private val _body = MutableLiveData<BodyDto>()
    val body: LiveData<BodyDto>
        get() = _body

    private val _bodyTypeList = MutableLiveData<List<BodyTypeDto>>()
    val bodyTypeList: LiveData<List<BodyTypeDto>>
        get() = _bodyTypeList

    private val _childrenList = MutableLiveData<List<ChildDto>>()
    val childrenList: LiveData<List<ChildDto>>
        get() = _childrenList

    private val _clubList = MutableLiveData<List<ClubDto>>()
    val clubList: LiveData<List<ClubDto>>
        get() = _clubList

    private val _drugList = MutableLiveData<List<DrugDto>>()
    val drugList: LiveData<List<DrugDto>>
        get() = _drugList

    private val _emailList = MutableLiveData<List<EmailDto>>()
    val emailList: LiveData<List<EmailDto>>
        get() = _emailList

    private val _encounterList = MutableLiveData<List<EncounterDto>>()
    val encounterList: LiveData<List<EncounterDto>>
        get() = _encounterList

    private val _endowment = MutableLiveData<EndowmentDto>()
    val endowment: LiveData<EndowmentDto>
        get() = _endowment

    private val _ethnicityList = MutableLiveData<List<EthnicityDto>>()
    val ethnicityList: LiveData<List<EthnicityDto>>
        get() = _ethnicityList

    private val _fetishList = MutableLiveData<List<FetishDto>>()
    val fetishList: LiveData<List<FetishDto>>
        get() = _fetishList

    private val _foodList = MutableLiveData<List<FoodDto>>()
    val foodList: LiveData<List<FoodDto>>
        get() = _foodList

    private val _health = MutableLiveData<HealthDto>()
    val health: LiveData<HealthDto>
        get() = _health

    private val _hobbyList = MutableLiveData<List<HobbyDto>>()
    val hobbyList: LiveData<List<HobbyDto>>
        get() = _hobbyList

    private val _labelList = MutableLiveData<List<LabelDto>>()
    val labelList: LiveData<List<LabelDto>>
        get() = _labelList

    private val _mediaList = MutableLiveData<List<Media>>()
    val mediaList: LiveData<List<Media>>
        get() = _mediaList

    private val _movieList = MutableLiveData<List<MovieDto>>()
    val movieList: LiveData<List<MovieDto>>
        get() = _movieList

    private val _nicknameList = MutableLiveData<List<NicknameDto>>()
    val nicknameList: LiveData<List<NicknameDto>>
        get() = _nicknameList

    private val _notes = MutableLiveData<NoteDto>()
    val notes: LiveData<NoteDto>
        get() = _notes

    private val _personal = MutableLiveData<PersonalDto>()
    val personal: LiveData<PersonalDto>
        get() = _personal

    private val _phoneList = MutableLiveData<List<PhoneDto>>()
    val phoneList: LiveData<List<PhoneDto>>
        get() = _phoneList

    private val _socialMedia = MutableLiveData<SocialMediaDto>()
    val socialMedia: LiveData<SocialMediaDto>
        get() = _socialMedia

    private val _sportList = MutableLiveData<List<SportDto>>()
    val sportList: LiveData<List<SportDto>>
        get() = _sportList

    private val _tagList = MutableLiveData<List<TagDto>>()
    val tagList: LiveData<List<TagDto>>
        get() = _tagList

    private val _websiteList = MutableLiveData<List<WebsiteDto>>()
    val websiteList: LiveData<List<WebsiteDto>>
        get() = _websiteList

    private val _workList = MutableLiveData<List<WorkDto>>()
    val workList: LiveData<List<WorkDto>>
        get() = _workList

    private val _xxx = MutableLiveData<XxxDto>()
    val xxx: LiveData<XxxDto>
        get() = _xxx

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _hasEntries = MutableLiveData(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val hasEntries: LiveData<Boolean>
        get() = _hasEntries

    private var _hasMediaFiles = MutableLiveData(false)
    val hasMediaFiles: LiveData<Boolean>
        get() = _hasMediaFiles


    init {
        _hasEntries.value = true
        _hasMediaFiles.value = true
        getContactWithDetails()
        getMedia()
    }

    private fun getContactWithDetails() {
        viewModelScope.launch {
            contactRepo.getContactWithDetails(contactId)
            _contactWithDetails.postValue(contactRepo.contactWithDetails)

            prepareAddresses(contactWithDetails.value?.addresses)
            prepareBody(contactWithDetails.value?.body)
            prepareBodytype(contactWithDetails.value?.bodyTypes)
            prepareChildren(contactWithDetails.value?.children)
            prepareClubs(contactWithDetails.value?.clubs)
            prepareDrugs(contactWithDetails.value?.drugs)
            prepareEmails(contactWithDetails.value?.emails)
            prepareEncounters(contactWithDetails.value?.encounters)
            prepareEndowment(contactWithDetails.value?.endowment)
            prepareEthnicities(contactWithDetails.value?.ethnicities)
            prepareFetishes(contactWithDetails.value?.fetishes)
            prepareFoods(contactWithDetails.value?.foods)
            prepareHealth(contactWithDetails.value?.health)
            prepareHobbies(contactWithDetails.value?.hobbies)
            //prepareLabels()
//            prepareMedia(contactWithDetails.value?.media)
            prepareMovies(contactWithDetails.value?.movies)
            prepareNicknames(contactWithDetails.value?.nicknames)
            prepareNotes(contactWithDetails.value?.note)
            preparePersonal(contactWithDetails.value?.personal)
            preparePhones(contactWithDetails.value?.phones)
            prepareSocialMedia(contactWithDetails.value?.socialMedia)
            prepareSports(contactWithDetails.value?.sports)
            //prepareSocialTags()
            prepareWebsites(contactWithDetails.value?.websites)
            prepareWork(contactWithDetails.value?.work)
            prepareXxx(contactWithDetails.value?.xxx)

            // If there are no recent contacts inform the UI accordingly
            _hasEntries.value = !contactRepo.recentContactsList.isNullOrEmpty()
        }
    }

    private fun prepareWebsites(websites: List<Website>?) {
        val websiteDtoList = arrayListOf<WebsiteDto>()

        websites?.let {
            for (website in websites) {
                val websiteItem = WebsiteDto()
                websiteItem.id = website.id
                websiteItem.contactId = website.contactId
                websiteItem.websiteStr = website.website
                websiteItem.websiteType = website.websiteType
                websiteItem.description = website.description
                websiteItem.website = website

                websiteDtoList.add(websiteItem)
            }
        }

        _websiteList.postValue(websiteDtoList)
    }

    private fun prepareSports(sports: List<Sport>?) {
        val sportDtoList = arrayListOf<SportDto>()

        sports?.let {
            for (sport in sports) {
                val sportItem = SportDto()
                sportItem.id = sport.id
                sportItem.contactId = sport.contactId
                sportItem.sportId = sport.sportId
                sportItem.sportStr = sport.sport
                sportItem.isLikesIt = sport.isLikesIt
                sportItem.sport = sport

                sportDtoList.add(sportItem)
            }
        }

        _sportList.postValue(sportDtoList)
    }

    private fun preparePersonal(personal: Personal?) {
        personal?.let {
            val personalDto = PersonalDto()
            personalDto.id = it.id
            personalDto.contactId = it.contactId
            personalDto.birthdate = tools.formatDateToPhoneLocale(it.birthdate)
            personalDto.age = it.age
            personalDto.isOut = it.isOut
            personalDto.isOutTo = it.isOutTo
            personalDto.effeminate = it.effeminate
            personalDto.drinking = it.drinking
            personalDto.smoking = it.smoking
            personalDto.religion = it.religion
            personalDto.relationshipStatus = it.relationshipStatus
            personalDto.politicalView = it.politicalView
            personalDto.personal = it

            _personal.postValue(personalDto)
        }

        _personal.postValue(PersonalDto())
    }

//    private fun prepareMedia(mediaList: List<Media>?) {
//        val mediaDtoList = arrayListOf<MediaDto>()
//
//        mediaList?.let {
//            for (media in mediaList) {
//                val mediaItem = MediaDto()
//                mediaItem.id = media.id
//                mediaItem.contactId = media.contactId
//                mediaItem.isSelected = media.isSelected
//                mediaItem.usedFileName = media.usedFileName
//                mediaItem.originalFileName = media.originalFileName
//                mediaItem.path = media.path
//                mediaItem.fullPath = media.fullPath
//                mediaItem.fileType = media.fileType
//                mediaItem.isInPrivateGallery = media.isInPrivateGallery
//                mediaItem.isXRated = media.isXRated
//                mediaItem.caption = media.caption
//                mediaItem.labels = media.labels
//                mediaItem.created = tools.formatDateToPhoneLocale(media.created, app)
//                mediaItem.lastMod = tools.formatDateToPhoneLocale(media.lastMod, app)
//                mediaItem.sortOrder = media.sortOrder
//                mediaItem.uri = media.uri
//                mediaItem.media = media
//
//                mediaDtoList.add(mediaItem)
//            }
//        }
//
//        _mediaList.postValue(mediaDtoList)
//    }

    private fun preparePhones(phones: List<Phone>?) {
        val phoneDtoList = arrayListOf<PhoneDto>()

        phones?.let {
            for (phone in phones) {
                val phoneItem = PhoneDto()
                phoneItem.id = phone.id
                phoneItem.contactId = phone.contactId
                phoneItem.phoneNumberType = phone.phoneNumberType
                phoneItem.phoneNumber = phone.phoneNumber
                phoneItem.countryCode = phone.countryCode
                phoneItem.phone = phone

                phoneDtoList.add(phoneItem)
            }
        }

        _phoneList.postValue(phoneDtoList)
    }

    private fun prepareNicknames(nicknames: List<Nickname>?) {
        val nicknameDtoList = arrayListOf<NicknameDto>()

        nicknames?.let {
            for (nickname in nicknames) {
                val nicknameItem = NicknameDto()
                nicknameItem.id = nickname.id
                nicknameItem.contactId = nickname.contactId
                nicknameItem.nicknameStr = nickname.nickname
                nicknameItem.nickname = nickname

                nicknameDtoList.add(nicknameItem)
            }
        }

        _nicknameList.postValue(nicknameDtoList)
    }

    private fun prepareMovies(movies: List<Movie>?) {
        val movieDtoList = arrayListOf<MovieDto>()

        movies?.let {
            for (movie in movies) {
                val movieItem = MovieDto()
                movieItem.id = movie.id
                movieItem.contactId = movie.contactId
                movieItem.movieStr = movie.movie
                movieItem.url = movie.url
                movieItem.likesIt = movie.likesIt
                movieItem.movie = movie

                movieDtoList.add(movieItem)
            }
        }

        _movieList.postValue(movieDtoList)
    }

    private fun prepareHobbies(hobbies: List<Hobby>?) {
        val hobbyDtoList = arrayListOf<HobbyDto>()

        hobbies?.let {
            for (hobby in hobbies) {
                val hobbyItem = HobbyDto()
                hobbyItem.id = hobby.id
                hobbyItem.contactId = hobby.contactId
                hobbyItem.hobbyId = hobby.hobbyId
                hobbyItem.hobbyStr = hobby.hobby
                hobbyItem.hobby = hobby

                hobbyDtoList.add(hobbyItem)
            }
        }

        _hobbyList.postValue(hobbyDtoList)
    }

    private fun prepareHealth(health: Health?) {
        health?.let {
            val healthDto = HealthDto(it.contactId)
            healthDto.id = it.id
            healthDto.contactId = it.contactId
            healthDto.hiv = it.hiv
            healthDto.hivTestDate = tools.formatDateToPhoneLocale(it.hivTestDate)
            healthDto.hcv = it.hcv
            healthDto.hcvVaccinationDate =
                tools.formatDateToPhoneLocale(it.hcvVaccinationDate)
            healthDto.hsv1 = it.hsv1
            healthDto.hsv2 = it.hsv2
            healthDto.hpv = it.hpv
            healthDto.hpvVaccinationDate =
                tools.formatDateToPhoneLocale(it.hpvVaccinationDate)
            healthDto.hadCovid19 = it.hadCovid19
            healthDto.covid19InfectionDate =
                tools.formatDateToPhoneLocale(it.covid19InfectionDate)
            healthDto.isCovid19Vaccinated = it.isCovid19Vaccinated
            healthDto.covid19VaccinationDate =
                tools.formatDateToPhoneLocale(it.covid19VaccinationDate)
            healthDto.covid19VaccineBrand = it.covid19VaccineBrand
            healthDto.health = it

            _health.postValue(healthDto)
        }

        _health.postValue(HealthDto(0))
    }

    private fun prepareFoods(foods: List<Food>?) {
        val foodDtoList = arrayListOf<FoodDto>()

        foods?.let {
            for (food in foods) {
                val foodItem = FoodDto()
                foodItem.id = food.id
                foodItem.contactId = food.contactId
                foodItem.foodId = food.foodId
                foodItem.foodStr = food.food
                foodItem.isLikesIt = food.isLikesIt
                foodItem.food = food

                foodDtoList.add(foodItem)
            }
        }

        _foodList.postValue(foodDtoList)
    }

    private fun prepareFetishes(fetishes: List<Fetish>?) {
        val fetishDtoList = arrayListOf<FetishDto>()

        fetishes?.let {
            for (fetish in fetishes) {
                val fetishItem = FetishDto()
                fetishItem.id = fetish.id
                fetishItem.contactId = fetish.contactId
                fetishItem.fetishId = fetish.fetishId
                fetishItem.fetishStr = fetish.fetish
                fetishItem.fetish = fetish

                fetishDtoList.add(fetishItem)
            }
        }

        _fetishList.postValue(fetishDtoList)
    }

    private fun prepareEthnicities(ethnicities: List<Ethnicity>?) {
        val ethnicityDtoList = arrayListOf<EthnicityDto>()

        ethnicities?.let {
            for (ethnicity in ethnicities) {
                val ethnicityItem = EthnicityDto()
                ethnicityItem.id = ethnicity.id
                ethnicityItem.contactId = ethnicity.contactId
                ethnicityItem.ethnicityId = ethnicity.ethnicityId
                ethnicityItem.ethnicityStr = ethnicity.ethnicity
                ethnicityItem.ethnicity = ethnicity

                ethnicityDtoList.add(ethnicityItem)
            }
        }

        _ethnicityList.postValue(ethnicityDtoList)
    }

    private fun prepareEncounters(encounters: List<Encounter>?) {
        val encounterDtoList = arrayListOf<EncounterDto>()

        encounters?.let {
            for (encounter in encounters) {
                val encounterItem = EncounterDto()
                encounterItem.id = encounter.id
                encounterItem.contactId = encounter.contactId
                encounterItem.created = tools.formatDateToPhoneLocale(encounter.created)
                encounterItem.meetDate = tools.formatDateToPhoneLocale(encounter.meetDate)
                encounterItem.meetLocation = encounter.meetLocation
                encounterItem.myRole = encounter.myRole
                encounterItem.hisRole = encounter.hisRole
                encounterItem.isSafeSex = encounter.isSafeSex
                encounterItem.sureAboutSafeSex = encounter.sureAboutSafeSex
                encounterItem.myLoadWentTo = encounter.myLoadWentTo
                encounterItem.hisLoadWentTo = encounter.hisLoadWentTo
                encounterItem.remarks = encounter.remarks
                encounterItem.longitude = encounter.longitude
                encounterItem.latitude = encounter.latitude
                encounterItem.googleMapsLink = encounter.googleMapsLink
                encounterItem.lastMod = tools.formatDateToPhoneLocale(encounter.lastMod)
                encounterItem.rating = encounter.rating
                encounterItem.encounter = encounter

                encounterDtoList.add(encounterItem)
            }
        }

        _encounterList.postValue(encounterDtoList)
    }

    private fun prepareEmails(emails: List<Email>?) {
        val emailDtoList = arrayListOf<EmailDto>()

        emails?.let {
            for (email in emails) {
                val emailItem = EmailDto()
                emailItem.id = email.id
                emailItem.contactId = email.contactId
                emailItem.emailStr = email.email
                emailItem.emailType = email.emailType
                emailItem.email = email

                emailDtoList.add(emailItem)
            }
        }

        _emailList.postValue(emailDtoList)
    }

    private fun prepareDrugs(drugs: List<Drug>?) {
        val drugDtoList = arrayListOf<DrugDto>()

        drugs?.let {
            for (drug in drugs) {
                val drugItem = DrugDto()
                drugItem.id = drug.id
                drugItem.contactId = drug.contactId
                drugItem.drugStr = drug.drug
                drugItem.drugId = drug.drugId
                drugItem.drug = drug

                drugDtoList.add(drugItem)
            }
        }

        _drugList.postValue(drugDtoList)
    }

    private fun prepareClubs(clubs: List<Club>?) {
        val clubDtoList = arrayListOf<ClubDto>()

        clubs?.let {
            for (club in clubs) {
                val clubItem = ClubDto()
                clubItem.id = club.id
                clubItem.contactId = club.contactId
                clubItem.clubName = club.clubName
                clubItem.address = club.address
                clubItem.phoneNumber = club.phoneNumber
                clubItem.email = club.email
                clubItem.url = club.url
                clubItem.club = club

                clubDtoList.add(clubItem)
            }
        }

        _clubList.postValue(clubDtoList)
    }

    private fun prepareChildren(children: List<Child>?) {
        val childDtoList = arrayListOf<ChildDto>()

        children?.let {
            for (child in children) {
                val childItem = ChildDto()
                childItem.id = child.id
                childItem.contactId = child.contactId
                childItem.name = child.name
                childItem.pronoun = child.pronoun
                childItem.birthdate = tools.formatDateToPhoneLocale(child.birthdate)
                childItem.child = child

                childDtoList.add(childItem)
            }
        }

        _childrenList.postValue(childDtoList)
    }

    private fun prepareBody(body: Body?) {
        val bodyDto = BodyDto()

        body?.let {
            bodyDto.id = body.id
            bodyDto.contactId = body.contactId

            bodyDto.weight = tools.convertWeightFromGramsToReadableFormat(
                body.weight, tools.useMetricSystem()
            )

            bodyDto.height = tools.convertHeightFromCentimetersToReadableFormat(
                body.height, tools.useMetricSystem()
            )

            bodyDto.hairColor = body.hairColor
            bodyDto.hairStyle = body.hairStyle
            bodyDto.eyeColor = body.eyeColor
            bodyDto.bodyHair = body.bodyHair
            bodyDto.gender = body.gender

            bodyDto.body = it
        }

        _body.postValue(bodyDto)
    }

    private fun prepareAddresses(addresses: List<Address>?) {
        val addressDtoList = arrayListOf<AddressDto>()

        addresses?.let {
            for (address in addresses) {
                val addressItem = AddressDto()
                addressItem.id = address.id
                addressItem.contactId = address.contactId
                addressItem.addressStr = address.address
                addressItem.addressType = address.addressType
                addressItem.address = address
                addressDtoList.add(addressItem)
            }
        }

        _addressList.postValue(addressDtoList)
    }

    private fun prepareAddressesA(addresses: List<Address>) {
        _addressAList.postValue(addresses)
    }

    private fun prepareNotes(note: Note?) {
        val noteDto = NoteDto()

        note?.let {
            noteDto.apply {
                id = it.id
                contactId = it.contactId
                noteStr = it.note
                noteTextColor = it.noteTextColor // 0 = black
                noteBackgroundColor = it.noteBackgroundColor // 0 = white
                textSizeSp = it.textSizeSp // 0 = default text size
                created = tools.formatDateToPhoneLocale(it.created)
                lastMod = tools.formatDateToPhoneLocale(it.lastMod)
            }

            noteDto.note = it
            _notes.postValue(noteDto)
        }
    }

    private fun prepareSocialMedia(socialMediax: SocialMedia?) {
        val socialMediaDto = SocialMediaDto()

        socialMediax?.let {
            socialMediaDto.apply {
                id = it.id
                contactId = it.contactId
                facebook = it.facebook
                twitter = it.twitter
                instagram = it.instagram
                pinterest = it.pinterest
                linkedIn = it.linkedIn
                xing = it.xing
                viber = it.viber
                whatsapp = it.whatsapp
                tumblr = it.tumblr
                grindr = it.grindr
                scruff = it.scruff
                planetRomeo = it.planetRomeo
                manhunt = it.manhunt
                adam4adam = it.adam4adam
                gaydar = it.gaydar
                recon = it.recon
                bbrt = it.bbrt
                hornet = it.hornet
                lineMessenger = it.lineMessenger
                weChat = it.weChat
                snapChat = it.snapChat
                jackd = it.jackd
                boyahoy = it.boyahoy
                growlr = it.growlr
                gaycom = it.gaycom
                tinder = it.tinder
                skype = it.skype
                youtube = it.youtube
                redtube = it.redtube
                xtube = it.xtube
                vimeo = it.vimeo
                pornhub = it.pornhub
                xhamster = it.xhamster
                dudesnude = it.dudesnude
                nintendoNetwork = it.nintendoNetwork
                parler = it.parler
                playstationNetwork = it.playstationNetwork
                xboxGamertag = it.xboxGamertag
                miiverse = it.miiverse
                flickr = it.flickr
                appleId = it.appleId
                spotify = it.spotify
                maleForce = it.maleForce
                daddyHunt = it.daddyHunt
                clubhouse = it.clubhouse
                telegram = it.telegram
                threema = it.threema
                signal = it.signal
                taimi = it.taimi
                tiktok = it.tiktok
                angle = it.angle
                socialMedia = it
            }

            _socialMedia.postValue(socialMediaDto)
        }

    }

    private fun prepareEndowment(endowment: Endowment?) {
        val endowmentDto = EndowmentDto()

        endowment?.let {

            endowmentDto.id = it.id
            endowmentDto.contactId = it.contactId

            endowmentDto.length = tools.convertFromMillimetersToReadableFormat(
                it.length, tools.useMetricSystem()
            )

            endowmentDto.girth = tools.convertFromMillimetersToReadableFormat(
                it.girth, tools.useMetricSystem()
            )

            endowmentDto.diameter = tools.convertFromMillimetersToReadableFormat(
                it.diameter, tools.useMetricSystem()
            )

            endowmentDto.feelsLike = tools.convertFromMillimetersToReadableFormat(
                it.feelsLike, tools.useMetricSystem()
            )

            when (it.isCut) {
                0 -> endowmentDto.isCut = app.getString(R.string.uncut)
                1 -> endowmentDto.isCut = app.getString(R.string.cut)
                2 -> {
                    endowmentDto.isCut = app.getString(R.string.unknown)
                    endowmentDto.isCutUnknownCheckbox = true
                }
            }

            endowmentDto.endowment = it
            _endowment.postValue(endowmentDto)
        }
    }

    private fun prepareWork(work: List<Work>?) {
        val workDtoList = arrayListOf<WorkDto>()

        work?.let {
            val workListMutable = work.toMutableList()

            workListMutable.sortWith { o1: Work, o2: Work ->
                if (o1.started == null) {
                    o1.started = Date()
                }
                if (o2.started == null) {
                    o2.started = Date()
                }
                o2.started!!.compareTo(o1.started)
            }

            for (w in workListMutable) {
                val workItem = WorkDto()

                w.started?.let {
                    workItem.started = tools.formatDateToPhoneLocaleYearOnly(it)
                }

                w.ended?.let {
                    workItem.ended = tools.formatDateToPhoneLocaleYearOnly(it)
                }

                // Only ENDED date is set
                if (workItem.started.isNullOrEmpty() && !workItem.ended.isNullOrEmpty()) {
                    workItem.workDuration =
                        app.getString(R.string.until).plus(" ").plus(workItem.ended)
                }

                // Only STARTED date is set. Job is CURRENT JOB.
                if (!workItem.started.isNullOrEmpty() && workItem.ended.isNullOrEmpty()) {
                    workItem.workDuration =
                        workItem.started.plus(" - ").plus(app.getString(R.string.until_current))
                }

                // Both STARTED & ENDED dates are set.
                if (!workItem.started.isNullOrEmpty() && !workItem.ended.isNullOrEmpty()) {
                    workItem.workDuration = "${workItem.started} - ${workItem.ended}"
                }

                workItem.work = w
                workDtoList.add(workItem)
            }
        }

        _workList.postValue(workDtoList)
    }

    private fun prepareBodytype(bodytypeList: List<BodyType>?) {
        val bodytypeDtoList = arrayListOf<BodyTypeDto>()

        bodytypeList?.let {
            for (bodyType in bodytypeList) {
                val bodytypeItem = BodyTypeDto()
                bodytypeItem.id = bodyType.id
                bodytypeItem.contactId = bodyType.contactId
                bodytypeItem.bodytypeId = bodyType.bodytypeId
                bodytypeItem.bodyTypeStr = bodyType.bodyType
                bodytypeItem.isSelected = false
                bodytypeItem.bodyType = bodyType

                bodytypeDtoList.add(bodytypeItem)
            }
        }

        _bodyTypeList.postValue(bodytypeDtoList)
    }

    private fun prepareXxx(xxx: Xxx?) {
        val xxxDto = XxxDto()

        xxx?.let {
            val swallowsStr = when (it.swallowsLoads) {
                0 -> app.getString(R.string.no)
                1 -> app.getString(R.string.yes)
                2 -> app.getString(R.string.unknown_plain)
                3 -> ""
                else -> app.getString(R.string.unknown_plain)
            }

            val takesLoadsUpTheBum = when (it.takesLoadsUpTheBum) {
                0 -> app.getString(R.string.no)
                1 -> app.getString(R.string.yes)
                2 -> app.getString(R.string.unknown_plain)
                3 -> ""
                else -> app.getString(R.string.unknown_plain)
            }

            xxxDto.id = it.id
            xxxDto.contactId = it.contactId
            xxxDto.sexualOrientation = it.sexualOrientation
            xxxDto.sexRole = it.sexRole
            xxxDto.safeSex = it.safeSex
            xxxDto.takesLoadsUpTheBum = takesLoadsUpTheBum
            xxxDto.swallowsLoads = swallowsStr
            xxxDto.prefCumGiveDestination = it.prefCumGiveDestination
            xxxDto.prefCumReceiveDestination = it.prefCumReceiveDestination
            xxxDto.sexperience = it.sexperience
            xxxDto.xxx = it

            _xxx.postValue(xxxDto)
        }

        _xxx.postValue(XxxDto())
    }

    fun getMedia() {
        viewModelScope.launch {
            Log.d(TAG, "XXXXX2 getMedia: Contact ID: $contactId")

            mediaRepo.getMedia(contactId, false)
            _mediaList.postValue(mediaRepo.mediaList)

            Log.d(TAG, "XXXXX2 getMedia: mediaRepo.mediaList: ${mediaRepo.mediaList.size}")

            // If there contact media inform the UI accordingly
            _hasMediaFiles.postValue(!mediaRepo.mediaList.isNullOrEmpty())
        }
    }

    fun updateContact(c: Contact) {
        viewModelScope.launch {
            contactRepo.updateContact(c)
        }
    }

    fun updateBodyType(bt: BodyType) {
        viewModelScope.launch {
            bodyTypeRepo.update(bt)
        }
    }

    fun modifyEmail(e: Email) {
        // TODO email repo erstellen
        // TODO email DB funktionen erstellen
        // TODO hier drin das email objekt updaten in der DB
        // TODO testen ob der observer anschlägt für den change
        // TODO in der VIEW schauen, ob die daten korrekt aktualisiert werden.
    }

    fun deleteAddress(a: Address) {
        viewModelScope.launch {
            addressRepo.delete(a)
        }
    }

    fun updateAddress(a: Address) {
        viewModelScope.launch {
            addressRepo.update(a)
        }
    }

    fun deleteChild(c: Child) {
        viewModelScope.launch {
            childrenRepo.delete(c)
        }
    }

    fun updateChild(c: Child) {
        viewModelScope.launch {
            childrenRepo.update(c)
        }
    }

    fun deleteClub(c: Club) {
        viewModelScope.launch {
            clubRepo.delete(c)
        }
    }

    fun updateClub(c: Club) {
        viewModelScope.launch {
            clubRepo.update(c)
        }
    }

    fun deleteNote(n: Note) {
        viewModelScope.launch {
            notesRepo.delete(n)
        }
    }

    fun deleteNickname(n: Nickname) {
        viewModelScope.launch {
            nicknamesRepo.delete(n)
        }
    }

    fun updateNickname(n: Nickname) {
        viewModelScope.launch {
            nicknamesRepo.update(n)
        }
    }

    fun deletePhone(p: Phone) {
        viewModelScope.launch {
            phoneRepository.delete(p)
        }
    }

    fun updatePhone(p: Phone) {
        viewModelScope.launch {
            phoneRepository.update(p)
        }
    }

    fun deleteWebsite(w: Website) {
        viewModelScope.launch {
            websitesRepository.delete(w)
        }
    }

    fun updateWebsite(w: Website) {
        viewModelScope.launch {
            websitesRepository.update(w)
        }
    }

    fun deletepersonal(p: Personal) {
        viewModelScope.launch {
            personalRepository.delete(p)
        }
    }

    fun updatePersonal(p: Personal) {
        viewModelScope.launch {
            personalRepository.update(p)
        }
    }
}