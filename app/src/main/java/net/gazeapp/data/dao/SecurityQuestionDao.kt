package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.SecurityQuestion

@Dao
interface SecurityQuestionDao {
    @Query("SELECT * FROM SecurityQuestion")
    fun getAll(): List<SecurityQuestion>

    @Query("SELECT * FROM SecurityQuestion where ID = :id")
    fun getSecurityQuestion(id: Int): SecurityQuestion

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(securityQuestion: SecurityQuestion): Long

    @Delete
    fun delete(securityQuestion: SecurityQuestion)

    @Update
    fun update(securityQuestion: SecurityQuestion)
}