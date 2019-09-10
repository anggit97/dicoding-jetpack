package com.anggit97.mynotesapps.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.anggit97.mynotesapps.database.Note
import com.anggit97.mynotesapps.database.NoteDao
import com.anggit97.mynotesapps.database.NoteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
class NoteRepository(
    application: Application
) {

    private var db: NoteDatabase = NoteDatabase(application)
    private var mNotesDao: NoteDao
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        mNotesDao = db.noteDao()
    }

    fun getAllNotes(): DataSource.Factory<Int, Note> {
        return mNotesDao.getAllNotes()
    }

    fun insertNote(note: Note) {
        executorService.execute {
            mNotesDao.insert(note)
        }
    }

    fun delete(note: Note) {
        executorService.execute {
            mNotesDao.delete(note)
        }
    }

    fun update(note: Note) {
        executorService.execute {
            mNotesDao.update(note)
        }
    }
}