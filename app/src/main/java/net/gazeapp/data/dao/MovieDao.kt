package net.gazeapp.data.dao

import androidx.room.*
import net.gazeapp.data.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * from Movie ORDER BY movie ASC")
    fun getAll(): List<Movie>

    @Query("SELECT * from Movie WHERE movie LIKE :typing ORDER BY movie ASC")
    fun getMoviesByTyping(typing: String): List<Movie>

    @Query("SELECT * FROM Movie where contact_id = :contactId ORDER BY movie ASC")
    fun getMovies(contactId: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: Movie): List<Long>

    @Delete
    fun delete(movie: Movie)

    @Update
    fun update(movie: Movie)
}