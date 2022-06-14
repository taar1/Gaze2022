package net.gazeapp.ui.addoredit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andexert.expandablelayout.library.ExpandableLayout
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import net.gazeapp.R
import net.gazeapp.data.dto.BodyTypeDto
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.databinding.FragmentAddOrEditContactBinding
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.GazeTools
import net.gazeapp.utilities.MediaTools
import net.gazeapp.utilities.SpecificValues
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddOrEditContactFragment : Fragment(R.layout.fragment_add_or_edit_contact) {

    private var isEditMode: Boolean = false

    @Inject
    lateinit var mediaTools: MediaTools

    @Inject
    lateinit var tools: GazeTools

    lateinit var contactWithDetails: ContactWithDetails

    lateinit var editImages: TextView
    lateinit var addImages: TextView
    lateinit var contactPicture: ImageView
    lateinit var contactName: TextInputEditText
    lateinit var deleteContactName: ImageView
    lateinit var nicknameLayout: LinearLayout
    lateinit var addNickname: TextView
    lateinit var additionalInfo: TextInputEditText
    lateinit var deleteAdditionalInfo: ImageView
    lateinit var birthdate: TextInputEditText
    lateinit var age: TextInputEditText
    lateinit var relationshipStatus: TextInputEditText

    lateinit var hiv: TextInputEditText
    lateinit var hcv: TextInputEditText

    lateinit var effeminateSlider: SeekBar
    lateinit var effeminateUnknown: CheckBox

    lateinit var length: TextInputEditText
    lateinit var girth: TextInputEditText
    lateinit var diameter: TextInputEditText
    lateinit var feelsLike: TextInputEditText
    lateinit var cutOrUncut: SwitchCompat
    lateinit var cutOrUncutUnknownCheckbox: CheckBox

    lateinit var sexualOrientation: TextInputEditText
    lateinit var sexRole: TextInputEditText
    lateinit var safeSex: TextInputEditText
    lateinit var swallowsLoads: TextInputEditText
    lateinit var loadsUpTheBum: TextInputEditText

    lateinit var phoneLayout: LinearLayout
    lateinit var addPhone: TextView

    lateinit var note: TextInputEditText

    lateinit var facebook: TextInputEditText
    lateinit var flickr: TextInputEditText
    lateinit var instagram: TextInputEditText
    lateinit var linkedin: TextInputEditText
    lateinit var pinterest: TextInputEditText
    lateinit var pornhub: TextInputEditText
    lateinit var redtube: TextInputEditText
    lateinit var spotify: TextInputEditText
    lateinit var tumblr: TextInputEditText
    lateinit var twitter: TextInputEditText
    lateinit var vimeo: TextInputEditText
    lateinit var xing: TextInputEditText
    lateinit var xtube: TextInputEditText
    lateinit var youtube: TextInputEditText
    lateinit var lineMessenger: TextInputEditText
    lateinit var skype: TextInputEditText
    lateinit var snapchat: TextInputEditText
    lateinit var viber: TextInputEditText
    lateinit var weChat: TextInputEditText
    lateinit var whatsapp: TextInputEditText
    lateinit var adam4adam: TextInputEditText
    lateinit var bbrt: TextInputEditText
    lateinit var dudesnude: TextInputEditText
    lateinit var gaycom: TextInputEditText
    lateinit var gaydar: TextInputEditText
    lateinit var grindr: TextInputEditText
    lateinit var growlr: TextInputEditText
    lateinit var hornet: TextInputEditText
    lateinit var jackd: TextInputEditText
    lateinit var manhunt: TextInputEditText
    lateinit var planetRomeo: TextInputEditText
    lateinit var recon: TextInputEditText
    lateinit var scruff: TextInputEditText
    lateinit var tinder: TextInputEditText
    lateinit var miiverse: TextInputEditText
    lateinit var nintendonetwork: TextInputEditText
    lateinit var playstationnetwork: TextInputEditText
    lateinit var xboxgamertag: TextInputEditText


    lateinit var contactLayout: ExpandableLayout
    lateinit var contactHeader: TextView
    lateinit var contactRightIcon: ImageView

    lateinit var personalInfoLayout: ExpandableLayout
    lateinit var personalHeader: TextView
    lateinit var personalRightIcon: ImageView

    lateinit var professionalInfoLayout: ExpandableLayout
    lateinit var professionalHeader: TextView
    lateinit var professionalRightIcon: ImageView

    lateinit var bodyFeaturesLayout: ExpandableLayout
    lateinit var bodyFeaturesHeader: TextView
    lateinit var bodyFeaturesRightIcon: ImageView

    lateinit var endowmentLayout: ExpandableLayout
    lateinit var endowmentHeader: TextView
    lateinit var endowmentRightIcon: ImageView

    lateinit var xxxLayout: ExpandableLayout
    lateinit var xxxHeader: TextView
    lateinit var xxxRightIcon: ImageView

    lateinit var healthLayout: ExpandableLayout
    lateinit var healthHeader: TextView
    lateinit var healthRightIcon: ImageView

    lateinit var encountersLayout: ExpandableLayout
    lateinit var encountersHeader: TextView
    lateinit var encountersRightIcon: ImageView

    lateinit var socialmediaLayout: ExpandableLayout
    lateinit var socialMediaHeader: TextView
    lateinit var socialMediaRightIcon: ImageView

    lateinit var notesLayout: ExpandableLayout
    lateinit var notesHeader: TextView
    lateinit var notesRightIcon: ImageView

    lateinit var labelsLayout: ExpandableLayout
    lateinit var labelsHeader: TextView
    lateinit var labelsRightIcon: ImageView

    lateinit var metInPersonSwitch: SwitchCompat
    lateinit var addressLayout: LinearLayout
    lateinit var addAddress: TextView
    lateinit var childrenLayout: LinearLayout
    lateinit var addChildren: TextView
    lateinit var clubsLayout: LinearLayout
    lateinit var addClubs: TextView

    lateinit var emailLayout: LinearLayout
    lateinit var workLayout: LinearLayout
    lateinit var encounterLayout: LinearLayout
    lateinit var websitesLayout: LinearLayout

    lateinit var eyeColor: TextInputEditText
    lateinit var hairColor: TextInputEditText
    lateinit var hairStyle: TextInputEditText
    lateinit var weight: TextInputEditText
    lateinit var height: TextInputEditText
    lateinit var bodyHair: TextInputEditText

    lateinit var bodyType: TextInputEditText
    lateinit var ethnicity: TextInputEditText
    lateinit var fetishes: TextInputEditText

    lateinit var deleteBodyType: ImageView
    lateinit var deleteEthnicity: ImageView
    lateinit var deleteFetishes: ImageView
    lateinit var deleteBirthdate: ImageView
    lateinit var deleteAge: ImageView
    lateinit var deleteRelationshipStatus: ImageView
    lateinit var deleteHiv: ImageView
    lateinit var deleteHcv: ImageView
    lateinit var deleteSexualOrientation: ImageView
    lateinit var deleteSexRole: ImageView
    lateinit var deleteSafeSex: ImageView
    lateinit var deleteSwallowsLoads: ImageView
    lateinit var deleteUpTheBum: ImageView
    lateinit var deleteLength: ImageView
    lateinit var deleteGirth: ImageView
    lateinit var deleteDiameter: ImageView
    lateinit var deleteFeelsLike: ImageView
    lateinit var deleteNote: ImageView
    lateinit var deleteLabels: ImageView
    lateinit var deleteFacebook: ImageView
    lateinit var deleteFlickr: ImageView
    lateinit var deleteInstagram: ImageView
    lateinit var deleteLinkedin: ImageView
    lateinit var deletePinterest: ImageView
    lateinit var deletePornhub: ImageView
    lateinit var deleteRedtube: ImageView
    lateinit var deleteSpotify: ImageView
    lateinit var deleteTumblr: ImageView
    lateinit var deleteTwitter: ImageView
    lateinit var deleteVimeo: ImageView
    lateinit var deleteXing: ImageView
    lateinit var deleteXtube: ImageView
    lateinit var deleteYoutube: ImageView
    lateinit var deleteLineMessenger: ImageView
    lateinit var deleteSkype: ImageView
    lateinit var deleteSnapchat: ImageView
    lateinit var deleteViber: ImageView
    lateinit var deleteWeChat: ImageView
    lateinit var deleteWhatsapp: ImageView
    lateinit var deleteAdam4adam: ImageView
    lateinit var deleteBbrt: ImageView
    lateinit var deleteDudesnude: ImageView
    lateinit var deleteGaycom: ImageView
    lateinit var deleteGaydar: ImageView
    lateinit var deleteGrindr: ImageView
    lateinit var deleteGrowlr: ImageView
    lateinit var deleteHornet: ImageView
    lateinit var deleteJackd: ImageView
    lateinit var deleteManhunt: ImageView
    lateinit var deletePlanetRomeo: ImageView
    lateinit var deleteRecon: ImageView
    lateinit var deleteScruff: ImageView
    lateinit var deleteTinder: ImageView
    lateinit var deleteMiiverse: ImageView
    lateinit var deleteNintendonetwork: ImageView
    lateinit var deletePlaystationnetwork: ImageView
    lateinit var deleteXboxgamertag: ImageView

    var bodyTypesArrayList: List<BodyTypeDto>? = null

    lateinit var viewBinding: FragmentAddOrEditContactBinding
    lateinit var viewModel: AddOrEditContactViewModel
    val inputMethodManager: InputMethodManager =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    var dateFormatter = SimpleDateFormat("dd/MM/yyyy")

    private val addOrEditAddresses: AddOrEditAddresses = AddOrEditAddresses(this)
    private val addOrEditBody: AddOrEditBody = AddOrEditBody(this)
    private val addOrEditBodyType: AddOrEditBodyType = AddOrEditBodyType(this)
    private val addOrEditChild: AddOrEditChild = AddOrEditChild(this)
    private val addOrEditClub: AddOrEditClub = AddOrEditClub(this)
    private val addOrEditEmails: AddOrEditEmails = AddOrEditEmails(this)
    private val addOrEditEncounters: AddOrEditEncounters = AddOrEditEncounters(this)
    private val addOrEditEndowment: AddOrEditEndowment = AddOrEditEndowment(this)
    private val addOrEditEthnicity: AddOrEditEthnicity = AddOrEditEthnicity(this)
    private val addOrEditFetishes: AddOrEditFetishes = AddOrEditFetishes(this)
    private val addOrEditNicknames: AddOrEditNicknames = AddOrEditNicknames(this)
    private val addOrEditNotes: AddOrEditNotes = AddOrEditNotes(this)
    private val addOrEditPersonal: AddOrEditPersonal = AddOrEditPersonal(this)
    private val addOrEditPhone: AddOrEditPhone = AddOrEditPhone(this)
    private val addOrEditSocialMedia: AddOrEditSocialMedia = AddOrEditSocialMedia(this)
    private val addOrEditWebsite: AddOrEditWebsite = AddOrEditWebsite(this)
    private val addOrEditWork: AddOrEditWork = AddOrEditWork(this)
    private val addOrEditXxx: AddOrEditXxx = AddOrEditXxx(this)

    companion object {
        private const val TAG = "AddOrEditContactFragmen"
        fun newInstance() = AddOrEditContactFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddOrEditContactBinding.inflate(inflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()
        setExandableLayoutBehaviour()


        viewModel = ViewModelProvider(this)[AddOrEditContactViewModel::class.java]

        viewModel.contactWithDetails.observe(viewLifecycleOwner) { contactWithDetails ->
            Log.d(TAG, "XXXXX onViewCreated: contactWithDetails: $contactWithDetails")

            this.contactWithDetails = contactWithDetails

            refreshContactCardMainPic()
        }

        viewModel.addressAList.observe(viewLifecycleOwner) { addressList ->
            Log.d(TAG, "XXXXX onViewCreated: addressList: $addressList")
            addOrEditAddresses.displayAddresses(addressList)
        }

        viewModel.body.observe(viewLifecycleOwner) { body ->
            Log.d(TAG, "XXXXX onViewCreated: body: $body")
            addOrEditBody.displayBody(body)
        }

        viewModel.bodyTypeList.observe(viewLifecycleOwner) { bodyTypeList ->
            Log.d(TAG, "XXXXX onViewCreated: bodyTypeList: $bodyTypeList")
            addOrEditBodyType.displayBodyTypeList(bodyTypeList)
        }

        viewModel.childrenList.observe(viewLifecycleOwner) { childrenList ->
            Log.d(TAG, "XXXXX onViewCreated: childrenList: $childrenList")
            addOrEditChild.displayChildrenList(childrenList)
        }

        viewModel.clubList.observe(viewLifecycleOwner) { clubList ->
            Log.d(TAG, "XXXXX onViewCreated: clubList: $clubList")
            addOrEditClub.displayClubList(clubList)
        }

        // TODO: E-Mail....
        // TODO: E-Mail....
        // TODO: E-Mail....
//        contactWithDetails.emails?.forEach { e ->
//            addOrEditEmails.addEmailLayout(e, false)
//        }

        viewModel.endowment.observe(viewLifecycleOwner) { endowment ->
            Log.d(TAG, "XXXXX onViewCreated: endowment: $endowment")
            addOrEditEndowment.displayEndowment(endowment)
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            Log.d(TAG, "XX§XXX onViewCreated: notes: $notes")
            addOrEditNotes.displayNotes(notes)
        }

        viewModel.socialMedia.observe(viewLifecycleOwner) { socialMedia ->
            Log.d(TAG, "XXXXX onViewCreated: socialMedia: $socialMedia")
            addOrEditSocialMedia.displaySocialMedia(socialMedia)
        }

        viewModel.workList.observe(viewLifecycleOwner) { work ->
            Log.d(TAG, "XXXXX onViewCreated: work: $work")
            addOrEditWork.displayWork(work)
        }

        viewModel.xxx.observe(viewLifecycleOwner) { xxx ->
            Log.d(TAG, "XXXXX onViewCreated: xxx: $xxx")
            addOrEditXxx.displayXxx(xxx)
        }

        viewModel.hasEntries.observe(viewLifecycleOwner) { hasEntries ->
            if (hasEntries) {
                viewBinding.scrollView.visibility = View.VISIBLE
            } else {
                // TODO show overlay with error message
                Toast.makeText(requireContext(), "NO CONTACT WITH THIS ID", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Hide Keyboard initially
        requireActivity().window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )

//        inputMethodManager =
//            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        setClickListeners()
    }

    private fun initViewBindings() {
        contactPicture = viewBinding.contactPicture
        editImages = viewBinding.editImages
        addImages = viewBinding.addImages

        contactName = viewBinding.contactName
        deleteContactName = viewBinding.deleteContactName

        nicknameLayout = viewBinding.nicknameLayout
        addNickname = viewBinding.addNickname

        additionalInfo = viewBinding.additionalInfo
        deleteAdditionalInfo = viewBinding.deleteAdditionalInfo

        contactLayout = viewBinding.contactLayout
        contactHeader = viewBinding.socialmediaLayout.findViewById(R.id.contactHeader)
        contactRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.contactRightIcon)

        personalInfoLayout = viewBinding.personalInfoLayout
        personalHeader = viewBinding.socialmediaLayout.findViewById(R.id.personalHeader)
        personalRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.personalRightIcon)

        professionalInfoLayout = viewBinding.professionalInfoLayout
        professionalHeader = viewBinding.socialmediaLayout.findViewById(R.id.professionalHeader)
        professionalRightIcon =
            viewBinding.socialmediaLayout.findViewById(R.id.professionalRightIcon)

        bodyFeaturesLayout = viewBinding.bodyFeaturesLayout
        bodyFeaturesHeader = viewBinding.socialmediaLayout.findViewById(R.id.bodyFeaturesHeader)
        bodyFeaturesRightIcon =
            viewBinding.socialmediaLayout.findViewById(R.id.bodyFeaturesRightIcon)

        endowmentLayout = viewBinding.endowmentLayout
        endowmentHeader = viewBinding.socialmediaLayout.findViewById(R.id.endowmentHeader)
        endowmentRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.endowmentRightIcon)

        xxxLayout = viewBinding.xxxLayout
        xxxHeader = viewBinding.socialmediaLayout.findViewById(R.id.xxxHeader)
        xxxRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.xxxRightIcon)

        healthLayout = viewBinding.healthLayout
        healthHeader = viewBinding.socialmediaLayout.findViewById(R.id.healthHeader)
        healthRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.healthRightIcon)

        encountersLayout = viewBinding.encountersLayout
        encountersHeader = viewBinding.socialmediaLayout.findViewById(R.id.encounters_header)
        encountersRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.encounters_right_icon)

        socialmediaLayout = viewBinding.socialmediaLayout
        socialMediaHeader = viewBinding.socialmediaLayout.findViewById(R.id.socialMediaHeader)
        socialMediaRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.socialMediaRightIcon)

        notesLayout = viewBinding.notesLayout
        notesHeader = viewBinding.socialmediaLayout.findViewById(R.id.notesHeader)
        notesRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.notesRightIcon)

        labelsLayout = viewBinding.labelsLayout
        labelsHeader = viewBinding.socialmediaLayout.findViewById(R.id.labelsHeader)
        labelsRightIcon = viewBinding.socialmediaLayout.findViewById(R.id.labelsRightIcon)

        metInPersonSwitch = viewBinding.personalInfoLayout[R.id.metInPerson]

        addressLayout = viewBinding.contactLayout[R.id.addressLayout]
        addAddress = viewBinding.contactLayout[R.id.addAddress]

        childrenLayout = viewBinding.contactLayout[R.id.childrenLayout]
        addChildren = viewBinding.contactLayout[R.id.addChildren]

        clubsLayout = viewBinding.contactLayout[R.id.clubsLayout]
        addClubs = viewBinding.contactLayout[R.id.addClubs]

        emailLayout = viewBinding.contactLayout[R.id.emailLayout]
        workLayout = viewBinding.contactLayout[R.id.work_layout]
        encounterLayout = viewBinding.contactLayout[R.id.encounters_layout]
        websitesLayout = viewBinding.contactLayout[R.id.websitesLayout]

        phoneLayout = viewBinding.contactLayout[R.id.phoneLayout]
        addPhone = viewBinding.contactLayout[R.id.addPhone]

        eyeColor = viewBinding.bodyFeaturesLayout[R.id.eyeColor]
        hairColor = viewBinding.bodyFeaturesLayout[R.id.hairColor]
        hairStyle = viewBinding.bodyFeaturesLayout[R.id.hairStyle]
        weight = viewBinding.bodyFeaturesLayout[R.id.weight]
        height = viewBinding.bodyFeaturesLayout[R.id.height]
        bodyType = viewBinding.bodyFeaturesLayout[R.id.bodytype]
        bodyHair = viewBinding.bodyFeaturesLayout[R.id.bodyHair]
        ethnicity = viewBinding.bodyFeaturesLayout[R.id.ethnicity]
        fetishes = viewBinding.bodyFeaturesLayout[R.id.fetishes]

        birthdate = viewBinding.personalInfoLayout[R.id.birthdate]
        age = viewBinding.personalInfoLayout[R.id.age]
        relationshipStatus =
            viewBinding.personalInfoLayout[R.id.relationshipStatus]

        effeminateSlider = viewBinding.personalInfoLayout[R.id.effeminateSlider]
        effeminateUnknown = viewBinding.personalInfoLayout[R.id.effeminateUnknown]
        hiv = viewBinding.healthLayout[R.id.hiv]
        hcv = viewBinding.healthLayout[R.id.hcv]

        length = viewBinding.endowmentLayout[R.id.length]
        girth = viewBinding.endowmentLayout[R.id.girth]
        diameter = viewBinding.endowmentLayout[R.id.diameter]
        feelsLike = viewBinding.endowmentLayout[R.id.feelsLike]
        cutOrUncut = viewBinding.endowmentLayout[R.id.cutOrUncut]
        cutOrUncutUnknownCheckbox =
            viewBinding.endowmentLayout[R.id.cutOrUncutUnknownCheckbox]

        sexualOrientation = viewBinding.xxxLayout[R.id.sexualOrientation]
        sexRole = viewBinding.xxxLayout[R.id.sexRole]
        safeSex = viewBinding.xxxLayout[R.id.safeSex]
        swallowsLoads = viewBinding.xxxLayout[R.id.swallowsLoads]
        loadsUpTheBum = viewBinding.xxxLayout[R.id.takesLoadsUpTheBum]

        note = viewBinding.notesLayout[R.id.note]

        facebook = viewBinding.socialmediaLayout[R.id.facebook]
        flickr = viewBinding.socialmediaLayout[R.id.flickr]
        instagram = viewBinding.socialmediaLayout[R.id.instagram]
        linkedin = viewBinding.socialmediaLayout[R.id.linkedin]
        pinterest = viewBinding.socialmediaLayout[R.id.pinterest]
        pornhub = viewBinding.socialmediaLayout[R.id.pornhub]
        redtube = viewBinding.socialmediaLayout[R.id.redtube]
        spotify = viewBinding.socialmediaLayout[R.id.spotify]
        tumblr = viewBinding.socialmediaLayout[R.id.tumblr]
        twitter = viewBinding.socialmediaLayout[R.id.twitter]
        vimeo = viewBinding.socialmediaLayout[R.id.vimeo]
        xing = viewBinding.socialmediaLayout[R.id.xing]
        xtube = viewBinding.socialmediaLayout[R.id.xtube]
        youtube = viewBinding.socialmediaLayout[R.id.youtube]
        lineMessenger = viewBinding.socialmediaLayout[R.id.lineMessenger]
        skype = viewBinding.socialmediaLayout[R.id.skype]
        snapchat = viewBinding.socialmediaLayout[R.id.snapchat]
        viber = viewBinding.socialmediaLayout[R.id.viber]
        weChat = viewBinding.socialmediaLayout[R.id.weChat]
        whatsapp = viewBinding.socialmediaLayout[R.id.whatsapp]
        adam4adam = viewBinding.socialmediaLayout[R.id.adam4adam]
        bbrt = viewBinding.socialmediaLayout[R.id.bbrt]
        dudesnude = viewBinding.socialmediaLayout[R.id.dudesnude]
        gaycom = viewBinding.socialmediaLayout[R.id.gaycom]
        gaydar = viewBinding.socialmediaLayout[R.id.gaydar]
        grindr = viewBinding.socialmediaLayout[R.id.grindr]
        growlr = viewBinding.socialmediaLayout[R.id.growlr]
        hornet = viewBinding.socialmediaLayout[R.id.hornet]
        jackd = viewBinding.socialmediaLayout[R.id.jackd]
        manhunt = viewBinding.socialmediaLayout[R.id.manhunt]
        planetRomeo = viewBinding.socialmediaLayout[R.id.planetRomeo]
        recon = viewBinding.socialmediaLayout[R.id.recon]
        scruff = viewBinding.socialmediaLayout[R.id.scruff]
        tinder = viewBinding.socialmediaLayout[R.id.tinder]
        miiverse = viewBinding.socialmediaLayout[R.id.miiverse]
        nintendonetwork = viewBinding.socialmediaLayout[R.id.nintendonetwork]
        playstationnetwork =
            viewBinding.socialmediaLayout[R.id.playstationnetwork]
        xboxgamertag = viewBinding.socialmediaLayout[R.id.xboxgamertag]


        deleteBodyType = viewBinding.bodyFeaturesLayout[R.id.deleteBodytype]
        deleteEthnicity = viewBinding.bodyFeaturesLayout[R.id.deleteEthnicity]
        deleteFetishes = viewBinding.xxxLayout[R.id.deleteFetishes]
        deleteBirthdate = viewBinding.personalInfoLayout[R.id.deleteBirthdate]
        deleteAge = viewBinding.personalInfoLayout[R.id.deleteAge]
        deleteRelationshipStatus =
            viewBinding.personalInfoLayout[R.id.deleteRelationshipStatus]
        deleteHiv = viewBinding.healthLayout[R.id.deleteHiv]
        deleteHcv = viewBinding.healthLayout[R.id.deleteHcv]
        deleteSexualOrientation = viewBinding.xxxLayout[R.id.deleteSexualOrientation]
        deleteSexRole = viewBinding.xxxLayout[R.id.deleteSexRole]
        deleteSafeSex = viewBinding.xxxLayout[R.id.deleteSafeSex]
        deleteSwallowsLoads = viewBinding.xxxLayout[R.id.deleteSwallowsLoads]
        deleteUpTheBum = viewBinding.xxxLayout[R.id.deleteUpTheBum]
        deleteLength = viewBinding.endowmentLayout[R.id.deleteLength]
        deleteGirth = viewBinding.endowmentLayout[R.id.deleteGirth]
        deleteDiameter = viewBinding.endowmentLayout[R.id.deleteDiameter]
        deleteFeelsLike = viewBinding.endowmentLayout[R.id.deleteFeelsLike]
        deleteNote = viewBinding.notesLayout[R.id.deleteNote]
        deleteLabels = viewBinding.labelsLayout[R.id.delete_labels]

        deleteFacebook = viewBinding.socialmediaLayout[R.id.deleteFacebook]
        deleteFlickr = viewBinding.socialmediaLayout[R.id.deleteFlickr]
        deleteInstagram = viewBinding.socialmediaLayout[R.id.deleteInstagram]
        deleteLinkedin = viewBinding.socialmediaLayout[R.id.deleteLinkedin]
        deletePinterest = viewBinding.socialmediaLayout[R.id.deletePinterest]
        deletePornhub = viewBinding.socialmediaLayout[R.id.deletePornhub]
        deleteRedtube = viewBinding.socialmediaLayout[R.id.deleteRedtube]
        deleteSpotify = viewBinding.socialmediaLayout[R.id.deleteSpotify]
        deleteTumblr = viewBinding.socialmediaLayout[R.id.deleteTumblr]
        deleteTwitter = viewBinding.socialmediaLayout[R.id.deleteTwitter]
        deleteVimeo = viewBinding.socialmediaLayout[R.id.deleteVimeo]
        deleteXing = viewBinding.socialmediaLayout[R.id.deleteXing]
        deleteXtube = viewBinding.socialmediaLayout[R.id.deleteXtube]
        deleteYoutube = viewBinding.socialmediaLayout[R.id.deleteYoutube]
        deleteLineMessenger = viewBinding.socialmediaLayout[R.id.deleteLineMessenger]
        deleteSkype = viewBinding.socialmediaLayout[R.id.deleteSkype]
        deleteSnapchat = viewBinding.socialmediaLayout[R.id.deleteSnapchat]
        deleteViber = viewBinding.socialmediaLayout[R.id.deleteViber]
        deleteWeChat = viewBinding.socialmediaLayout[R.id.deleteWeChat]
        deleteWhatsapp = viewBinding.socialmediaLayout[R.id.deleteWhatsapp]
        deleteAdam4adam = viewBinding.socialmediaLayout[R.id.deleteAdam4adam]
        deleteBbrt = viewBinding.socialmediaLayout[R.id.deleteBbrt]
        deleteDudesnude = viewBinding.socialmediaLayout[R.id.deleteDudesnude]
        deleteGaycom = viewBinding.socialmediaLayout[R.id.deleteGaycom]
        deleteGaydar = viewBinding.socialmediaLayout[R.id.deleteGaydar]
        deleteGrindr = viewBinding.socialmediaLayout[R.id.deleteGrindr]
        deleteGrowlr = viewBinding.socialmediaLayout[R.id.deleteGrowlr]
        deleteHornet = viewBinding.socialmediaLayout[R.id.deleteHornet]
        deleteJackd = viewBinding.socialmediaLayout[R.id.deleteJackd]
        deleteManhunt = viewBinding.socialmediaLayout[R.id.deleteManhunt]
        deletePlanetRomeo = viewBinding.socialmediaLayout[R.id.deletePlanetRomeo]
        deleteRecon = viewBinding.socialmediaLayout[R.id.deleteRecon]
        deleteScruff = viewBinding.socialmediaLayout[R.id.deleteScruff]
        deleteTinder = viewBinding.socialmediaLayout[R.id.deleteTinder]
        deleteMiiverse = viewBinding.socialmediaLayout[R.id.deleteMiiverse]
        deleteNintendonetwork =
            viewBinding.socialmediaLayout[R.id.deleteNintendonetwork]
        deletePlaystationnetwork =
            viewBinding.socialmediaLayout[R.id.deletePlaystationnetwork]
        deleteXboxgamertag = viewBinding.socialmediaLayout[R.id.deleteXboxgamertag]
    }

    private fun setExandableLayoutBehaviour() {
        contactLayout.headerLayout.setOnClickListener {
            if (contactLayout.isOpened) {
                contactLayout.hide()
                contactRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                contactLayout.show()
                contactRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        personalInfoLayout.headerLayout.setOnClickListener {
            if (personalInfoLayout.isOpened) {
                personalInfoLayout.hide()
                personalRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                personalInfoLayout.show()
                personalRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        professionalInfoLayout.headerLayout.setOnClickListener {
            if (professionalInfoLayout.isOpened) {
                professionalInfoLayout.hide()
                professionalRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                professionalInfoLayout.show()
                professionalRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        socialmediaLayout.headerLayout.setOnClickListener {
            if (socialmediaLayout.isOpened) {
                socialmediaLayout.hide()
                socialMediaRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                socialmediaLayout.show()
                socialMediaRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        bodyFeaturesLayout.headerLayout.setOnClickListener {
            if (bodyFeaturesLayout.isOpened) {
                bodyFeaturesLayout.hide()
                bodyFeaturesRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                bodyFeaturesLayout.show()
                bodyFeaturesRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        if (SpecificValues.SHOW_XRATED) {
            endowmentLayout.headerLayout.setOnClickListener {
                if (endowmentLayout.isOpened) {
                    endowmentLayout.hide()
                    endowmentRightIcon.setImageResource(R.drawable.ic_expand_more)
                } else {
                    endowmentLayout.show()
                    endowmentRightIcon.setImageResource(R.drawable.ic_expand_less)
                }
            }
            xxxLayout.headerLayout.setOnClickListener {
                if (xxxLayout.isOpened) {
                    xxxLayout.hide()
                    xxxRightIcon.setImageResource(R.drawable.ic_expand_more)
                } else {
                    xxxLayout.show()
                    xxxRightIcon.setImageResource(R.drawable.ic_expand_less)
                }
            }
            healthLayout.headerLayout.setOnClickListener {
                if (healthLayout.isOpened) {
                    healthLayout.hide()
                    healthRightIcon.setImageResource(R.drawable.ic_expand_more)
                } else {
                    healthLayout.show()
                    healthRightIcon.setImageResource(R.drawable.ic_expand_less)
                }
            }
        } else {
            endowmentLayout.visibility = View.GONE
            xxxLayout.visibility = View.GONE
            healthLayout.visibility = View.GONE
//            effeminateLayout.setVisibility(View.GONE)
//            effeminatePadding.setVisibility(View.GONE)
        }
        encountersLayout.headerLayout.setOnClickListener { v: View? ->
            if (encountersLayout.isOpened) {
                encountersLayout.hide()
                encountersRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                encountersLayout.show()
                encountersRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }
        notesLayout.headerLayout.setOnClickListener { v: View? ->
            if (notesLayout.isOpened) {
                notesLayout.hide()
                notesRightIcon.setImageResource(R.drawable.ic_expand_more)
            } else {
                notesLayout.show()
                notesRightIcon.setImageResource(R.drawable.ic_expand_less)
            }
        }

//        labelsLayout.getHeaderLayout().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (labelsLayout.isOpened()) {
//                    labelsLayout.hide();
//                    labelsRightIcon.setImageResource(R.drawable.ic_expand_more);
//                } else {
//                    labelsLayout.show();
//                    labelsRightIcon.setImageResource(R.drawable.ic_expand_less);
//                }
//            }
//        });
    }

    private fun refreshContactCardMainPic() {
        Glide.with(requireContext())
            .load(
                mediaTools.getFileFromInternalStorage(
                    contactWithDetails.contact,
                    Const.GALLERY_PUBLIC
                )
            )
            .placeholder(R.drawable.silhouette).into(contactPicture)

        editImages.visibility = View.VISIBLE
    }

    fun revertToSilhouetteImage() {
        Glide.with(requireContext()).load(R.drawable.silhouette).into(contactPicture)
        editImages.visibility = View.INVISIBLE
    }

    // Used only for EDIT MODE (because ADD ContactEntity has no pre-filled values)
    private fun fillFieldsWithData() {
        additionalInfo.setText(contactWithDetails.contact.additionalInfo)

        // TODO met in person ein häkchen hinter dem namen?
        // TODO met in person ein häkchen hinter dem namen?
        // TODO met in person ein häkchen hinter dem namen?

        metInPersonSwitch.isChecked = contactWithDetails.contact.isMetInPerson

        // wird in OnViewCreated() via observer ausgeführt:
        // wird in OnViewCreated() via observer ausgeführt:
        // wird in OnViewCreated() via observer ausgeführt:
        // wird in OnViewCreated() via observer ausgeführt:
//        contactWithDetails.addresses?.forEach { a ->
//            addOrEditAddresses.addAddressLayout(a, false)
//        }


        contactWithDetails.emails?.forEach { e ->
            addOrEditEmails.addEmailLayout(e, false)
        }

        addOrEditEncounters.fillEncountersOverviewLayout(contactWithDetails.encounters?.toMutableList())
        addOrEditEthnicity.displayEthnicity()
        addOrEditFetishes.displayFetishes()

        contactWithDetails.nicknames?.forEach { n ->
            addOrEditNicknames.addNicknameLayout(n, false)
        }

        contactWithDetails.phones?.forEach { p ->
            addOrEditPhone.addPhoneLayout(p, false)
        }

        contactWithDetails.websites?.forEach { w ->
            addOrEditWebsite.addWebsiteLayout(w, false)
        }

        addOrEditPersonal.displayBirthdate()
        addOrEditPersonal.displayAge()
        addOrEditPersonal.displayRelationship()
        addOrEditPersonal.displayChildren()
        addOrEditPersonal.displayEffeminate()


        // TODO FIXME die N to N relation contact-contactlabel-label rausfinden mit ROOM.....
        // TODO FIXME die N to N relation contact-contactlabel-label rausfinden mit ROOM.....
        // TODO FIXME die N to N relation contact-contactlabel-label rausfinden mit ROOM.....
        // TODO FIXME die N to N relation contact-contactlabel-label rausfinden mit ROOM.....
        //tempLabelObj = contactLabelEntityDao.getContactLabelByContactId(newContact.getId());
//        contactLabelsArrayList = contactLabelDao.getLabelsWithContactId(newContact.id)
//        val labelList: MutableList<String?> = ArrayList()
//        for (label in contactLabelsArrayList) {
//            labelList.add(label.label)
//        }
//        labels.setText(TextUtils.join(", ", labelList))
//        if (labels.getText().toString().trim { it <= ' ' }.length > 0) {
//            deleteLabels.visibility = View.VISIBLE
//        }
    }

    fun getIndexOfSpinner(spinner: Spinner, myString: String?): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                index = i
                break
            }
        }
        return index
    }

    fun updateContactInDatabase() {
        contactWithDetails.contact.lastMod = Date()
        viewModel.updateContact(contactWithDetails.contact)
    }

    private fun setClickListeners() {
        bodyType.setOnClickListener {
            addOrEditBodyType.bodyTypeClick()
        }

        deleteBodyType.setOnClickListener {
            addOrEditBodyType.deleteBodyTypeClicked()
        }

        addChildren.setOnClickListener {
            // TODO show add child dialog
        }

        addClubs.setOnClickListener {
            // TODO show add club dialog
        }
    }
}