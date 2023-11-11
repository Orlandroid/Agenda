package com.example.crudagenda.repositorio

import com.example.crudagenda.db.db.NoteDao
import com.example.crudagenda.db.modelo.Note
import javax.inject.Inject


class NotesRepository @Inject constructor(private val db: NoteDao) {

    suspend fun addNote(note: Note) = db.insert(note)
    fun getAllNotesFlow() = db.getAllNotesFlow()
    suspend fun getAllNotes() = db.getAllNotes()
    suspend fun updateNote(note: Note) = db.update(note)
    suspend fun deleteNote(note: Note) = db.deleteNote(note)
    suspend fun deleteAllNotes() = db.deleteAll()
    suspend fun searchNotes(title: String) = db.searchNotes(title)

    suspend fun getAllNotesByPriority(priority: String) = db.getAllNotesByPriority(priority)

}