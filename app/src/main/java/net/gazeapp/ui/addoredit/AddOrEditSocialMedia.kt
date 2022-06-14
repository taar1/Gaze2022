package net.gazeapp.ui.addoredit

import androidx.core.view.isVisible
import net.gazeapp.data.dto.SocialMediaDto

class AddOrEditSocialMedia(
    val f: AddOrEditContactFragment
) {
    fun displaySocialMedia(socialMediaDto: SocialMediaDto) {
        // TODO input listeners
        with(f) {
            facebook.setText(socialMediaDto.facebook)
            flickr.setText(socialMediaDto.flickr)
            instagram.setText(socialMediaDto.instagram)
            linkedin.setText(socialMediaDto.linkedIn)
            pinterest.setText(socialMediaDto.pinterest)
            pornhub.setText(socialMediaDto.pornhub)
            redtube.setText(socialMediaDto.redtube)
            spotify.setText(socialMediaDto.spotify)
            tumblr.setText(socialMediaDto.tumblr)
            twitter.setText(socialMediaDto.twitter)
            vimeo.setText(socialMediaDto.vimeo)
            xing.setText(socialMediaDto.xing)
            xtube.setText(socialMediaDto.xtube)
            youtube.setText(socialMediaDto.youtube)
            lineMessenger.setText(socialMediaDto.lineMessenger)
            skype.setText(socialMediaDto.skype)
            snapchat.setText(socialMediaDto.snapChat)
            viber.setText(socialMediaDto.viber)
            weChat.setText(socialMediaDto.weChat)
            whatsapp.setText(socialMediaDto.whatsapp)
            adam4adam.setText(socialMediaDto.adam4adam)
            bbrt.setText(socialMediaDto.bbrt)
            dudesnude.setText(socialMediaDto.dudesnude)
            gaycom.setText(socialMediaDto.gaycom)
            gaydar.setText(socialMediaDto.gaydar)
            grindr.setText(socialMediaDto.grindr)
            growlr.setText(socialMediaDto.growlr)
            hornet.setText(socialMediaDto.hornet)
            jackd.setText(socialMediaDto.jackd)
            manhunt.setText(socialMediaDto.manhunt)
            planetRomeo.setText(socialMediaDto.planetRomeo)
            recon.setText(socialMediaDto.recon)
            scruff.setText(socialMediaDto.scruff)
            tinder.setText(socialMediaDto.tinder)
            miiverse.setText(socialMediaDto.miiverse)
            nintendonetwork.setText(socialMediaDto.nintendoNetwork)
            playstationnetwork.setText(socialMediaDto.playstationNetwork)
            xboxgamertag.setText(socialMediaDto.xboxGamertag)

            deleteFacebook.isVisible = !socialMediaDto.facebook.isNullOrEmpty()
            deleteFlickr.isVisible = !socialMediaDto.flickr.isNullOrEmpty()
            deleteInstagram.isVisible = !socialMediaDto.instagram.isNullOrEmpty()
            deleteLinkedin.isVisible = !socialMediaDto.linkedIn.isNullOrEmpty()
            deletePinterest.isVisible = !socialMediaDto.pinterest.isNullOrEmpty()
            deletePornhub.isVisible = !socialMediaDto.pornhub.isNullOrEmpty()
            deleteRedtube.isVisible = !socialMediaDto.redtube.isNullOrEmpty()
            deleteSpotify.isVisible = !socialMediaDto.spotify.isNullOrEmpty()
            deleteTumblr.isVisible = !socialMediaDto.tumblr.isNullOrEmpty()
            deleteTwitter.isVisible = !socialMediaDto.twitter.isNullOrEmpty()
            deleteVimeo.isVisible = !socialMediaDto.vimeo.isNullOrEmpty()
            deleteXing.isVisible = !socialMediaDto.xing.isNullOrEmpty()
            deleteXtube.isVisible = !socialMediaDto.xtube.isNullOrEmpty()
            deleteYoutube.isVisible = !socialMediaDto.youtube.isNullOrEmpty()
            deleteLineMessenger.isVisible = !socialMediaDto.lineMessenger.isNullOrEmpty()
            deleteSkype.isVisible = !socialMediaDto.skype.isNullOrEmpty()
            deleteSnapchat.isVisible = !socialMediaDto.snapChat.isNullOrEmpty()
            deleteViber.isVisible = !socialMediaDto.viber.isNullOrEmpty()
            deleteWeChat.isVisible = !socialMediaDto.weChat.isNullOrEmpty()
            deleteWhatsapp.isVisible = !socialMediaDto.whatsapp.isNullOrEmpty()
            deleteAdam4adam.isVisible = !socialMediaDto.adam4adam.isNullOrEmpty()
            deleteBbrt.isVisible = !socialMediaDto.bbrt.isNullOrEmpty()
            deleteDudesnude.isVisible = !socialMediaDto.dudesnude.isNullOrEmpty()
            deleteGaycom.isVisible = !socialMediaDto.gaycom.isNullOrEmpty()
            deleteGaydar.isVisible = !socialMediaDto.gaydar.isNullOrEmpty()
            deleteGrindr.isVisible = !socialMediaDto.grindr.isNullOrEmpty()
            deleteGrowlr.isVisible = !socialMediaDto.growlr.isNullOrEmpty()
            deleteHornet.isVisible = !socialMediaDto.hornet.isNullOrEmpty()
            deleteJackd.isVisible = !socialMediaDto.jackd.isNullOrEmpty()
            deleteManhunt.isVisible = !socialMediaDto.manhunt.isNullOrEmpty()
            deletePlanetRomeo.isVisible = !socialMediaDto.planetRomeo.isNullOrEmpty()
            deleteRecon.isVisible = !socialMediaDto.recon.isNullOrEmpty()
            deleteScruff.isVisible = !socialMediaDto.scruff.isNullOrEmpty()
            deleteTinder.isVisible = !socialMediaDto.tinder.isNullOrEmpty()
            deleteMiiverse.isVisible = !socialMediaDto.miiverse.isNullOrEmpty()
            deleteNintendonetwork.isVisible = !socialMediaDto.nintendoNetwork.isNullOrEmpty()
            deletePlaystationnetwork.isVisible = !socialMediaDto.playstationNetwork.isNullOrEmpty()
            deleteXboxgamertag.isVisible = !socialMediaDto.xboxGamertag.isNullOrEmpty()
        }
    }
}