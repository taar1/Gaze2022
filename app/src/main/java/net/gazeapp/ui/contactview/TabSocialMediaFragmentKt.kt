package net.gazeapp.ui.contactview

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import net.gazeapp.R
import net.gazeapp.data.model.SocialMedia
import net.gazeapp.databinding.ContactviewTabSocialmediaContainerKtBinding
import net.gazeapp.databinding.SocialMediaItemConstraintLayoutBinding
import net.gazeapp.utilities.GazeTools
import javax.inject.Inject

class TabSocialMediaFragmentKt : Fragment(R.layout.contactview_tab_socialmedia_container_kt) {

    @Inject
    lateinit var tools: GazeTools

    private val activity: ContactViewWithViewPagerTabActivity? = null

    private lateinit var messengerCard: CardView
    private lateinit var socialMediaCard: CardView
    private lateinit var datingCard: CardView
    private lateinit var gamingCard: CardView

    private lateinit var noInfoCard: CardView

    private lateinit var outerLayout: ConstraintLayout
    private lateinit var socialMediaInnerLayout: LinearLayout
    private lateinit var messengerInnerLayout: LinearLayout
    private lateinit var datingInnerLayout: LinearLayout
    private lateinit var gamingInnerLayout: LinearLayout

    private var mAppleIdText: String? = null
    private var mFacebookText: String? = null
    private var mFlickrText: String? = null
    private var mInstagramText: String? = null
    private var mLinkedinText: String? = null
    private var mPinterestText: String? = null
    private var mPornhubText: String? = null
    private var mRedtubeText: String? = null
    private var mSpotifyText: String? = null
    private var mTumblrText: String? = null
    private var mTwitterText: String? = null
    private var mVimeoText: String? = null
    private var mXhamsterText: String? = null
    private var mXtubeText: String? = null
    private var mXingText: String? = null
    private var mYoutubeText: String? = null
    private var mLineText: String? = null
    private var skypeText: String? = null
    private var snapchatText: String? = null
    private var viberText: String? = null
    private var wechatText: String? = null
    private var whatsappText: String? = null
    private var adam4adamText: String? = null
    private var bbrtText: String? = null
    private var boyahoyText: String? = null
    private var dudesnudeText: String? = null
    private var gaycomText: String? = null
    private var gaydarText: String? = null
    private var grindrText: String? = null
    private var growlrText: String? = null
    private var hornetText: String? = null
    private var jackdText: String? = null
    private var manhuntText: String? = null
    private var planetromeoText: String? = null
    private var reconText: String? = null
    private var scruffText: String? = null
    private var miiverseText: String? = null
    private var tinderText: String? = null
    private var nintendonetworkText: String? = null
    private var playstationnetworkText: String? = null
    private var xboxgamertagText: String? = null

    private var contactId: Int = 0
    private lateinit var viewBinding: ContactviewTabSocialmediaContainerKtBinding

