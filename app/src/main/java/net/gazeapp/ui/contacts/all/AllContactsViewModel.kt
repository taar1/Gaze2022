package net.gazeapp.ui.contacts.all

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
import net.gazeapp.helpers.Const

class AllContactsViewModel constructor(app: Application) : AndroidViewModel(app) {

    private val TAG = "AllContactsViewModel"

    private val repository: ContactsRepository = ContactsRepository(GazeDatabase.getDatabase(app))
    //private val labelRepo: LabelRepository = LabelRepository(GazeDatabase.getDatabase(app))

    /**
     * A list of all contacts that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _allContacts = MutableLiveData<List<Contact>>()

    /**
     * A list of all contacts that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val allContacts: LiveData<List<Contact>>
        get() = _allContacts


    private val _allFullContacts = MutableLiveData<List<ContactWithDetails>>()
    val allContactsWithDetails: LiveData<List<ContactWithDetails>>
        get() = _allFullContacts

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
        getAllContacts()
    }

    fun getAllContacts() {
        viewModelScope.launch {

            repository.getAllContacts()
            _allContacts.postValue(repository.recentContactsList)

            repository.getRecentFullContacts(Const.RECENT_CONTACTS_LIMIT)
            _allFullContacts.postValue(repository.recentContactWithDetailsList)

            // If there are no contacts inform the UI accordingly
            _hasNoEntries.value = repository.allContactsList.isNullOrEmpty()
        }
    }

}