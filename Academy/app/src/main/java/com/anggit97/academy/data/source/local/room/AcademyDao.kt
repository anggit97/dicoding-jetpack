package com.anggit97.academy.data.source.local.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.data.source.local.entity.ModuleEntity

/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
@Dao
interface AcademyDao {

    @WorkerThread
    @Query("SELECT * FROM courseentities")
    fun getAllCourses(): LiveData<List<CourseEntity>>

    @WorkerThread
    @Query("SELECT * FROM courseentities WHERE bookmarked = 1")
    fun getAllBookmarks(): DataSource.Factory<Int, CourseEntity>

    @Transaction
    @Query("SELECT * FROM courseentities WHERE courseId = :courseId")
    fun getCourseWithModulesById(courseId: String): LiveData<CourseWithModule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: List<CourseEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateCourse(course: CourseEntity): Int

    @Query("SELECT * FROM moduleentities WHERE courseId=:courseId")
    fun getAllModulesByCourseId(courseId: String): LiveData<List<ModuleEntity>>

    @Query("SELECT * FROM moduleentities WHERE moduleId=:moduleId")
    fun getModuleById(moduleId: String): LiveData<ModuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModule(module: List<ModuleEntity>): LongArray

    @Update
    fun updateModule(module: ModuleEntity): Int

    @Query("UPDATE moduleentities SET content = :content WHERE moduleId=:moduleId")
    fun updateModuleByContent(content: String, moduleId: String)
}