package com.anggit97.academy.data.source.local

import androidx.lifecycle.LiveData
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.local.room.AcademyDao


/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
class LocalRepository(
    private val academyDao: AcademyDao
) {

    companion object{
        private var INSTANCE: LocalRepository? = null

        fun getInstance(academyDao: AcademyDao): LocalRepository {
            if (INSTANCE == null) {
                INSTANCE = LocalRepository(academyDao)
            }
            return INSTANCE as LocalRepository
        }
    }

    fun getAllCourses(): LiveData<List<CourseEntity>> {
        return academyDao.getAllCourses()
    }

    fun getBookmarkCourses(): LiveData<List<CourseEntity>> {
        return academyDao.getAllBookmarks()
    }

    fun getCourseWithModules(courseId: String): LiveData<CourseWithModule> {
        return academyDao.getCourseWithModulesById(courseId)
    }

    fun getAllModulesByCourse(courseId: String): LiveData<List<ModuleEntity>> {
        return academyDao.getAllModulesByCourseId(courseId)
    }

    fun insertCourses(courses: ArrayList<CourseEntity>) {
        academyDao.insertCourse(courses)
    }

    fun insertModules(modules: ArrayList<ModuleEntity>) {
        academyDao.insertModule(modules)
    }

    fun setCourseBookmark(course: CourseEntity, newState: Boolean) {
        course.bookmarked = newState
        academyDao.updateCourse(course)
    }

    fun getModuleWithContent(moduleId: String): LiveData<ModuleEntity> {
        return academyDao.getModuleById(moduleId)
    }

    fun updateContent(moduleId: String, content: String) {
        academyDao.updateModuleByContent(content, moduleId)
    }

    fun setReadModule(module: ModuleEntity) {
        module.read = true
        academyDao.updateModule(module)
    }
}
