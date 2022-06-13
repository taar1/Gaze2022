package net.gazeapp.ui.contactview

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.gazeapp.utilities.BuildSpecificValues

class ViewPagerAdapter(fa: FragmentActivity, val contactId: Int) :
    FragmentStateAdapter(fa) {

    companion object {
        private const val TAG = "ViewPagerAdapter"
    }

    lateinit var tabTitles: List<String>

    override fun createFragment(position: Int): Fragment {
        // val customFragmentFactory = ContactDetailFragmentFactory(contactId)

        Log.d(TAG, "YYYYY createFragment: $position")
        if (BuildSpecificValues.SHOW_XRATED) {
            return when (position) {
                0 -> TabOverviewFragmentKt.newInstance(contactId)
                1 -> TabGalleryFragmentKt.newInstance(contactId)
                2 -> TabContactFragmentKt.newInstance(contactId)
                3 -> TabPersonalFragmentKt.newInstance(contactId)
                4 -> TabWorkFragmentKt.newInstance(contactId)
                5 -> TabBodyFragmentKt.newInstance(contactId)
                6 -> TabXxxStuffFragmentKt.newInstance(contactId)
                7 -> TabEncountersFragmentKt.newInstance(contactId)
                8 -> TabSocialMediaFragmentKt.newInstance(contactId)
                else -> TabOverviewFragmentKt.newInstance(contactId)
            }
        } else {
            return when (position) {
                0 -> TabOverviewFragmentKt.newInstance(contactId)
                1 -> TabGalleryFragmentKt.newInstance(contactId)
                2 -> TabContactFragmentKt.newInstance(contactId)
                3 -> TabPersonalFragmentKt.newInstance(contactId)
                4 -> TabWorkFragmentKt.newInstance(contactId)
                5 -> TabBodyFragmentKt.newInstance(contactId)
                6 -> TabEncountersFragmentKt.newInstance(contactId)
                7 -> TabSocialMediaFragmentKt.newInstance(contactId)
                else -> TabOverviewFragmentKt.newInstance(contactId)
            }
        }
    }

    override fun getItemCount(): Int {
        return tabTitles.size
    }

}