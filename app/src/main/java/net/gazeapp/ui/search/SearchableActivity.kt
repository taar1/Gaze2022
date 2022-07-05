package net.gazeapp.ui.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.ads.AdSize
import com.facebook.ads.AdView
import dagger.hilt.android.AndroidEntryPoint
import needle.Needle
import net.gazeapp.R
import net.gazeapp.data.GazeDatabase.Companion.getDatabase
import net.gazeapp.data.dao.ContactDao
import net.gazeapp.data.model.Contact
import net.gazeapp.databinding.ActivitySearchableBinding
import net.gazeapp.helpers.Const
import net.gazeapp.utilities.GazeTools
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class SearchableActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null

    @Inject
    lateinit var tools: GazeTools

    lateinit var progressBar: ProgressBar
    lateinit var noContactsLayout: RelativeLayout
    lateinit var recyclerView: RecyclerView
    lateinit var facebookBanner: LinearLayout
    lateinit var toolbar: Toolbar
    lateinit var labelFilterLayout: LinearLayout

    var adView: AdView? = null

    var searchResultsRecyclerListAdapter: SearchResultsRecyclerListAdapter? = null
    var contactList: List<Contact>? = null
    private var contactDao: ContactDao? = null

    lateinit var binding: ActivitySearchableBinding

    companion object {
        private const val TAG = "SearchableActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchableBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bindViews()

        contactDao = getDatabase(this).contactDao

        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar.visibility = View.VISIBLE
        labelFilterLayout.visibility = View.GONE

        handleIntent(intent)
        initFacebookAds()
        displayAds()
    }

    private fun bindViews() {
        progressBar = binding.inclContentSearchable.progressBar
        noContactsLayout = binding.inclContentSearchable.noContactsLayout
        recyclerView = binding.inclContentSearchable.contactsRecyclerView
        facebookBanner = binding.inclAdview.bannerContainer
        toolbar = binding.inclContentSearchable.toolbar
        labelFilterLayout = binding.inclContentSearchable.labelFilterLayout
    }

    private fun initFacebookAds() {
        adView =
            AdView(this, Const.FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID, AdSize.BANNER_HEIGHT_50)
        facebookBanner.addView(adView)
    }

    private fun displayAds() {
        if (tools.isProUser()) {
            title = getString(R.string.gaze_pro)

            // If Pro user wants ads, show them now.
            if (tools.isAdsEnabled) {
                adView?.loadAd()
                adView?.visibility = View.VISIBLE
            } else {
                adView?.setBackgroundColor(resources.getColor(R.color.red))
                adView?.visibility = View.GONE
            }
        } else {
            // Free users get ads
            adView?.loadAd()
            adView?.visibility = View.VISIBLE
        }
    }

    fun fillView() {
        Needle.onMainThread().execute {
            progressBar.visibility = View.GONE

            if (contactList.isNullOrEmpty()) {
                searchResultsRecyclerListAdapter =
                    SearchResultsRecyclerListAdapter(this@SearchableActivity, contactList)
                recyclerView.adapter = searchResultsRecyclerListAdapter
            } else {
                noContactsLayout.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            val suggestions = SearchRecentSuggestions(
                this,
                SearchSuggestionProvider.AUTHORITY,
                SearchSuggestionProvider.MODE
            )
            suggestions.saveRecentQuery(query, null)
            actionBar?.title = getString(
                R.string.search_results_for,
                query!!.uppercase(Locale.ENGLISH).trim { it <= ' ' })
            doMySearch(query)
        }
    }

    private fun doMySearch(query: String?) {
        Log.d(TAG, "SEARCH QUERY: $query")
        contactList = contactDao?.searchContacts(query)

        contactList?.let {
            for (contact in it) {
                Log.d(TAG, "FOUND CONTACT: $contact")
            }
        }
        fillView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        adView?.destroy()
    }
}