    companion object {
        private const val TAG = "TabSocialMediaFragmentKt"
        private const val CONTACT_ID = "contactId"

        fun newInstance(contactId: Int) = TabSocialMediaFragmentKt().apply {
            arguments = bundleOf(CONTACT_ID to contactId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getInt(CONTACT_ID)?.let {
            contactId = it
        }
    }

    val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(
            contactId, Application()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = ContactviewTabSocialmediaContainerKtBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindings()

        viewModel.contactWithDetails.observe(viewLifecycleOwner, { contactWithDetails ->
            contactWithDetails.apply {
                prepareData(contactWithDetails.socialMedia)
                updateUI()
            }
        })

        viewModel.hasEntries.observe(viewLifecycleOwner, { hasEntries ->
            if (hasEntries) {
                noInfoCard.visibility = View.GONE
            } else {
                noInfoCard.visibility = View.VISIBLE
            }
        })
    }

    private fun initViewBindings() {
        noInfoCard = viewBinding.noInfoCard

        outerLayout = viewBinding.outerLayout

        socialMediaCard = viewBinding.socialmediaCard
        messengerCard = viewBinding.messengerCard
        datingCard = viewBinding.datingCard
        gamingCard = viewBinding.gamingCard

        socialMediaInnerLayout = viewBinding.socialnetworksInnerLayout
        messengerInnerLayout = viewBinding.messengersInnerLayout
        datingInnerLayout = viewBinding.datingInnerLayout
        gamingInnerLayout = viewBinding.gamingInnerLayout
    }


    private fun prepareData(socialMedia: SocialMedia?) {
        // SOCIAL NETWORKS
        // SOCIAL NETWORKS
        // SOCIAL NETWORKS
        // SOCIAL NETWORKS
        if (!socialMedia?.facebook.isNullOrEmpty()) {
            mFacebookText = socialMedia?.facebook?.trim { it <= ' ' }
        }
        if (!socialMedia?.flickr.isNullOrEmpty()) {
            mFlickrText = socialMedia?.flickr?.trim { it <= ' ' }
        }
        if (!socialMedia?.instagram.isNullOrEmpty()) {
            mInstagramText = socialMedia?.instagram?.trim { it <= ' ' }
        }
        if (!socialMedia?.linkedIn.isNullOrEmpty()) {
            mLinkedinText = socialMedia?.linkedIn?.trim { it <= ' ' }
        }
        if (!socialMedia?.pinterest.isNullOrEmpty()) {
            mPinterestText = socialMedia?.pinterest?.trim { it <= ' ' }
        }
        if (!socialMedia?.pornhub.isNullOrEmpty()) {
            mPornhubText = socialMedia?.pornhub?.trim { it <= ' ' }
        }
        if (!socialMedia?.redtube.isNullOrEmpty()) {
            mRedtubeText = socialMedia?.redtube?.trim { it <= ' ' }
        }
        if (!socialMedia?.spotify.isNullOrEmpty()) {
            mSpotifyText = socialMedia?.spotify?.trim { it <= ' ' }
        }
        if (!socialMedia?.tumblr.isNullOrEmpty()) {
            mTumblrText = socialMedia?.tumblr?.trim { it <= ' ' }
        }
        if (!socialMedia?.twitter.isNullOrEmpty()) {
            mTwitterText = socialMedia?.twitter?.trim { it <= ' ' }
        }
        if (!socialMedia?.vimeo.isNullOrEmpty()) {
            mVimeoText = socialMedia?.vimeo?.trim { it <= ' ' }
        }
        if (!socialMedia?.xing.isNullOrEmpty()) {
            mXingText = socialMedia?.xing?.trim { it <= ' ' }
        }
        if (!socialMedia?.xhamster.isNullOrEmpty()) {
            mXhamsterText = socialMedia?.xhamster?.trim { it <= ' ' }
        }
        if (!socialMedia?.xtube.isNullOrEmpty()) {
            mXtubeText = socialMedia?.xtube?.trim { it <= ' ' }
        }
        if (!socialMedia?.youtube.isNullOrEmpty()) {
            mYoutubeText = socialMedia?.youtube?.trim { it <= ' ' }
        }


        // MESSENGERS
        // MESSENGERS
        // MESSENGERS
        // MESSENGERS
        if (!socialMedia?.lineMessenger.isNullOrEmpty()) {
            mLineText = socialMedia?.lineMessenger?.trim { it <= ' ' }
        }
        if (!socialMedia?.skype.isNullOrEmpty()) {
            skypeText = socialMedia?.skype?.trim { it <= ' ' }
        }
        if (!socialMedia?.snapChat.isNullOrEmpty()) {
            snapchatText = socialMedia?.snapChat?.trim { it <= ' ' }
        }
        if (!socialMedia?.viber.isNullOrEmpty()) {
            viberText = socialMedia?.viber?.trim { it <= ' ' }
        }
        if (!socialMedia?.weChat.isNullOrEmpty()) {
            wechatText = socialMedia?.weChat?.trim { it <= ' ' }
        }
        if (!socialMedia?.whatsapp.isNullOrEmpty()) {
            whatsappText = socialMedia?.whatsapp?.trim { it <= ' ' }
        }


        // DATING APPS
        // DATING APPS
        // DATING APPS
        // DATING APPS
        if (!socialMedia?.adam4adam.isNullOrEmpty()) {
            adam4adamText = socialMedia?.adam4adam?.trim { it <= ' ' }
        }
        if (!socialMedia?.bbrt.isNullOrEmpty()) {
            bbrtText = socialMedia?.bbrt?.trim { it <= ' ' }
        }
        if (!socialMedia?.boyahoy.isNullOrEmpty()) {
            boyahoyText = socialMedia?.boyahoy?.trim { it <= ' ' }
        }
        if (!socialMedia?.dudesnude.isNullOrEmpty()) {
            dudesnudeText = socialMedia?.dudesnude?.trim { it <= ' ' }
        }
        if (!socialMedia?.gaycom.isNullOrEmpty()) {
            gaycomText = socialMedia?.gaycom?.trim { it <= ' ' }
        }
        if (!socialMedia?.gaydar.isNullOrEmpty()) {
            gaydarText = socialMedia?.gaydar?.trim { it <= ' ' }
        }
        if (!socialMedia?.grindr.isNullOrEmpty()) {
            grindrText = socialMedia?.grindr?.trim { it <= ' ' }
        }
        if (!socialMedia?.growlr.isNullOrEmpty()) {
            growlrText = socialMedia?.growlr?.trim { it <= ' ' }
        }
        if (!socialMedia?.hornet.isNullOrEmpty()) {
            hornetText = socialMedia?.hornet?.trim { it <= ' ' }
        }
        if (!socialMedia?.jackd.isNullOrEmpty()) {
            jackdText = socialMedia?.jackd?.trim { it <= ' ' }
        }
        if (!socialMedia?.manhunt.isNullOrEmpty()) {
            manhuntText = socialMedia?.manhunt?.trim { it <= ' ' }
        }
        if (!socialMedia?.planetRomeo.isNullOrEmpty()) {
            planetromeoText = socialMedia?.planetRomeo?.trim { it <= ' ' }
        }
        if (!socialMedia?.recon.isNullOrEmpty()) {
            reconText = socialMedia?.recon?.trim { it <= ' ' }
        }
        if (!socialMedia?.scruff.isNullOrEmpty()) {
            scruffText = socialMedia?.scruff?.trim { it <= ' ' }
        }
        if (!socialMedia?.tinder.isNullOrEmpty()) {
            tinderText = socialMedia?.tinder?.trim { it <= ' ' }
        }


        // GAMING
        // GAMING
        // GAMING
        // GAMING
        if (!socialMedia?.miiverse.isNullOrEmpty()) {
            miiverseText = socialMedia?.miiverse?.trim { it <= ' ' }
        }
        if (!socialMedia?.nintendoNetwork.isNullOrEmpty()) {
            nintendonetworkText = socialMedia?.nintendoNetwork?.trim { it <= ' ' }
        }
        if (!socialMedia?.playstationNetwork.isNullOrEmpty()) {
            playstationnetworkText = socialMedia?.playstationNetwork?.trim { it <= ' ' }
        }
        if (!socialMedia?.xboxGamertag.isNullOrEmpty()) {
            xboxgamertagText = socialMedia?.xboxGamertag?.trim { it <= ' ' }
        }
    }

    private fun generateItem(
        cardCategory: CardCategory,
        elementText: String?,
        elementIcon: Int,
        hintText: String,
        urlReplacement: String?
    ) {
        if (!elementText.isNullOrEmpty()) {
            val itemBinding =
                SocialMediaItemConstraintLayoutBinding.inflate(
                    requireContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE
                    ) as LayoutInflater
                )

            itemBinding.smIcon.load(elementIcon)

            itemBinding.smHint.text = hintText
            itemBinding.smValue.text = tools.removeHttpPrefix(elementText)

            itemBinding.smValue.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                if (URLUtil.isValidUrl(elementText)) {
                    i.data = Uri.parse(elementText)
                } else {
                    i.data = Uri.parse(urlReplacement)
                }
                startActivity(i)
            }

            itemBinding.root.setOnLongClickListener {
                tools.copyToClipboard(elementText)

                Toast.makeText(
                    activity,
                    getString(R.string.copied_to_clipboard, elementText),
                    Toast.LENGTH_SHORT
                ).show()

                true
            }

            when (cardCategory) {
                CardCategory.SOCIAL_NETWORK -> {
                    socialMediaInnerLayout.addView(itemBinding.root)
                    deleteSocialNetworksCard = false
                }
                CardCategory.MESSENGER -> {
                    messengerInnerLayout.addView(itemBinding.root)
                    deleteSocialNetworksCard = false
                }
                CardCategory.DATING -> {
                    datingInnerLayout.addView(itemBinding.root)
                    deleteDatingCard = false
                }
                CardCategory.GAMING -> {
                    gamingInnerLayout.addView(itemBinding.root)
                    deleteGamingCard = false
                }
            }
        }
    }

    enum class CardCategory {
        SOCIAL_NETWORK, MESSENGER, DATING, GAMING
    }

    var deleteSocialNetworksCard = true
    var deleteMessengerCard = true
    var deleteDatingCard = true
    var deleteGamingCard = true

