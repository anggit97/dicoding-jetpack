package com.anggit97.mynotesapps.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.anggit97.mynotesapps.database.Note
import com.anggit97.mynotesapps.repository.NoteRepository

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
class NoteAddUpdateViewModel(
    application: Application
): ViewModel() {

    private var noteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note){
        noteRepository.insertNote(note)
    }

    fun update(note: Note){
        noteRepository.update(note)
    }

    fun delete(note: Note){
        noteRepository.delete(note)
    }
}