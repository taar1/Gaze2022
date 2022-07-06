package net.gazeapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.gazeapp.data.dao.ContactKtDao
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.ContactWithDetails

class ContactsRepository(private val contactKtDao: ContactKtDao) {

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

    suspend fun getContactWithDetails(contactId: Int) {
        withContext(Dispatchers.IO) {
            contactWithDetails =
                contactKtDao.getContactWithDetails(contactId)
        }
    }

    suspend fun getRecentContacts(limit: Long) {
        withContext(Dispatchers.IO) {
            recentContactsList =
                contactKtDao.getRecentContacts(limit)
        }
    }

    suspend fun getRecentFullContacts(limit: Long) {
        withContext(Dispatchers.IO) {
            recentContactWithDetailsList =
                contactKtDao.getRecentContactsWithDetails(limit)
        }
    }

    suspend fun getAllContacts() {
        withContext(Dispatchers.IO) {
            allContactsList =
                contactKtDao.getAll()
        }
    }

    suspend fun getAllFullContacts() {
        withContext(Dispatchers.IO) {
            allContactWithDetailsList =
                contactKtDao.getAllContactsWithDetails()
        }
    }

    suspend fun insertContact(contact: Contact): Long {
        withContext(Dispatchers.IO) {
            insertedContactId = contactKtDao.insert(contact)
        }
        return insertedContactId
    }


    suspend fun updateContact(contact: Contact) {
        withContext(Dispatchers.IO) {
            contactKtDao.update(contact)
        }
    }
}