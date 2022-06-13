package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Address

@Dao
interface AddressDao {
    @Query("SELECT * FROM Address")
    fun getAll(): List<Address>

    @Query("SELECT * FROM Address where contact_id = :contactId")
    fun getAddressesByContactId(contactId: Int): List<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: Address): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg addresses: Address): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(addresses: List<Address>): List<Long>

    @Delete
    fun delete(address: Address)

    @Update
    fun update(address: Address)

    @Query("DELETE FROM Address where contact_id = :contactId")
    fun deleteAll(contactId: Int)
}