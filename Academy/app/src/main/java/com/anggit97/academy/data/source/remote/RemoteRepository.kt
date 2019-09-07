package com.anggit97.academy.data.source.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggit97.academy.data.source.remote.response.ContentResponse
import com.anggit97.academy.data.source.remote.response.CourseResponse
import com.anggit97.academy.data.source.remote.response.ModuleResponse
import com.anggit97.academy.utils.EspressoIdlingResource
import com.anggit97.academy.utils.JsonHelper


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
class RemoteRepository constructor(private val jsonHelper: JsonHelper) {

    private val SERVICE_LATENCY_IN_MILLIS: Long = 2000

    companion object {

        private var INSTANCE: RemoteRepository? = null

        fun getInstance(helper: JsonHelper): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(helper)
            }
            return INSTANCE as RemoteRepository
        }
    }

    fun getAllCoursesAsLiveData(): LiveData<ApiResponse<List<CourseResponse>>> {
        EspressoIdlingResource.increment()
        val resultCourses = MutableLiveData<ApiResponse<List<CourseResponse>>>()

        val handler = Handler()
        handler.postDelayed({
            resultCourses.value = ApiResponse.success(jsonHelper.loadCourses())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultCourses
    }

    fun getModules(courseId: String): LiveData<ApiResponse<List<ModuleResponse>>> {
        EspressoIdlingResource.increment()
        val resultModules = MutableLiveData<ApiResponse<List<ModuleResponse>>>()

        val handler = Handler()
        handler.postDelayed({
            resultModules.value = ApiResponse.success(jsonHelper.loadModule(courseId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultModules
    }

    fun getContent(moduleId: String): LiveData<ApiResponse<ContentResponse>> {
        val resultContent = MutableLiveData<ApiResponse<ContentResponse>>()
        EspressoIdlingResource.increment()

        val handler = Handler()
        handler.postDelayed({
            jsonHelper.loadContent(moduleId)?.let { content ->
                resultContent.value = ApiResponse.success(content)
            }
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultContent
    }

    interface LoadCourseCallback {

        fun onAllCourseRecieved(courseResponse: List<CourseResponse>)

        fun onDataNotAvailable()
    }

    interface LoadModuleCallback {

        fun onAllModuleRecieved(modeleResponse: List<ModuleResponse>)

        fun onDataNotAvailable()
    }

    interface GetContentCallback {

        fun onContentRecieved(contentResponse: ContentResponse)

        fun onDataNotAvailable()
    }
}