package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.MyPreferences

@Dao
interface MyPreferencesDao {
    @Query("SELECT * FROM MyPreferences where ID = :id")
    fun getMyPreferences(id: Int): List<MyPreferences>

    @Insert
    fun insert(myPreferences: MyPreferences): Long

    @Delete
    fun delete(myPreferences: MyPreferences)

    @Update
    fun update(myPreferences: MyPreferences)
}