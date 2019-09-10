package com.anggit97.academy.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.vo.Resource


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getBookmarkedCourses(): LiveData<Resource<PagedList<CourseEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    fun setCourseBookmark(course: CourseEntity, state: Boolean)

    fun setReadModule(module: ModuleEntity)
}