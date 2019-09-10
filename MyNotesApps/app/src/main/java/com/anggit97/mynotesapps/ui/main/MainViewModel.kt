package com.anggit97.mynotesapps.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.anggit97.mynotesapps.database.Note
import com.anggit97.mynotesapps.repository.NoteRepository

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
class MainViewModel(
    application: Application
) : ViewModel() {

    var noteRepository = NoteRepository(application)

    fun getAllNotes(): LiveData<PagedList<Note>> {
        return LivePagedListBuilder(noteRepository.getAllNotes(), 10).build()
    }
}