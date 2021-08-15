package com.example.crudagenda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val phone: String,
    val birthday: String,
    val nota: String
)
