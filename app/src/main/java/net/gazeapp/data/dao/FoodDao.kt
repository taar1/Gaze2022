package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Food

@Dao
interface FoodDao {
    @Query("SELECT * FROM Food")
    fun getAll(): List<Food>

    @Query("SELECT * FROM Food where contact_id = :contactId")
    fun getFoodsByContactId(contactId: Int): List<Food>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: Food): Long

    @Delete
    fun delete(food: Food)

    @Update
    fun update(food: Food)
}