    fun updateUI() {

        // SOCIAL NETWORKS
        // SOCIAL NETWORKS
        // SOCIAL NETWORKS

        // FACEBOOK
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mFacebookText,
            elementIcon = R.drawable.brand_facebook_24dp,
            hintText = getString(R.string.facebook),
            urlReplacement = "https://www.facebook.com/$mFacebookText"
        )
//        if (!mFacebookText.isNullOrEmpty()) {
//            val itemBinding =
//                SocialMediaItemConstraintLayoutBinding.inflate(
//                    requireContext().getSystemService(
//                        Context.LAYOUT_INFLATER_SERVICE
//                    ) as LayoutInflater
//                )
//
//            Glide.with(requireContext()).load(R.drawable.brand_facebook_24dp)
//                .into(itemBinding.smIcon)
//
//            itemBinding.smHint.text = R.string.facebook.toString()
//            itemBinding.smValue.text = tools.removeHttpPrefix(mFacebookText)
//
//            itemBinding.smValue.setOnClickListener {
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mFacebookText)) {
//                    i.data = Uri.parse(mFacebookText)
//                } else {
//                    i.data = Uri.parse("https://www.facebook.com/$mFacebookText")
//                }
//                startActivity(i)
//            }
//
//            itemBinding.root.setOnLongClickListener {
//                tools.copyToClipboard(mFacebookText)
//
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mFacebookText),
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                true
//            }
//
//            socialMediaInnerLayout.addView(itemBinding.root)
//
////            val facebookIcon = facebookView.findViewById<ImageView>(R.id.sm_icon)
////            Glide.with(context!!).load(R.drawable.brand_facebook_24dp).into(facebookIcon)
////            val facebookHint = facebookView.findViewById<TextView>(R.id.sm_hint)
////            facebookHint.setText(R.string.facebook)
////            val facebookValue = facebookView.findViewById<TextView>(R.id.sm_value)
////            facebookValue.text = tools.removeHttpPrefix(mFacebookText)
////            facebookValue.setOnClickListener { view: View? ->
////                val i = Intent(Intent.ACTION_VIEW)
////                if (URLUtil.isValidUrl(mFacebookText)) {
////                    i.data = Uri.parse(mFacebookText)
////                } else {
////                    i.data = Uri.parse("https://www.facebook.com/$mFacebookText")
////                }
////                startActivity(i)
////            }
////            socialmediaInnerLayout!!.addView(facebookView)
//            deleteSocialNetworksCard = false
//        }


        // FLICKR
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mFlickrText,
            elementIcon = R.drawable.brand_flickr_24dp,
            hintText = getString(R.string.flickr),
            urlReplacement = "https://www.flickr.com/photos/$mFlickrText"
        )
//        if (mFlickrText != null && mFlickrText!!.length > 0) {
//            val flickrView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            flickrView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mFlickrText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mFlickrText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val flickrIcon = flickrView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_flickr_24dp).into(flickrIcon)
//            val flickrHint = flickrView.findViewById<TextView>(R.id.sm_hint)
//            flickrHint.setText(R.string.flickr)
//            val flickrValue = flickrView.findViewById<TextView>(R.id.sm_value)
//            flickrValue.text = tools.removeHttpPrefix(mFlickrText)
//            flickrValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mFlickrText)) {
//                    i.data = Uri.parse(mFlickrText)
//                } else {
//                    i.data = Uri.parse("https://www.flickr.com/photos/$mFlickrText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(flickrView)
//            deleteSocialNetworksCard = false
//        }

        // INSTAGRAM
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mInstagramText,
            elementIcon = R.drawable.brand_instagram_24dp,
            hintText = getString(R.string.instagram),
            urlReplacement = "https://www.instagram.com/$mInstagramText"
        )
//        if (mInstagramText != null && mInstagramText!!.length > 0) {
//            val instagramView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            instagramView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mInstagramText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mInstagramText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val instagramIcon = instagramView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_instagram_24dp).into(instagramIcon)
//            val instagramHint = instagramView.findViewById<TextView>(R.id.sm_hint)
//            instagramHint.setText(R.string.instagram)
//            val instagramValue = instagramView.findViewById<TextView>(R.id.sm_value)
//            instagramValue.text = tools.removeHttpPrefix(mInstagramText)
//            instagramValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mInstagramText)) {
//                    i.data = Uri.parse(mInstagramText)
//                } else {
//                    i.data = Uri.parse("https://www.instagram.com/$mInstagramText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(instagramView)
//            deleteSocialNetworksCard = false
//        }

        // LINKED IN
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mLinkedinText,
            elementIcon = R.drawable.brand_linkedin_24dp,
            hintText = getString(R.string.linkedin),
            urlReplacement = "https://www.linkedin.com/in/$mLinkedinText"
        )
//        if (mLinkedinText != null && mLinkedinText!!.length > 0) {
//            val linkedInView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            linkedInView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mLinkedinText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mLinkedinText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val linkedInIcon = linkedInView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_linkedin_24dp).into(linkedInIcon)
//            val linkedInHint = linkedInView.findViewById<TextView>(R.id.sm_hint)
//            linkedInHint.setText(R.string.linkedin)
//            val linkedInValue = linkedInView.findViewById<TextView>(R.id.sm_value)
//            linkedInValue.text = tools.removeHttpPrefix(mLinkedinText)
//            linkedInValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mLinkedinText)) {
//                    i.data = Uri.parse(mLinkedinText)
//                } else {
//                    i.data = Uri.parse("https://www.linkedin.com/in/$mLinkedinText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(linkedInView)
//            deleteSocialNetworksCard = false
//        }


        // PINTEREST
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mPinterestText,
            elementIcon = R.drawable.brand_pinterest_24dp,
            hintText = getString(R.string.pinterest),
            urlReplacement = "https://www.pinterest.com/$mPinterestText"
        )
//        if (mPinterestText != null && mPinterestText!!.length > 0) {
//            val pinterestView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            pinterestView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mPinterestText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mPinterestText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val pinterestIcon = pinterestView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_pinterest_24dp).into(pinterestIcon)
//            val pinterestHint = pinterestView.findViewById<TextView>(R.id.sm_hint)
//            pinterestHint.setText(R.string.pinterest)
//            val pinterestValue = pinterestView.findViewById<TextView>(R.id.sm_value)
//            pinterestValue.text = tools.removeHttpPrefix(mPinterestText)
//            pinterestValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mPinterestText)) {
//                    i.data = Uri.parse(mPinterestText)
//                } else {
//                    i.data = Uri.parse("https://www.pinterest.com/$mPinterestText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(pinterestView)
//            deleteSocialNetworksCard = false
//        }

        // PORNHUB
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mPornhubText,
            elementIcon = R.drawable.brand_pornhub_24dp,
            hintText = getString(R.string.pornhub),
            urlReplacement = "http://www.pornhub.com/users/$mPornhubText"
        )

