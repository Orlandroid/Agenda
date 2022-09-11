package com.example.crudagenda.modelo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactoDao {

    @Query("SELECT * FROM contactos")
    fun getAllContacs(): Flow<List<Contacto>>

    @Query("SELECT * FROM contactos where id = :id")
    fun getById(id: Int): Flow<Contacto>

    @Query("DELETE  FROM contactos")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacto: Contacto)

    @Delete
    suspend fun delete(contacto: Contacto)

    @Update
    suspend fun update(contacto: Contacto)


}