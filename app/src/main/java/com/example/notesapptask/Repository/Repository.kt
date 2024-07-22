package com.example.notesapptask.Repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.notesapptask.Database.AppDatabase
import com.example.notesapptask.Database.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database4")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val userDao by lazy { db.noteDao() }

    suspend fun insertDataInRoom(note: Note) {
        withContext(Dispatchers.IO) {
            try {
                userDao.insert(note)
            } catch (e: Exception) {
                Log.e("Repository", "Error inserting note", e)
            }
        }
    }

    suspend fun getNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            try {
                userDao.getAllNotes()
            } catch (e: Exception) {
                Log.e("Repository", "Error fetching notes", e)
                emptyList()
            }
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            try {
                userDao.deleteNote(note)
            } catch (e: Exception) {
                Log.e("Repository", "Error deleting note", e)
            }
        }
    }

    suspend fun updateNotes(note: Note) {
        withContext(Dispatchers.IO) {
            try {
                userDao.updateNotes(note)
            } catch (e: Exception) {
                Log.e("Repository", "Error updating note", e)
            }
        }
    }
}
