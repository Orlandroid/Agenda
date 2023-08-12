package com.example.crudagenda.db.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crudagenda.db.convertes.BitMapConverter
import com.example.crudagenda.db.convertes.EnumConverter
import com.example.crudagenda.db.modelo.Note

@Database(entities = [Note::class], version = 2)
@TypeConverters(BitMapConverter::class, EnumConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

}
