package com.anggit97.mynotesapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
                add()
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).fallbackToDestructiveMigration().setJournalMode(JournalMode.TRUNCATE).build()

        fun add() {
            Executors.newSingleThreadExecutor().execute {
                val list = arrayListOf<Note>()
                for (i in 0..29) {
                    list.add(Note(i, "Tugas $i", "Belajar Modul $i", ""))
                }
                INSTANCE?.noteDao()?.insertAll(list)
            }
        }
    }
}