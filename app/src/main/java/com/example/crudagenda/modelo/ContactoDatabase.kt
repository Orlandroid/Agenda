package com.example.crudagenda.modelo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crudagenda.util.BitMapConverter

@Database(entities = [Contacto::class], version = 1)
@TypeConverters(BitMapConverter::class)
abstract class ContactoDatabase : RoomDatabase() {

    abstract fun contactoDao(): ContactoDao

}
