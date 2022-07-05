package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.GazeDatabase
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.ContactWithDetails

class ContactsRepository(val database: GazeDatabase) {

    companion object {
        private const val TAG = "ContactsRepository"
    }

    lateinit var contactWithDetails: ContactWithDetails
    var allContactsList: List<Contact> = listOf()
    var recentContactsList: List<Contact> = listOf()
    var recentContactWithDetailsList: List<ContactWithDetails> = listOf()
    var allContactWithDetailsList: List<ContactWithDetails> = listOf()

    // TODO FIXME wie macht man das am besten?
    var insertedContactId: Long = 0

    // TODO FIXME create contactKtDao -> getContactWithDetails....
    // TODO FIXME create contactKtDao -> getContactWithDetails....
    // TODO FIXME create contactKtDao -> getContactWithDetails....
    suspend fun getContactWithDetails(contactId: Int) {
        withContext(Dispatchers.IO) {
            contactWithDetails =
                database.contactKtDao.getContactWithDetails(contactId)
        }
    }

    suspend fun getRecentContacts(limit: Long) {
        withContext(Dispatchers.IO) {
            recentContactsList =
                database.contactKtDao.getRecentContacts(limit)
        }
    }

    suspend fun getRecentFullContacts(limit: Long) {
        withContext(Dispatchers.IO) {
            recentContactWithDetailsList =
                database.contactKtDao.getRecentContactsWithDetails(limit)
        }
    }

    suspend fun getAllContacts() {
        withContext(Dispatchers.IO) {
            allContactsList =
                database.contactKtDao.getAll()
        }
    }

    suspend fun getAllFullContacts() {
        withContext(Dispatchers.IO) {
            allContactWithDetailsList =
                database.contactKtDao.getAllContactsWithDetails()
        }
    }

    suspend fun insertContact(contact: Contact): Long {
        withContext(Dispatchers.IO) {
            insertedContactId = database.contactKtDao.insert(contact)
        }
        return insertedContactId
    }


    suspend fun updateContact(contact: Contact) {
        withContext(Dispatchers.IO) {
            database.contactKtDao.update(contact)
        }
    }
}