package com.example.crudagenda.repositorio

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.crudagenda.db.modelo.Note
import com.example.crudagenda.db.db.NoteDao
import javax.inject.Inject


class NotesRepository @Inject constructor(private val db: NoteDao) {

    suspend fun addNote(note: Note) = db.insert(note)
    fun getAllNotesFlow(): LiveData<List<Note>> = db.getAllNotesFlow().asLiveData()
    suspend fun getAllNotes() = db.getAllNotes()
    suspend fun updateNote(note: Note) = db.update(note)
    suspend fun deleteNote(note: Note) = db.deleteNote(note)
    suspend fun deleteAllNotes() = db.deleteAll()
    suspend fun searchNotes(title: String) = db.searchNotes(title)

}