package com.example.notesapptask.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapptask.Repository.Repository
import com.example.notesapptask.Database.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(context: Application): AndroidViewModel(context){
    private val notesRepository: Repository = Repository(context)
    private val notes=MutableLiveData<List<Note>>()
     val _notes:LiveData<List<Note>> get() =notes

    fun insertDataInRoom(obj : Note){
        viewModelScope.launch {
            notesRepository.insertDataInRoom(obj)
        }
    }
    fun updateNotes(obj : Note){
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.updateNotes(obj)
        }
    }
    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteJobs = notesRepository.getNotes()
            notes.postValue(favoriteJobs)
        }
    }
    fun deleteNotes(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
           notesRepository.deleteNote(note)
            getAllNotes()
        }
    }

}