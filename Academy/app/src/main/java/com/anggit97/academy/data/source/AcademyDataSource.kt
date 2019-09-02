package com.anggit97.academy.data.source

import androidx.lifecycle.LiveData
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.local.entity.CourseEntity


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
interface AcademyDataSource {

    fun getAllCourses(): LiveData<ArrayList<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<CourseEntity?>

    fun getAllModulesByCourse(courseId: String): LiveData<ArrayList<ModuleEntity>>

    fun getBookmarkedCourses(): LiveData<ArrayList<CourseEntity>>

    fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity?>
}