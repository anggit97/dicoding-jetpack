package com.anggit97.academy.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggit97.academy.data.ContentEntity
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.data.source.remote.RemoteRepository
import com.anggit97.academy.data.source.remote.response.ContentResponse
import com.anggit97.academy.data.source.remote.response.CourseResponse
import com.anggit97.academy.data.source.remote.response.ModuleResponse


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
open class AcademyRepository private constructor(
    private val remoteRepository: RemoteRepository
) : AcademyDataSource {

    override fun getAllCourses(): LiveData<ArrayList<CourseEntity>> {
        val courseResult = MutableLiveData<ArrayList<CourseEntity>>()
        remoteRepository.getAllCourses(object : RemoteRepository.LoadCourseCallback {
            override fun onAllCourseRecieved(courseResponse: List<CourseResponse>) {
                val courseList = arrayListOf<CourseEntity>()
                for (i in 0 until courseResponse.size) {
                    val response = courseResponse[i]
                    val course = CourseEntity(
                        response.id,
                        response.title,
                        response.description,
                        response.date,
                        false,
                        response.imagePath
                    )

                    courseList.add(course)
                }
                courseResult.postValue(courseList)
            }

            override fun onDataNotAvailable() {

            }
        })
        return courseResult
    }

    override fun getCourseWithModules(courseId: String): LiveData<CourseEntity?> {
        val courseResult = MutableLiveData<CourseEntity>()
        remoteRepository.getAllCourses(object : RemoteRepository.LoadCourseCallback {
            override fun onAllCourseRecieved(courseResponse: List<CourseResponse>) {
                for (item in courseResponse) {
                    if (item.id == courseId) {
                        val course = CourseEntity(
                            item.id,
                            item.title,
                            item.description,
                            item.date,
                            false,
                            item.imagePath
                        )
                        courseResult.postValue(course)
                    }
                }
            }

            override fun onDataNotAvailable() {

            }
        })
        return courseResult
    }

    override fun getBookmarkedCourses(): MutableLiveData<ArrayList<CourseEntity>> {
        val courseResults = MutableLiveData<ArrayList<CourseEntity>>()
        remoteRepository.getAllCourses(object: RemoteRepository.LoadCourseCallback{
            override fun onAllCourseRecieved(courseResponse: List<CourseResponse>) {
                val bookmarksList = ArrayList<CourseEntity>()
                for (item in courseResponse){
                    val bookmark = CourseEntity(
                        item.id,
                        item.title,
                        item.description,
                        item.date,
                        false,
                        item.imagePath
                    )
                    bookmarksList.add(bookmark)
                }
                courseResults.postValue(bookmarksList)
            }

            override fun onDataNotAvailable() {

            }
        })
        return courseResults
    }

    override fun getAllModulesByCourse(courseId: String): MutableLiveData<ArrayList<ModuleEntity>> {
        val modulesResults = MutableLiveData<ArrayList<ModuleEntity>>()
        remoteRepository.getModules(courseId, object: RemoteRepository.LoadModuleCallback{
            override fun onAllModuleRecieved(modeleResponse: List<ModuleResponse>) {
                val mResponse = ArrayList<ModuleEntity>()
                for (item in modeleResponse){
                    val course = ModuleEntity(
                        moduleId = item.moduleId,
                        courseId = item.courseId,
                        title = item.title,
                        position = item.position,
                        read = false
                    )
                    mResponse.add(course)
                }
                modulesResults.postValue(mResponse)
            }

            override fun onDataNotAvailable() {

            }
        })
        return modulesResults
    }

    override fun getContent(courseId: String, moduleId: String): MutableLiveData<ModuleEntity> {
        val contentResult = MutableLiveData<ModuleEntity>()

        remoteRepository.getModules(courseId, object: RemoteRepository.LoadModuleCallback{
            override fun onAllModuleRecieved(modeleResponse: List<ModuleResponse>) {
                val content: ModuleEntity
                for (module in modeleResponse){
                    if (module.moduleId == moduleId){
                        content = ModuleEntity(
                            moduleId = module.moduleId,
                            courseId = module.courseId,
                            title = module.title,
                            position = module.position,
                            read = false
                        )

                        remoteRepository.getContent(moduleId, object : RemoteRepository.GetContentCallback{
                            override fun onContentRecieved(contentResponse: ContentResponse) {
                                content.contentEntity = ContentEntity(contentResponse.content)
                                contentResult.postValue(content)
                            }

                            override fun onDataNotAvailable() {

                            }
                        })
                        break
                    }
                }
            }

            override fun onDataNotAvailable() {

            }
        })
        return contentResult
    }

    companion object {

        @Volatile
        private var INSTANCE: AcademyRepository? = null

        fun getInstance(remoteData: RemoteRepository): AcademyRepository? {
            if (INSTANCE == null) {
                synchronized(AcademyRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AcademyRepository(remoteData)
                    }
                }
            }
            return INSTANCE
        }
    }
}