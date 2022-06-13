package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.SocialMedia

@Dao
interface SocialMediaDao {
    @Query("SELECT * FROM SocialMedia where contact_id = :contactId LIMIT 1")
    fun getSocialMedia(contactId: Int): SocialMedia

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(socialMedia: SocialMedia): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg socialMediaEntities: SocialMedia): List<Long>

    @Delete
    fun delete(socialMedia: SocialMedia)

    @Delete
    fun delete(vararg socialMedia: SocialMedia)

    @Update
    fun update(socialMedia: SocialMedia)
}