//        if (mPornhubText != null && mPornhubText!!.length > 0) {
//            val pornhubView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            pornhubView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mPornhubText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mPornhubText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val pornhubIcon = pornhubView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_pornhub_24dp).into(pornhubIcon)
//            val pornhubHint = pornhubView.findViewById<TextView>(R.id.sm_hint)
//            pornhubHint.setText(R.string.pornhub)
//            val pornhubValue = pornhubView.findViewById<TextView>(R.id.sm_value)
//            pornhubValue.text = tools.removeHttpPrefix(mPornhubText)
//            pornhubValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mPornhubText)) {
//                    i.data = Uri.parse(mPornhubText)
//                } else {
//                    i.data = Uri.parse("http://www.pornhub.com/users/$mPornhubText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(pornhubView)
//            deleteSocialNetworksCard = false
//        }


        // REDTUBE
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mRedtubeText,
            elementIcon = R.drawable.brand_redtube_24dp,
            hintText = getString(R.string.redtube),
            urlReplacement = "http://www.redtube.com/$mRedtubeText"
        )
//        if (mRedtubeText != null && mRedtubeText!!.length > 0) {
//            val redtubeView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            redtubeView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mRedtubeText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mRedtubeText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val redtubeIcon = redtubeView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_redtube_24dp).into(redtubeIcon)
//            val redtubeHint = redtubeView.findViewById<TextView>(R.id.sm_hint)
//            redtubeHint.setText(R.string.redtube)
//            val redtubeValue = redtubeView.findViewById<TextView>(R.id.sm_value)
//            redtubeValue.text = tools.removeHttpPrefix(mRedtubeText)
//            redtubeValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mRedtubeText)) {
//                    i.data = Uri.parse(mRedtubeText)
//                } else {
//                    i.data = Uri.parse("http://www.redtube.com/$mRedtubeText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(redtubeView)
//            deleteSocialNetworksCard = false
//        }


        // SPOTIFY
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mSpotifyText,
            elementIcon = R.drawable.brand_spotify_24dp,
            hintText = getString(R.string.spotify),
            urlReplacement = "https://www.spotify.com/$mSpotifyText"
        )
//        if (mSpotifyText != null && mSpotifyText!!.length > 0) {
//            val spotifyView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            spotifyView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mSpotifyText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mSpotifyText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val spotifyIcon = spotifyView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_spotify_24dp).into(spotifyIcon)
//            val spotifyHint = spotifyView.findViewById<TextView>(R.id.sm_hint)
//            spotifyHint.setText(R.string.spotify)
//            val spotifyValue = spotifyView.findViewById<TextView>(R.id.sm_value)
//            spotifyValue.text = tools.removeHttpPrefix(mSpotifyText)
//            spotifyValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mSpotifyText)) {
//                    i.data = Uri.parse(mSpotifyText)
//                } else {
//                    i.data = Uri.parse("https://www.spotify.com/$mSpotifyText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(spotifyView)
//            deleteSocialNetworksCard = false
//        }


        // TUMBLR
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mTumblrText,
            elementIcon = R.drawable.brand_tumblr_24dp,
            hintText = getString(R.string.tumblr),
            urlReplacement = "https://$mTumblrText.tumblr.com/"
        )
//        if (mTumblrText != null && mTumblrText!!.length > 0) {
//            val tumblrView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            tumblrView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mTumblrText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mTumblrText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val tumblrIcon = tumblrView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_tumblr_24dp).into(tumblrIcon)
//            val tumblrHint = tumblrView.findViewById<TextView>(R.id.sm_hint)
//            tumblrHint.setText(R.string.tumblr)
//            val tumblrValue = tumblrView.findViewById<TextView>(R.id.sm_value)
//            tumblrValue.text = tools.removeHttpPrefix(mTumblrText)
//            tumblrValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mTumblrText)) {
//                    i.data = Uri.parse(mTumblrText)
//                } else {
//                    i.data = Uri.parse("https://$mTumblrText.tumblr.com/")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(tumblrView)
//            deleteSocialNetworksCard = false
//        }


        // TWITTER
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mTwitterText,
            elementIcon = R.drawable.brand_twitter_24dp,
            hintText = getString(R.string.twitter),
            urlReplacement = "https://twitter.com/$mTwitterText"
        )
//        if (mTwitterText != null && mTwitterText!!.length > 0) {
//            val twitterView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            twitterView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mTwitterText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mTwitterText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val twitterIcon = twitterView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_twitter_24dp).into(twitterIcon)
//            val twitterHint = twitterView.findViewById<TextView>(R.id.sm_hint)
//            twitterHint.setText(R.string.twitter)
//            val twitterValue = twitterView.findViewById<TextView>(R.id.sm_value)
//            twitterValue.text = tools.removeHttpPrefix(mTwitterText)
//            twitterValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mTwitterText)) {
//                    i.data = Uri.parse(mTwitterText)
//                } else {
//                    i.data = Uri.parse("https://twitter.com/$mTwitterText")
//                }
//                startActivity(i)
//            }
//            socialmediaInnerLayout!!.addView(twitterView)
//            deleteSocialNetworksCard = false
//        }


        // VIMEO
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mVimeoText,
            elementIcon = R.drawable.brand_vimeo_24dp,
            hintText = getString(R.string.vimeo),
            urlReplacement = "https://vimeo.com/$mVimeoText"
        )
//        if (mVimeoText != null && mVimeoText!!.length > 0) {
//            val vimeoView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            vimeoView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mVimeoText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mVimeoText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val vimeoIcon = vimeoView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_vimeo_24dp).into(vimeoIcon)
//            val vimeoHint = vimeoView.findViewById<TextView>(R.id.sm_hint)
//            vimeoHint.setText(R.string.vimeo)
//            val vimeoValue = vimeoView.findViewById<TextView>(R.id.sm_value)
//            vimeoValue.text = tools.removeHttpPrefix(mVimeoText)
//
//            vimeoValue.setOnClickListener(
//                clickToOpenWebsite(
//                    vimeoValue,
//                    Const.VIMEO_BASE_URL,
//                    mVimeoText!!
//                )
//            )
//            socialmediaInnerLayout!!.addView(vimeoView)
//            deleteSocialNetworksCard = false
//        }


        // XING
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mXingText,
            elementIcon = R.drawable.brand_xing_24dp,
            hintText = getString(R.string.xing),
            urlReplacement = "https://www.xing.com/profile/$mXingText"
        )
//        if (mXingText != null && mXingText!!.length > 0) {
//            val xingView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            xingView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mXingText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mXingText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val xingIcon = xingView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_xing_24dp).into(xingIcon)
//            val xingHint = xingView.findViewById<TextView>(R.id.sm_hint)
//            xingHint.setText(R.string.xing)
//            val xingValue = xingView.findViewById<TextView>(R.id.sm_value)
//            xingValue.text = tools.removeHttpPrefix(mXingText)
//            xingValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(mXingText)) {
//                    i.data = Uri.parse(mXingText)
//                } else {
//                    i.data = Uri.parse("https://www.xing.com/profile/$mXingText")
//                }
//                startActivity(i)
//            }
//            xingValue.setOnClickListener(
//                clickToOpenWebsite(
//                    xingValue,
//                    Const.XING_BASE_URL,
//                    mXingText!!
//                )
//            )
//            socialmediaInnerLayout!!.addView(xingView)
//            deleteSocialNetworksCard = false
//        }


        // XHAMSTER
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mXhamsterText,
            elementIcon = R.drawable.brand_xhamster_24dp,
            hintText = getString(R.string.xhamster),
            urlReplacement = "https://xhamster.com/user/$mXhamsterText"
        )
