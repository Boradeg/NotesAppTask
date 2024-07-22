package com.example.notesapptask.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Note")
data class Note(
    @PrimaryKey(autoGenerate=true)
    val id: Int = 0,
    val notesTitle: String,
    val notesDesc: String
)