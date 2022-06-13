package net.gazeapp.ui.recent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.data.repository.ContactsRepository
import net.gazeapp.data.repository.LabelRepository
import net.gazeapp.helpers.Const

class RecentContactsViewModel constructor(app: Application) : AndroidViewModel(app) {

    private val TAG = "RecentContactsViewModel"

    private val repository: ContactsRepository = ContactsRepository(GazeDatabase.getDatabase(app))
    private val labelRepo: LabelRepository = LabelRepository(GazeDatabase.getDatabase(app))

    /**
     * A list of recent contacts that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _recentContacts = MutableLiveData<List<Contact>>()

    /**
     * A list of recent contacts that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val recentContacts: LiveData<List<Contact>>
        get() = _recentContacts


    private val _recentContactsWithDetails = MutableLiveData<List<ContactWithDetails>>()
    val recentContactsWithDetails: LiveData<List<ContactWithDetails>>
        get() = _recentContactsWithDetails

    /**
     * Flag to display the error message. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private var _hasNoEntries = MutableLiveData(false)

    /**
     * Event triggered for network error. Views should use this to get access
     * to the data.
     */
    val hasNoEntries: LiveData<Boolean>
        get() = _hasNoEntries

    init {
        getRecentContacts()
    }

    fun getRecentContacts() {
        viewModelScope.launch {

            repository.getRecentContacts(Const.RECENT_CONTACTS_LIMIT)
            _recentContacts.postValue(repository.recentContactsList)

            repository.getRecentFullContacts(Const.RECENT_CONTACTS_LIMIT)
            _recentContactsWithDetails.postValue(repository.recentContactWithDetailsList)

            // If there are no recent contacts inform the UI accordingly
            _hasNoEntries.value = repository.recentContactsList.isNullOrEmpty()
        }

    }

    fun copyFirstContactImageToInternalStorage() {
    }

//    fun insertLabel() {
//        viewModelScope.launch {
//            labelRepo.insertLabel(
//                Label(
//                    0,
//                    "THIS IS A TEST",
//                    Date(),
//                    Date(),
//                    false
//                )
//            )
//
//            labelRepo.insertLabels()
//        }
//    }

}