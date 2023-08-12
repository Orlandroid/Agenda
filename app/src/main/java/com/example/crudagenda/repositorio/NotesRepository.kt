package com.example.crudagenda.repositorio

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.db.NoteDao
import javax.inject.Inject


class NotesRepository @Inject constructor(private val db: NoteDao) {

    suspend fun addNote(note: Note) = db.insert(note)
    fun getAllNotes(): LiveData<List<Note>> = db.getAllNotes().asLiveData()
    suspend fun updateNote(note: Note) = db.update(note)
    suspend fun deleteNote(note: Note) = db.delete(note)
    suspend fun deleteAllNotes() = db.deleteAll()


}