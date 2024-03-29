package com.anggit97.mynotesapps.database

import androidx.paging.DataSource
import androidx.room.*

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Note>)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getAllNotes(): DataSource.Factory<Int, Note>
}