package com.example.crudagenda.data

import androidx.room.*

@Dao
interface ContactoDao {

    @Query("SELECT * FROM contactos")
    suspend fun getAllContacs(): List<Contacto>

    @Query("SELECT * FROM contactos where id = :id")
    suspend fun getById(id: Int): Contacto

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contacto: Contacto)


    @Delete
    suspend fun delete(contacto: Contacto)

    @Update
    suspend fun update(contacto: Contacto)


}