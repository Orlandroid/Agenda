package com.example.crudagenda.db.db

import androidx.room.*
import com.example.crudagenda.db.modelo.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotesFlow(): Flow<List<Note>>

    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note where title LIKE :title")
    suspend fun searchNotes(title: String): List<Note>

    @Query("SELECT * FROM note where id = :id")
    fun getById(id: Int): Flow<Note>

    @Query("DELETE  FROM note")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note): Long

    @Delete
    suspend fun deleteNote(note: Note): Int

    @Update
    suspend fun update(note: Note)


}