package com.anggit97.academy.data.source.remote

import android.os.Handler
import com.anggit97.academy.utils.JsonHelper
import com.anggit97.academy.data.source.remote.response.ContentResponse
import com.anggit97.academy.data.source.remote.response.ModuleResponse
import com.anggit97.academy.data.source.remote.response.CourseResponse
import com.anggit97.academy.utils.EspressoIdlingResource


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
class RemoteRepository constructor(private val jsonHelper: JsonHelper) {

    private val SERVICE_LATENCY_IN_MILLIS : Long = 2000

    companion object {

        private var INSTANCE: RemoteRepository? = null

        fun getInstance(helper: JsonHelper): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(helper)
            }
            return INSTANCE as RemoteRepository
        }
    }

    fun getAllCourses(callback: LoadCourseCallback){
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed({
            EspressoIdlingResource.decrement()
            callback.onAllCourseRecieved(jsonHelper.loadCourses())
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getModules(courseId: String, callback: LoadModuleCallback){
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed({
            EspressoIdlingResource.decrement()
            callback.onAllModuleRecieved(jsonHelper.loadModule(courseId))
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getContent(moduleId: String, callback: GetContentCallback){
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed({
            EspressoIdlingResource.decrement()
            jsonHelper.loadContent(moduleId)?.let { callback.onContentRecieved(it) }
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    interface LoadCourseCallback{

        fun onAllCourseRecieved(courseResponse: List<CourseResponse>)

        fun onDataNotAvailable()
    }

    interface LoadModuleCallback{

        fun onAllModuleRecieved(modeleResponse: List<ModuleResponse>)

        fun onDataNotAvailable()
    }

    interface GetContentCallback{

        fun onContentRecieved(contentResponse: ContentResponse)

        fun onDataNotAvailable()
    }
}