//        if (mXhamsterText != null && mXhamsterText!!.length > 0) {
//            val xhamsterView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            xhamsterView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mXhamsterText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mXhamsterText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val xhamsterIcon = xhamsterView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_xhamster_24dp).into(xhamsterIcon)
//            val xhamsterHint = xhamsterView.findViewById<TextView>(R.id.sm_hint)
//            xhamsterHint.setText(R.string.xhamster)
//            val xhamsterValue = xhamsterView.findViewById<TextView>(R.id.sm_value)
//            xhamsterValue.text = tools.removeHttpPrefix(mXhamsterText)
//            xhamsterValue.setOnClickListener(
//                clickToOpenWebsite(
//                    xhamsterValue,
//                    Const.XHAMSTER_BASE_URL,
//                    mXhamsterText!!
//                )
//            )
//            socialmediaInnerLayout!!.addView(xhamsterView)
//            deleteSocialNetworksCard = false
//        }


        // XTUBE
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mXtubeText,
            elementIcon = R.drawable.brand_xtube_24dp,
            hintText = getString(R.string.xtube),
            urlReplacement = "http://www.xtube.com/profile/$mXtubeText"
        )
//        if (mXtubeText != null && mXtubeText!!.length > 0) {
//            val xtubeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            xtubeView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mXtubeText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mXtubeText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val xtubeIcon = xtubeView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_xtube_24dp).into(xtubeIcon)
//            val xtubeHint = xtubeView.findViewById<TextView>(R.id.sm_hint)
//            xtubeHint.setText(R.string.xtube)
//            val xtubeValue = xtubeView.findViewById<TextView>(R.id.sm_value)
//            xtubeValue.text = tools.removeHttpPrefix(mXtubeText)
//            xtubeValue.setOnClickListener(
//                clickToOpenWebsite(
//                    xtubeValue,
//                    Const.XTUBE_BASE_URL,
//                    mXtubeText!!
//                )
//            )
//            socialmediaInnerLayout!!.addView(xtubeView)
//            deleteSocialNetworksCard = false
//        }

        // YOUTUBE
        generateItem(
            cardCategory = CardCategory.SOCIAL_NETWORK,
            elementText = mYoutubeText,
            elementIcon = R.drawable.brand_youtube_24dp,
            hintText = getString(R.string.youtube),
            urlReplacement = "https://www.youtube.com/user/$mYoutubeText"
        )
//        if (mYoutubeText != null && mYoutubeText!!.length > 0) {
//            val youtubeView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            youtubeView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mYoutubeText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mYoutubeText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val youtubeIcon = youtubeView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_youtube_24dp).into(youtubeIcon)
//            val youtubeHint = youtubeView.findViewById<TextView>(R.id.sm_hint)
//            youtubeHint.setText(R.string.youtube)
//            val youtubeValue = youtubeView.findViewById<TextView>(R.id.sm_value)
//            youtubeValue.text = tools.removeHttpPrefix(mYoutubeText)
//            youtubeValue.setOnClickListener(
//                clickToOpenWebsite(
//                    youtubeValue,
//                    Const.YOUTUBE_BASE_URL,
//                    mYoutubeText!!
//                )
//            )
//            socialmediaInnerLayout!!.addView(youtubeView)
//            deleteSocialNetworksCard = false
//        }


        // MESSENGERS
        // MESSENGERS
        // MESSENGERS
        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(mLineText),
            elementIcon = R.drawable.brand_line_24dp,
            hintText = getString(R.string.line_messenger),
            urlReplacement = mLineText
        )
//        if (mLineText != null && mLineText!!.length > 0) {
//            val lineView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            lineView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(mLineText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, mLineText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val lineIcon = lineView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_line_24dp).into(lineIcon)
//            val lineHint = lineView.findViewById<TextView>(R.id.sm_hint)
//            lineHint.setText(R.string.line_messenger)
//            val lineValue = lineView.findViewById<TextView>(R.id.sm_value)
//            lineValue.text = tools.removeHttpPrefix(mLineText)
//            messengersInnerLayout!!.addView(lineView)
//            deleteMessengerCard = false
//        }

        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(skypeText),
            elementIcon = R.drawable.brand_skype_24dp,
            hintText = getString(R.string.skype),
            urlReplacement = skypeText
        )
//        if (skypeText != null && skypeText!!.length > 0) {
//            val skypeView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            skypeView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(skypeText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, skypeText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val skypeIcon = skypeView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_skype_24dp).into(skypeIcon)
//            val skypeHint = skypeView.findViewById<TextView>(R.id.sm_hint)
//            skypeHint.setText(R.string.skype)
//            val skypeValue = skypeView.findViewById<TextView>(R.id.sm_value)
//            skypeValue.text = tools.removeHttpPrefix(skypeText)
//            messengersInnerLayout!!.addView(skypeView)
//            deleteMessengerCard = false
//        }

        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(snapchatText),
            elementIcon = R.drawable.brand_snapchat_24dp,
            hintText = getString(R.string.snapchat),
            urlReplacement = snapchatText
        )
//        if (snapchatText != null && snapchatText!!.length > 0) {
//            val snapchatView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            snapchatView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(snapchatText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, snapchatText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val snapchatIcon = snapchatView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_snapchat_24dp).into(snapchatIcon)
//            val snapchatHint = snapchatView.findViewById<TextView>(R.id.sm_hint)
//            snapchatHint.setText(R.string.snapchat)
//            val snapchatValue = snapchatView.findViewById<TextView>(R.id.sm_value)
//            snapchatValue.text = tools.removeHttpPrefix(snapchatText)
//            messengersInnerLayout!!.addView(snapchatView)
//            deleteMessengerCard = false
//        }

        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(viberText),
            elementIcon = R.drawable.brand_viber_24dp,
            hintText = getString(R.string.viber),
            urlReplacement = viberText
        )
//        if (viberText != null && viberText!!.length > 0) {
//            val viberView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            viberView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(viberText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, viberText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val viberIcon = viberView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_viber_24dp).into(viberIcon)
//            val viberHint = viberView.findViewById<TextView>(R.id.sm_hint)
//            viberHint.setText(R.string.viber)
//            val viberValue = viberView.findViewById<TextView>(R.id.sm_value)
//            viberValue.text = tools.removeHttpPrefix(viberText)
//            messengersInnerLayout!!.addView(viberView)
//            deleteMessengerCard = false
//        }

        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(wechatText),
            elementIcon = R.drawable.brand_wechat_24dp,
            hintText = getString(R.string.wechat),
            urlReplacement = wechatText
        )
