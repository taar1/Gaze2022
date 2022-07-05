package net.gazeapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
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
import net.gazeapp.data.dao.ContactLabelDao
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.Label
import net.gazeapp.databinding.ActivitySearchableBinding
import net.gazeapp.helpers.Const
import net.gazeapp.ui.widgets.StylizedLabel
import net.gazeapp.utilities.GazeTools
import javax.inject.Inject

@AndroidEntryPoint
class ListLabelledContactsActivity : AppCompatActivity() {

    @Inject
    lateinit var tools: GazeTools

    lateinit var progressBar: ProgressBar
    lateinit var noContactsLayout: RelativeLayout
    lateinit var recyclerView: RecyclerView
    lateinit var facebookBanner: LinearLayout
    lateinit var adView: AdView
    lateinit var toolbar: Toolbar
    lateinit var flowLayout: net.gazeapp.ui.widgets.FlowLayout

    lateinit var binding: ActivitySearchableBinding

    private var actionBar: ActionBar? = null

    var searchResultsRecyclerListAdapter: SearchResultsRecyclerListAdapter? = null
    private var labelIds: ArrayList<Int>? = null
    var contactList: List<Contact>? = ArrayList()
    var labelList: List<Label>? = ArrayList()
    private var contactLabelDao: ContactLabelDao? = null

    companion object {
        private const val TAG = "ListLabelledContactsAct"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchableBinding.inflate(layoutInflater)

        setContentView(binding.root)

        bindViews()


        contactLabelDao = getDatabase(this).contactLabelDao
        labelIds = intent.getParcelableExtra("labelIds")

        if (labelIds.isNullOrEmpty()) {
            Toast.makeText(this, R.string.no_label_selected, Toast.LENGTH_SHORT).show()
            finish()
        }

        setSupportActionBar(toolbar)
        actionBar = supportActionBar
        if (actionBar != null) {
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar!!.setTitle(R.string.contacts_with_labels)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar.visibility = View.VISIBLE
        contactsWithLabels

        initFacebookAds()
        displayAds()
    }

    private fun bindViews() {
        progressBar = binding.inclContentSearchable.progressBar
        noContactsLayout = binding.inclContentSearchable.noContactsLayout
        recyclerView = binding.inclContentSearchable.contactsRecyclerView
        facebookBanner = binding.inclAdview.bannerContainer
        toolbar = binding.inclContentSearchable.toolbar
        flowLayout = binding.inclContentSearchable.flowLayout
    }

    private val contactsWithLabels: Unit
        get() {
            Log.d(TAG, "getContactsWithLabels()")
            Needle.onBackgroundThread().execute {
                val contactIds: MutableList<Int> = ArrayList()
                contactList = contactLabelDao?.getContactsWithLabelIds(labelIds!!)
                for ((id) in contactList!!) {
                    contactIds.add(id)
                }
                labelList = contactLabelDao?.getLabelsWithContactIds(contactIds)
                fillView()
            }
        }

    private fun displayAds() {
        if (tools.isProUser()) {
            title = getString(R.string.gaze_pro)

            // If Pro user wants ads, show them now.
            if (tools.isAdsEnabled) {
                adView.loadAd()
                facebookBanner.visibility = View.VISIBLE
            } else {
                facebookBanner.visibility = View.GONE
            }
        } else {
            // Free users get ads
            adView.loadAd()
            facebookBanner.visibility = View.VISIBLE
        }
    }

    private fun fillView() {
        progressBar.visibility = View.GONE

        // TODO FIXME labels werden noch mehrfach angezeigt...

        // LABELS
        labelList?.let {
            for (labelItem in it) {
                val stylizedLabel = StylizedLabel(this@ListLabelledContactsActivity)
                stylizedLabel.setTitle(labelItem.label)

                stylizedLabel.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                stylizedLabel.setOnClickListener { v: View? ->
                    val labelList = ArrayList<Int>()
                    labelList.add(labelItem.id)

                    // TODO FIXME anstatt neue activity laden die liste refreshen mit dem neuen parameter
                    val intent = Intent(
                        this@ListLabelledContactsActivity,
                        ListLabelledContactsActivity::class.java
                    )
                    intent.putExtra("labelIds", labelList)
                    startActivity(intent)
                }
                flowLayout.addView(stylizedLabel)
            }
        }


        if (contactList.isNullOrEmpty()) {
            searchResultsRecyclerListAdapter =
                SearchResultsRecyclerListAdapter(this@ListLabelledContactsActivity, contactList)
            recyclerView.adapter = searchResultsRecyclerListAdapter
        } else {
            noContactsLayout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
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

    private fun initFacebookAds() {
        adView =
            AdView(this, Const.FACEBOOK_AUDIENCE_NETWORK_NATIVE_BANNER_ID, AdSize.BANNER_HEIGHT_50)
        facebookBanner.addView(adView)
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}