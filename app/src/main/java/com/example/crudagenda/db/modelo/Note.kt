package com.example.crudagenda.db.modelo


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    var isComplete: Boolean = false,
    val priority: Priority
)

enum class Priority {
    LOW,
    MEDIUM,
    HIGH
}