//        if (wechatText != null && wechatText!!.length > 0) {
//            val wechatView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            wechatView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(wechatText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, wechatText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val wechatIcon = wechatView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_wechat_24dp).into(wechatIcon)
//            val wechatHint = wechatView.findViewById<TextView>(R.id.sm_hint)
//            wechatHint.setText(R.string.wechat)
//            val wechatValue = wechatView.findViewById<TextView>(R.id.sm_value)
//            wechatValue.text = tools.removeHttpPrefix(wechatText)
//            messengersInnerLayout!!.addView(wechatView)
//            deleteMessengerCard = false
//        }

        generateItem(
            cardCategory = CardCategory.MESSENGER,
            elementText = tools.removeHttpPrefix(whatsappText),
            elementIcon = R.drawable.brand_whatsapp_24dp,
            hintText = getString(R.string.whatsapp),
            urlReplacement = whatsappText
        )
//        if (whatsappText != null && whatsappText!!.length > 0) {
//            val whatsappView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            whatsappView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(whatsappText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, whatsappText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val whatsappIcon = whatsappView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_whatsapp_24dp).into(whatsappIcon)
//            val whatsappHint = whatsappView.findViewById<TextView>(R.id.sm_hint)
//            whatsappHint.setText(R.string.whatsapp)
//            val whatsappValue = whatsappView.findViewById<TextView>(R.id.sm_value)
//            whatsappValue.text = tools.removeHttpPrefix(whatsappText)
//            messengersInnerLayout!!.addView(whatsappView)
//            deleteMessengerCard = false
//        }


        // DATING APPS
        // DATING APPS
        // DATING APPS
        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(adam4adamText),
            elementIcon = R.drawable.brand_adam4adam_24dp,
            hintText = getString(R.string.adam4adam),
            urlReplacement = adam4adamText
        )
//        if (adam4adamText != null && adam4adamText!!.length > 0) {
//            val adam4adamView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            adam4adamView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(adam4adamText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, adam4adamText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val adam4adamIcon = adam4adamView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_adam4adam_24dp).into(adam4adamIcon)
//            val adam4adamHint = adam4adamView.findViewById<TextView>(R.id.sm_hint)
//            adam4adamHint.setText(R.string.adam4adam)
//            val adam4adamValue = adam4adamView.findViewById<TextView>(R.id.sm_value)
//            adam4adamValue.text = tools.removeHttpPrefix(adam4adamText)
//            adam4adamValue.setOnClickListener(
//                clickToOpenWebsite(
//                    adam4adamValue,
//                    Const.ADAM4ADAM_BASE_URL,
//                    adam4adamText!!
//                )
//            )
//            datingInnerLayout!!.addView(adam4adamView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(bbrtText),
            elementIcon = R.drawable.brand_bbrt_24dp,
            hintText = getString(R.string.bbrt),
            urlReplacement = bbrtText
        )
//        if (bbrtText != null && bbrtText!!.length > 0) {
//            val bbrtView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            bbrtView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(bbrtText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, bbrtText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val bbrtIcon = bbrtView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_bbrt_24dp).into(bbrtIcon)
//            val bbrtHint = bbrtView.findViewById<TextView>(R.id.sm_hint)
//            bbrtHint.setText(R.string.bbrt)
//            val bbrtValue = bbrtView.findViewById<TextView>(R.id.sm_value)
//            bbrtValue.text = tools.removeHttpPrefix(bbrtText)
//            datingInnerLayout!!.addView(bbrtView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(boyahoyText),
            elementIcon = R.drawable.brand_boyahoy_24dp,
            hintText = getString(R.string.boyahoy),
            urlReplacement = boyahoyText
        )
//        if (boyahoyText != null && boyahoyText!!.length > 0) {
//            val boyahoyView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            boyahoyView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(boyahoyText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, boyahoyText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val boyahoyIcon = boyahoyView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_boyahoy_24dp).into(boyahoyIcon)
//            val boyahoyHint = boyahoyView.findViewById<TextView>(R.id.sm_hint)
//            boyahoyHint.setText(R.string.boyahoy)
//            val boyahoyValue = boyahoyView.findViewById<TextView>(R.id.sm_value)
//            boyahoyValue.text = tools.removeHttpPrefix(boyahoyText)
//            datingInnerLayout!!.addView(boyahoyView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(dudesnudeText),
            elementIcon = R.drawable.brand_dudesnude_24dp,
            hintText = getString(R.string.dudesnude),
            urlReplacement = dudesnudeText
        )
//        if (dudesnudeText != null && dudesnudeText!!.length > 0) {
//            val dudesnudeView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            dudesnudeView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(dudesnudeText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, dudesnudeText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val dudesnudeIcon = dudesnudeView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_dudesnude_24dp).into(dudesnudeIcon)
//            val dudesnudeHint = dudesnudeView.findViewById<TextView>(R.id.sm_hint)
//            dudesnudeHint.setText(R.string.dudesnude)
//            val dudesnudeValue = dudesnudeView.findViewById<TextView>(R.id.sm_value)
//            dudesnudeValue.text = tools.removeHttpPrefix(dudesnudeText)
//            dudesnudeValue.setOnClickListener(
//                clickToOpenWebsite(
//                    dudesnudeValue,
//                    Const.DUDESNUDE_BASE_URL,
//                    dudesnudeText!!
//                )
//            )
//            datingInnerLayout!!.addView(dudesnudeView)
//            deleteDatingCard = false
//        }
        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(gaycomText),
            elementIcon = R.drawable.brand_gaycom_24dp,
            hintText = getString(R.string.gaycom),
            urlReplacement = gaycomText
        )
//        if (gaycomText != null && gaycomText!!.length > 0) {
//            val gayComView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            gayComView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(gaycomText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, gaycomText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val gayComIcon = gayComView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_gaycom_24dp).into(gayComIcon)
//            val gayComHint = gayComView.findViewById<TextView>(R.id.sm_hint)
//            gayComHint.setText(R.string.gaycom)
//            val gayComValue = gayComView.findViewById<TextView>(R.id.sm_value)
//            gayComValue.text = tools.removeHttpPrefix(gaycomText)
//            gayComValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(gaycomText)) {
//                    i.data = Uri.parse(gaycomText)
//                } else {
//                    i.data = Uri.parse(Const.GAYCOM_BASE_URL + gaycomText)
//                }
//                startActivity(i)
//            }
//            datingInnerLayout!!.addView(gayComView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(gaydarText),
            elementIcon = R.drawable.brand_gaydar_24dp,
            hintText = getString(R.string.gaydar),
            urlReplacement = gaydarText
        )
