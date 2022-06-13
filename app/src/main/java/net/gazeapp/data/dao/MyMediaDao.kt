package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.MyMedia

@Dao
interface MyMediaDao {
    @Query("SELECT * FROM MyMedia WHERE id = :id")
    fun getMedia(id: Int): MyMedia

    @Query("SELECT * FROM MyMedia")
    fun getAll(): List<MyMedia>

    @Query("SELECT * FROM MyMedia where myMediaGalleryNumber = :myMediaGalleryNumber ORDER BY sortOrder ASC")
    fun getFromGallery(myMediaGalleryNumber: Int): List<MyMedia>

    @Query("SELECT * FROM MyMedia where myMediaGalleryNumber = :myMediaGalleryNumber ORDER BY :orderBy ASC")
    fun getFromGalleryOrderBy(myMediaGalleryNumber: Int, orderBy: String): List<MyMedia>

    @Insert
    fun insert(media: MyMedia): Long

    @Insert
    fun insertAll(vararg media: MyMedia): List<Long>

    @Delete
    fun delete(media: MyMedia)

    @Delete
    fun delete(vararg media: MyMedia)

    @Update
    fun update(media: MyMedia)
}