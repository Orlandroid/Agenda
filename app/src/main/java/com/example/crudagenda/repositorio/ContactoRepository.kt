package com.example.crudagenda.repositorio

import com.example.crudagenda.modelo.Contacto
import com.example.crudagenda.modelo.ContactoDao
import javax.inject.Inject


class ContactoRepository @Inject constructor(private val db: ContactoDao) {


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