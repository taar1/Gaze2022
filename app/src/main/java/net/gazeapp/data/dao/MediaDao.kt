package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Media

@Dao
interface MediaDao {
    @Query("SELECT * FROM Media")
    fun getAll(): List<Media>

    /**
     * @param contactId
     * @param isInPrivateGallery (true = private gallery, false = public gallery)
     * @return
     */
    @Query("SELECT * FROM Media where contact_id = :contactId AND " + Media.IS_IN_PRIVATE_GALLERY + " = :isInPrivateGallery")
    fun getMedia(contactId: Int, isInPrivateGallery: Boolean): List<Media>

    @Query("SELECT * FROM Media where contact_id = :contactId AND " + Media.originalFilename + " IN(:fileList) AND " + Media.IS_IN_PRIVATE_GALLERY + " = :isInPrivateGallery")
    fun getMedia(
        contactId: Int, fileList: List<String>, isInPrivateGallery: Boolean
    ): List<Media>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: Media): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg media: Media): List<Long>

    @Delete
    fun delete(media: Media)

    @Delete
    fun delete(medias: List<Media>)

    @Query("DELETE FROM Media where contact_id = :contactId")
    fun deleteMediasByContactId(contactId: Int)

    @Update
    fun update(media: Media)
}