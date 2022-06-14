package net.gazeapp.ui.contactview

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import net.gazeapp.utilities.SpecificValues

class ContactViewNavigationAdapter(
    fragmentActivity: FragmentActivity,
    private val tabTitles: List<String>
) : FragmentStateAdapter(fragmentActivity) {
    private var mScrollY = 0
    private var mTabTitles: List<String>? = null
    private var mPosition = 0
    private val ARG_SCROLL_Y = "ARG_SCROLL_Y"

    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null

        when (position) {
            0 -> {
                // OVERVIEW VIEW
                fragment = ContactViewOverviewFragment()
            }
            1 -> {
                // GALLERY VIEW
                fragment = ContactViewGalleryFragment()
            }
            2 -> {
                // CONTACT (EMAIL, PHONE, ADDRESSES)
                fragment = ContactViewContactFragment()
            }
            3 -> {
                // PERSONAL
                fragment = ContactViewPersonalFragment()
            }
            4 -> {
                // WORK
                fragment = ContactViewWorkFragment()
            }
            5 -> {
                // BODY VIEW
                fragment = ContactViewBodyFragment()
            }
        }
        if (SpecificValues.SHOW_XRATED) {
            when (position) {
                6 -> {
                    // XXX
                    fragment = ContactViewXxxStuffFragment()
                }
                7 -> {
                    // ENCOUNTERS
                    fragment = ContactViewEncountersFragment()
                }
                8 -> {
                    // SOCIAL MEDIA
                    fragment = ContactViewSocialMediaFragment()
                }
            }
        } else {
            if (position == 6) {
                // ENCOUNTERS
                fragment = ContactViewEncountersFragment()
            } else if (position == 7) {
                // SOCIAL MEDIA
                fragment = ContactViewSocialMediaFragment()
            }
        }
        val args = Bundle()
        //            args.putInt(RecentContactsTabRecyclerViewFragment.ARG_INITIAL_POSITION, 1);
//            analyticsBundle.putString(FirebaseAnalytics.Param.DESTINATION, "Recent Contacts Fragment");
        fragment!!.arguments = args
        return fragment
    }

    override fun getItemCount(): Int {
        return tabTitles.size
    }

    fun setScrollY(scrollY: Int) {
        mScrollY = scrollY
    }

    private fun setArguments(scrollY: Int) {
        if (0 <= scrollY) {
            val args = Bundle()
            args.putInt(ARG_SCROLL_Y, scrollY)
            setArguments(scrollY)
        }
    }
}