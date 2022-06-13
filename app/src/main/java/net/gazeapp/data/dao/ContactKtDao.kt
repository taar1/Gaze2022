package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.*

@Dao
interface ContactKtDao {
    @Query("SELECT * FROM Contact WHERE isMe = 0 ORDER BY " + Contact.CONTACT_NAME + " ASC")
    suspend fun getAll(): List<Contact>

    @Query("SELECT * FROM Contact WHERE id = :contactId LIMIT 1")
    fun getContact(contactId: Int): Contact

    @Query("SELECT * FROM Contact WHERE id = :contactId LIMIT 1")
    fun getContactWithDetails(contactId: Int): ContactWithDetails

    @Query("SELECT * FROM Contact WHERE isMe = 1 LIMIT 1")
    fun getMyself(): Contact

    // Maybe not needed anymore since we have getRecentContactsWithDetails()
    @Query("SELECT * FROM Contact WHERE isMe = 0 ORDER BY " + Contact.LAST_VIEWED + " LIMIT :limit")
    fun getRecentContacts(limit: Long): List<Contact>

    @Transaction
    @Query("SELECT * FROM Contact WHERE isMe = 0 ORDER BY " + Contact.LAST_VIEWED + " LIMIT :limit")
    fun getRecentContactsWithDetails(limit: Long): List<ContactWithDetails>

    @Transaction
    @Query("SELECT * FROM Contact WHERE Contact.isMe = 0 ORDER BY Contact." + Contact.CONTACT_NAME)
    fun getAllContactsWithDetails(): List<ContactWithDetails>

    @Insert
    suspend fun insert(contact: Contact): Long

//    @Insert
//    suspend fun insertContactWithDetails(contactWithDetails: ContactWithDetails): Long

    @Delete
    suspend fun delete(contact: Contact)

//    @Delete
//    suspend fun delete(vararg contact: Contact)

    @Query("DELETE FROM Contact WHERE id = :contactId")
    suspend fun deleteContacts(contactId: String)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT COUNT(ID) FROM Contact")
    suspend fun countContacts(): Int

    /**
     * Do a full text search.
     *
     * @param query Parameter MUST be surrounded by %XXX% in order for the LIKE clause to work.
     * @return
     */
    @Transaction
    @Query(
        "SELECT * FROM Contact AS c " +
                "LEFT JOIN Nickname AS n ON n.contact_id = c.id " +
                "LEFT JOIN Address AS a ON a.contact_id = c.id " +
                "LEFT JOIN Email AS e ON e.contact_id = c.id " +
                "LEFT JOIN Phone AS p ON p.contact_id = c.id " +
                "LEFT JOIN Website AS we ON we.contact_id = c.id " +
                "LEFT JOIN Work AS wo ON wo.contact_id = c.id " +
                "WHERE c." + Contact.CONTACT_NAME + " LIKE :query " +
                "OR c." + Contact.ADDITIONAL_INFO + " LIKE :query " +
                "OR n." + Nickname.NICKNAME + " LIKE :query " +
                "OR a." + Address.ADDRESS + " LIKE :query " +
                "OR e." + Email.EMAIL + " LIKE :query " +
                "OR p." + Phone.PHONE_NUMBER + " LIKE :query " +
                "OR we." + Website.WEBSITE + " LIKE :query " +
                "OR wo." + Work.EMPLOYER + " LIKE :query " +
                "OR wo." + Work.PROFESSION + " LIKE :query " +
                "OR wo." + Work.EMPLOYER_ADDRESS + " LIKE :query " +
                "OR wo." + Work.PHONE + " LIKE :query " +
                "OR wo." + Work.EMAIL + " LIKE :query " +
                "ORDER BY c." + Contact.CONTACT_NAME + ", " +
                "c." + Contact.ADDITIONAL_INFO + ", " +
                "c." + Contact.CONTACT_NAME + " COLLATE NOCASE"
    )
    suspend fun searchContacts(query: String): List<Contact>
}