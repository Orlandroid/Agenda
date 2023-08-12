package com.example.crudagenda.db.db

import androidx.room.*
import com.example.crudagenda.db.modelo.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note where id = :id")
    fun getById(id: Int): Flow<Note>

    @Query("DELETE  FROM note")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)


}