//        if (gaydarText != null && gaydarText!!.length > 0) {
//            val gaydarView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            gaydarView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(gaydarText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, gaydarText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val gaydarIcon = gaydarView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_gaydar_24dp).into(gaydarIcon)
//            val gaydarHint = gaydarView.findViewById<TextView>(R.id.sm_hint)
//            gaydarHint.setText(R.string.gaydar)
//            val gaydarValue = gaydarView.findViewById<TextView>(R.id.sm_value)
//            gaydarValue.text = tools.removeHttpPrefix(gaydarText)
//            gaydarValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(gaydarText)) {
//                    i.data = Uri.parse(gaydarText)
//                } else {
//                    i.data = Uri.parse(Const.GAYDAR_BASE_URL + gaydarText)
//                }
//                startActivity(i)
//            }
//            datingInnerLayout!!.addView(gaydarView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(grindrText),
            elementIcon = R.drawable.brand_grindr_24dp,
            hintText = getString(R.string.grindr),
            urlReplacement = grindrText
        )
//        if (grindrText != null && grindrText!!.length > 0) {
//            val grindrView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            grindrView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(grindrText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, grindrText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val grindrIcon = grindrView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_grindr_24dp).into(grindrIcon)
//            val grindrHint = grindrView.findViewById<TextView>(R.id.sm_hint)
//            grindrHint.setText(R.string.grindr)
//            val grindrValue = grindrView.findViewById<TextView>(R.id.sm_value)
//            grindrValue.text = tools.removeHttpPrefix(grindrText)
//            datingInnerLayout!!.addView(grindrView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(growlrText),
            elementIcon = R.drawable.brand_growlr_24dp,
            hintText = getString(R.string.growlr),
            urlReplacement = growlrText
        )
//        if (growlrText != null && growlrText!!.length > 0) {
//            val growlrView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            growlrView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(growlrText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, growlrText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val growlrIcon = growlrView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_growlr_24dp).into(growlrIcon)
//            val growlrHint = growlrView.findViewById<TextView>(R.id.sm_hint)
//            growlrHint.setText(R.string.growlr)
//            val growlrValue = growlrView.findViewById<TextView>(R.id.sm_value)
//            growlrValue.text = tools.removeHttpPrefix(growlrText)
//            datingInnerLayout!!.addView(growlrView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(hornetText),
            elementIcon = R.drawable.brand_hornet_24dp,
            hintText = getString(R.string.hornet),
            urlReplacement = hornetText
        )
//        if (hornetText != null && hornetText!!.length > 0) {
//            val hornetView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            hornetView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(hornetText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, hornetText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val hornetIcon = hornetView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_hornet_24dp).into(hornetIcon)
//            val hornetHint = hornetView.findViewById<TextView>(R.id.sm_hint)
//            hornetHint.setText(R.string.hornet)
//            val hornetValue = hornetView.findViewById<TextView>(R.id.sm_value)
//            hornetValue.text = tools.removeHttpPrefix(hornetText)
//            datingInnerLayout!!.addView(hornetView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(jackdText),
            elementIcon = R.drawable.brand_jackd_24dp,
            hintText = getString(R.string.jackd),
            urlReplacement = jackdText
        )
//        if (jackdText != null && jackdText!!.length > 0) {
//            val jackdView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            jackdView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(jackdText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, jackdText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val jackdIcon = jackdView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_jackd_24dp).into(jackdIcon)
//            val jackdHint = jackdView.findViewById<TextView>(R.id.sm_hint)
//            jackdHint.setText(R.string.jackd)
//            val jackdValue = jackdView.findViewById<TextView>(R.id.sm_value)
//            jackdValue.text = tools.removeHttpPrefix(jackdText)
//            datingInnerLayout!!.addView(jackdView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(manhuntText),
            elementIcon = R.drawable.brand_manhunt_24dp,
            hintText = getString(R.string.manhunt),
            urlReplacement = manhuntText
        )
//        if (manhuntText != null && manhuntText!!.length > 0) {
//            val manhuntView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            manhuntView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(manhuntText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, manhuntText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val manhuntIcon = manhuntView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_manhunt_24dp).into(manhuntIcon)
//            val manhuntHint = manhuntView.findViewById<TextView>(R.id.sm_hint)
//            manhuntHint.setText(R.string.manhunt)
//            val manhuntValue = manhuntView.findViewById<TextView>(R.id.sm_value)
//            manhuntValue.text = tools.removeHttpPrefix(manhuntText)
//            manhuntValue.setOnClickListener { view: View? ->
//                val i = Intent(Intent.ACTION_VIEW)
//                if (URLUtil.isValidUrl(manhuntText)) {
//                    i.data = Uri.parse(manhuntText)
//                } else {
//                    i.data = Uri.parse(Const.MANHUNT_BASE_URL + manhuntText)
//                }
//                startActivity(i)
//            }
//            datingInnerLayout!!.addView(manhuntView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(planetromeoText),
            elementIcon = R.drawable.brand_planetromeo_24dp,
            hintText = getString(R.string.planetromeo),
            urlReplacement = planetromeoText
        )
//        if (planetromeoText != null && planetromeoText!!.length > 0) {
//            val planetRomeoView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            planetRomeoView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(planetromeoText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, planetromeoText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val planetRomeoIcon = planetRomeoView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_planetromeo_24dp).into(planetRomeoIcon)
//            val planetRomeoHint = planetRomeoView.findViewById<TextView>(R.id.sm_hint)
//            planetRomeoHint.setText(R.string.planetromeo)
//            val planetRomeoValue = planetRomeoView.findViewById<TextView>(R.id.sm_value)
//            planetRomeoValue.text = tools.removeHttpPrefix(planetromeoText)
//            datingInnerLayout!!.addView(planetRomeoView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(reconText),
            elementIcon = R.drawable.brand_recon_24dp,
            hintText = getString(R.string.recon),
            urlReplacement = reconText
        )
//        if (reconText != null && reconText!!.length > 0) {
//            val reconView = inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            reconView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(reconText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, reconText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val reconIcon = reconView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_recon_24dp).into(reconIcon)
//            val reconHint = reconView.findViewById<TextView>(R.id.sm_hint)
//            reconHint.setText(R.string.recon)
//            val reconValue = reconView.findViewById<TextView>(R.id.sm_value)
//            reconValue.text = tools.removeHttpPrefix(reconText)
//            datingInnerLayout!!.addView(reconView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(scruffText),
            elementIcon = R.drawable.brand_scruff_24dp,
            hintText = getString(R.string.scruff),
            urlReplacement = scruffText
        )

//        if (scruffText != null && scruffText!!.length > 0) {
//            val scruffView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            scruffView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(scruffText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, scruffText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val scruffIcon = scruffView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_scruff_24dp).into(scruffIcon)
//            val scruffHint = scruffView.findViewById<TextView>(R.id.sm_hint)
//            scruffHint.setText(R.string.scruff)
//            val scruffValue = scruffView.findViewById<TextView>(R.id.sm_value)
//            scruffValue.text = tools.removeHttpPrefix(scruffText)
//            datingInnerLayout!!.addView(scruffView)
//            deleteDatingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.DATING,
            elementText = tools.removeHttpPrefix(tinderText),
            elementIcon = R.drawable.brand_tinder_24dp,
            hintText = getString(R.string.tinder),
            urlReplacement = tinderText
        )
