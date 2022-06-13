package net.gazeapp.ui.contactview

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.ContactWithDetails
import net.gazeapp.data.model.Media
import net.gazeapp.data.repository.ContactsRepository
import net.gazeapp.data.repository.MediaRepository

class ContactViewModelFactory(val contactId: Int, val app: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(contactId, app) as T
    }

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//        ContactViewModel(contactId, app) as T
}

class ContactViewModel constructor(val contactId: Int, val app: Application) :
    AndroidViewModel(app) {

    private val TAG = "ContactViewModel"

    private val repository: ContactsRepository = ContactsRepository(GazeDatabase.getDatabase(app))
    private val mediaRepo: MediaRepository = MediaRepository(GazeDatabase.getDatabase(app))

    private val _contactWithDetails = MutableLiveData<ContactWithDetails>()
    val contactWithDetails: LiveData<ContactWithDetails>
        get() = _contactWithDetails

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

    private val _mediaList = MutableLiveData<List<Media>>()
    val mediaList: LiveData<List<Media>>
        get() = _mediaList

    private var _hasMediaFiles = MutableLiveData(false)
    val hasMediaFiles: LiveData<Boolean>
        get() = _hasMediaFiles


    init {
        _hasEntries.value = true
        _hasMediaFiles.value = true
        getContactWithDetails()
        getMedia()
    }

    fun getContactWithDetails() {
        viewModelScope.launch {
            repository.getContactWithDetails(contactId)
            _contactWithDetails.postValue(repository.contactWithDetails)

            // If there are no recent contacts inform the UI accordingly
            _hasEntries.value = !repository.recentContactsList.isNullOrEmpty()
        }

    }

    fun getMedia() {
        viewModelScope.launch {
            Log.d(TAG, "XXXXX2 getMedia: Contact ID: $contactId")

            mediaRepo.getMedia(contactId, false)
            _mediaList.postValue(mediaRepo.mediaList)

            Log.d(TAG, "XXXXX2 getMedia: mediaRepo.mediaList: ${mediaRepo.mediaList.size}")

            // If there contact media inform the UI accordingly
            _hasMediaFiles.value = !mediaRepo.mediaList.isNullOrEmpty()
        }
    }

}