package com.example.crudagenda.db.convertes

import androidx.room.TypeConverter
import com.example.crudagenda.db.modelo.Priority

class EnumConverter {

    @TypeConverter
    fun toPriority(value: String) = enumValueOf<Priority>(value)

    @TypeConverter
    fun fromPriority(value: Priority) = value.name

}