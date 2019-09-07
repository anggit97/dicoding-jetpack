package com.anggit97.academy.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.ModuleEntity

/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
@Database(
    entities = [CourseEntity::class, ModuleEntity::class],
    version = 1
)
abstract class AcademyDatabase : RoomDatabase() {

    abstract fun academyDao(): AcademyDao

    companion object {
        @Volatile
        var instance: AcademyDatabase? = null
        val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AcademyDatabase::class.java,
            "academies.db"
        ).build()
    }
}