//        if (tinderText != null && tinderText!!.length > 0) {
//            val tinderView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            tinderView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(tinderText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, tinderText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val tinderIcon = tinderView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_tinder_24dp).into(tinderIcon)
//            val tinderHint = tinderView.findViewById<TextView>(R.id.sm_hint)
//            tinderHint.setText(R.string.tinder)
//            val tinderValue = tinderView.findViewById<TextView>(R.id.sm_value)
//            tinderValue.text = tools.removeHttpPrefix(tinderText)
//            datingInnerLayout!!.addView(tinderView)
//            deleteDatingCard = false
//        }


        // GAMING
        // GAMING
        // GAMING
        generateItem(
            cardCategory = CardCategory.GAMING,
            elementText = tools.removeHttpPrefix(miiverseText),
            elementIcon = R.drawable.brand_miiverse_24dp,
            hintText = getString(R.string.miiverse),
            urlReplacement = miiverseText
        )
//        if (miiverseText != null && miiverseText!!.length > 0) {
//            val miiverseView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            miiverseView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(miiverseText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, miiverseText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val miiverseIcon = miiverseView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_miiverse_24dp).into(miiverseIcon)
//            val miiverseHint = miiverseView.findViewById<TextView>(R.id.sm_hint)
//            miiverseHint.setText(R.string.miiverse)
//            val miiverseValue = miiverseView.findViewById<TextView>(R.id.sm_value)
//            miiverseValue.text = tools.removeHttpPrefix(miiverseText)
//            gamingInnerLayout!!.addView(miiverseView)
//            deleteGamingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.GAMING,
            elementText = tools.removeHttpPrefix(nintendonetworkText),
            elementIcon = R.drawable.brand_nintendonetwork_24dp,
            hintText = getString(R.string.nintendonetwork),
            urlReplacement = nintendonetworkText
        )
//        if (nintendonetworkText != null && nintendonetworkText!!.length > 0) {
//            val nintendoNetworkIdView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            nintendoNetworkIdView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(nintendonetworkText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, nintendonetworkText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val nintendoNetworkIdIcon =
//                nintendoNetworkIdView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_nintendonetwork_24dp)
//                .into(nintendoNetworkIdIcon)
//            val nintendoNetworkIdHint =
//                nintendoNetworkIdView.findViewById<TextView>(R.id.sm_hint)
//            nintendoNetworkIdHint.setText(R.string.nintendonetwork)
//            val nintendoNetworkIdValue =
//                nintendoNetworkIdView.findViewById<TextView>(R.id.sm_value)
//            nintendoNetworkIdValue.text = tools.removeHttpPrefix(nintendonetworkText)
//            gamingInnerLayout!!.addView(nintendoNetworkIdView)
//            deleteGamingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.GAMING,
            elementText = tools.removeHttpPrefix(playstationnetworkText),
            elementIcon = R.drawable.brand_playstation_24dp,
            hintText = getString(R.string.playstationnetwork),
            urlReplacement = playstationnetworkText
        )
//        if (playstationnetworkText != null && playstationnetworkText!!.length > 0) {
//            val playstationNetworkIdView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            playstationNetworkIdView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(playstationnetworkText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, playstationnetworkText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val playstationNetworkIdIcon =
//                playstationNetworkIdView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_playstation_24dp)
//                .into(playstationNetworkIdIcon)
//            val playstationNetworkIdHint =
//                playstationNetworkIdView.findViewById<TextView>(R.id.sm_hint)
//            playstationNetworkIdHint.setText(R.string.playstationnetwork)
//            val playstationNetworkIdValue =
//                playstationNetworkIdView.findViewById<TextView>(R.id.sm_value)
//            playstationNetworkIdValue.text = tools.removeHttpPrefix(playstationnetworkText)
//            gamingInnerLayout!!.addView(playstationNetworkIdView)
//            deleteGamingCard = false
//        }

        generateItem(
            cardCategory = CardCategory.GAMING,
            elementText = tools.removeHttpPrefix(xboxgamertagText),
            elementIcon = R.drawable.brand_xbox_24dp,
            hintText = getString(R.string.xboxgamertag),
            urlReplacement = xboxgamertagText
        )
//        if (xboxgamertagText != null && xboxgamertagText!!.length > 0) {
//            val xboxGamertagView =
//                inflater.inflate(R.layout.social_media_item_constraint_layout, null)
//            xboxGamertagView.setOnLongClickListener { view: View? ->
//                tools.copyToClipboard(xboxgamertagText)
//                Toast.makeText(
//                    activity,
//                    getString(R.string.copied_to_clipboard, xboxgamertagText),
//                    Toast.LENGTH_SHORT
//                ).show()
//                false
//            }
//            val xboxGamertagIcon = xboxGamertagView.findViewById<ImageView>(R.id.sm_icon)
//            Glide.with(context!!).load(R.drawable.brand_xbox_24dp).into(xboxGamertagIcon)
//            val xboxGamertagHint = xboxGamertagView.findViewById<TextView>(R.id.sm_hint)
//            xboxGamertagHint.setText(R.string.xboxgamertag)
//            val xboxGamertagValue = xboxGamertagView.findViewById<TextView>(R.id.sm_value)
//            xboxGamertagValue.text = tools.removeHttpPrefix(xboxgamertagText)
//            gamingInnerLayout!!.addView(xboxGamertagView)
//            deleteGamingCard = false
//        }


        if (deleteSocialNetworksCard) {
            socialMediaCard.visibility = View.GONE
        }
        if (deleteMessengerCard) {
            messengerCard.visibility = View.GONE
        }
        if (deleteDatingCard) {
            datingCard.visibility = View.GONE
        }
        if (deleteGamingCard) {
            gamingCard.visibility = View.GONE
        }
        if (deleteSocialNetworksCard && deleteMessengerCard && deleteDatingCard && deleteGamingCard) {
            socialMediaCard.visibility = View.GONE
            messengerCard.visibility = View.GONE
            datingCard.visibility = View.GONE
            gamingCard.visibility = View.GONE
        } else {
            noInfoCard.visibility = View.GONE
//            infoLayout.visibility = View.VISIBLE
        }
    }

    // TODO
    // TODO
    // TODO
    // TODO
    private fun clickToOpenWebsite(
        textView: TextView,
        baseUrl: String,
        username: String
    ): View.OnClickListener {
        return View.OnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            if (URLUtil.isValidUrl(username)) {
                i.data = Uri.parse(username)
            } else {
                i.data = Uri.parse(baseUrl + username)
            }
            startActivity(i)
        }
    }

}