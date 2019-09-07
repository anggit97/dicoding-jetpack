package com.anggit97.academy.data.source

import androidx.lifecycle.LiveData
import com.anggit97.academy.data.source.local.LocalRepository
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.remote.ApiResponse
import com.anggit97.academy.data.source.remote.RemoteRepository
import com.anggit97.academy.data.source.remote.response.ContentResponse
import com.anggit97.academy.data.source.remote.response.CourseResponse
import com.anggit97.academy.data.source.remote.response.ModuleResponse
import com.anggit97.academy.utils.AppExecutors
import com.anggit97.academy.vo.Resource


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
open class AcademyRepository private constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
    private val appExecutors: AppExecutors
) : AcademyDataSource {

    override fun getAllCourses(): LiveData<Resource<List<CourseEntity>>> {
        return object : NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CourseEntity>> {
                return localRepository.getAllCourses()
            }

            override fun shouldFetch(data: List<CourseEntity>): Boolean {
                return data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> {
                return remoteRepository.getAllCoursesAsLiveData()
            }

            override fun saveCallResult(data: List<CourseResponse>) {
                val courseEntities = arrayListOf<CourseEntity>()

                for (course in data) {
                    with(course) {
                        courseEntities.add(
                            CourseEntity(
                                id,
                                title,
                                description,
                                date,
                                false,
                                imagePath
                            )
                        )
                    }
                }

                localRepository.insertCourses(courseEntities)
            }
        }.asLiveData()
    }

    override fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>> {
        return object :
            NetworkBoundResource<CourseWithModule, List<ModuleResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<CourseWithModule> {
                return localRepository.getCourseWithModules(courseId)
            }

            override fun shouldFetch(data: CourseWithModule): Boolean {
                return data == null || data.modules.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> {
                return remoteRepository.getModules(courseId)
            }

            override fun saveCallResult(data: List<ModuleResponse>) {
                val modules = arrayListOf<ModuleEntity>()
                for (item in data) {
                    modules.add(
                        ModuleEntity(
                            moduleId = item.moduleId,
                            courseId = item.courseId,
                            title = item.title,
                            position = item.position,
                            read = false
                        )
                    )
                }
                localRepository.insertModules(modules)
            }
        }.asLiveData()
    }

    override fun getBookmarkedCourses(): LiveData<Resource<List<CourseEntity>>> {
        return object :
            NetworkBoundResource<List<CourseEntity>, List<CourseResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<CourseEntity>> {
                return localRepository.getBookmarkCourses()
            }

            override fun shouldFetch(data: List<CourseEntity>): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<List<CourseResponse>>> {
                return remoteRepository.getAllCoursesAsLiveData()
            }

            override fun saveCallResult(data: List<CourseResponse>) {

            }
        }.asLiveData()
    }

    override fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>> {
        return object :
            NetworkBoundResource<List<ModuleEntity>, List<ModuleResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<ModuleEntity>> {
                return localRepository.getAllModulesByCourse(courseId)
            }

            override fun shouldFetch(data: List<ModuleEntity>): Boolean {
                return data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<ModuleResponse>>> {
                return remoteRepository.getModules(courseId)
            }

            override fun saveCallResult(data: List<ModuleResponse>) {
                val moduleEntities = arrayListOf<ModuleEntity>()

                for (moduleResponse in data) {
                    moduleEntities.add(
                        ModuleEntity(
                            moduleId = moduleResponse.moduleId,
                            courseId = courseId,
                            title = moduleResponse.title,
                            position = moduleResponse.position,
                            read = null
                        )
                    )
                }

                localRepository.insertModules(moduleEntities)
            }
        }.asLiveData()
    }

    override fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>> {
        return object : NetworkBoundResource<ModuleEntity, ContentResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<ModuleEntity> {
                return localRepository.getModuleWithContent(moduleId)
            }

            override fun shouldFetch(data: ModuleEntity): Boolean {
                return (data.contentEntity == null)
            }

            override fun createCall(): LiveData<ApiResponse<ContentResponse>> {
                return remoteRepository.getContent(moduleId)
            }

            override fun saveCallResult(data: ContentResponse) {
                localRepository.updateContent(moduleId, data.content)
            }
        }.asLiveData()
    }

    override fun setCourseBookmark(course: CourseEntity, state: Boolean) {
        val runnable = Runnable {
            localRepository.setCourseBookmark(course, state)
        }
        appExecutors.diskIO().execute(runnable)
    }

    override fun setReadModule(module: ModuleEntity) {
        val runnable = Runnable {
            localRepository.setReadModule(module)
        }
        appExecutors.diskIO().execute(runnable)
    }

    companion object {

        @Volatile
        private var INSTANCE: AcademyRepository? = null

        fun getInstance(
            remoteData: RemoteRepository,
            localRepository: LocalRepository,
            appExecutors: AppExecutors
        ): AcademyRepository? {
            if (INSTANCE == null) {
                synchronized(AcademyRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AcademyRepository(remoteData, localRepository, appExecutors)
                    }
                }
            }
            return INSTANCE
        }
    }
}