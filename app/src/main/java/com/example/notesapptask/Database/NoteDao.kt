package com.example.notesapptask.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Query("SELECT * FROM Note")
    fun getAllNotes(): List<Note>

    @Update()
     fun updateNotes(note: Note)

    @Delete()
    fun deleteNote(note: Note)
}