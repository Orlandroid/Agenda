package com.example.crudagenda.repositorio

import android.content.Context
import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.modelo.ContactoDatabase

class ContactoRepository(context: Context) {

    private val db = ContactoDatabase.getDatabase(context).contactoDao()


    suspend fun addContacto(contacto: Contacto) {
        db.insert(contacto)
    }

    suspend fun getAllContacs(): List<Contacto> {
        return db.getAllContacs()
    }

    suspend fun updateContact(contacto: Contacto) {
        db.update(contacto)
    }

    suspend fun deleteContacto(contacto: Contacto) {
        db.delete(contacto)
    }

    suspend fun deleteAllContactos() {
        db.deleteAll()
    }

}