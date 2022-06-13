package net.gazeapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.gazeapp.data.model.Contact
import net.gazeapp.data.model.ContactLabelCrossRef
import net.gazeapp.data.model.Label
import net.gazeapp.data.model.PersistentObject

@Dao
interface ContactLabelDao {
    @Query("SELECT * FROM Contact INNER JOIN ContactLabelCrossRef ON Contact.ID = ContactLabelCrossRef.contact_id WHERE ContactLabelCrossRef.label_id =:labelId")
    fun getContactsWithLabelId(labelId: Int): List<Contact>

    @Query("SELECT * FROM Contact INNER JOIN ContactLabelCrossRef ON Contact.ID = ContactLabelCrossRef.contact_id WHERE ContactLabelCrossRef.label_id IN(:labelIds)")
    fun getContactsWithLabelIds(labelIds: List<Int>): List<Contact>

    @Query("SELECT * FROM Label INNER JOIN ContactLabelCrossRef ON Label.ID = ContactLabelCrossRef.label_id WHERE ContactLabelCrossRef.contact_id =:contactId")
    fun getLabelsWithContactId(contactId: Int): List<Label>

    @Query("SELECT * FROM Label INNER JOIN ContactLabelCrossRef ON Label.ID = ContactLabelCrossRef.label_id WHERE ContactLabelCrossRef.contact_id IN(:contactIds)")
    fun getLabelsWithContactIds(contactIds: List<Int>): List<Label>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contactLabelCrossRef: ContactLabelCrossRef): Long

    @Query("INSERT INTO ContactLabelCrossRef (" + PersistentObject.CONTACT_ID_FIELD_NAME + ", " + PersistentObject.LABEL_ID_FIELD_NAME + ") VALUES (:contactId, :labelId)")
    fun insertContactLabelConnection(contactId: Int, labelId: Int): Long

    @Query("DELETE FROM ContactLabelCrossRef WHERE " + PersistentObject.CONTACT_ID_FIELD_NAME + " = :contactId")
    fun deleteLabelsFromContact(contactId: Int)

    @Query("DELETE FROM ContactLabelCrossRef WHERE " + PersistentObject.LABEL_ID_FIELD_NAME + " = :labelId")
    fun deleteLabelFromContact(labelId: Int)
}