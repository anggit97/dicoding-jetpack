package com.anggit97.academy.data.source

import com.anggit97.academy.data.source.remote.ApiResponse
import androidx.lifecycle.LiveData
import com.anggit97.academy.utils.AppExecutors
import androidx.lifecycle.MediatorLiveData
import com.anggit97.academy.data.source.remote.StatusResponse.*
import com.anggit97.academy.vo.Resource


/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */
abstract class NetworkBoundResource<ResultType, RequestType>(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    private val mExecutors: AppExecutors? = null

    init {
        result.value = Resource.loading(null)
        val dbSource = loadFromDB()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(
                    Resource.success(
                        newData
                    )
                ) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(
            dbSource
        ) { newData -> result.setValue(Resource.loading(newData)) }

        result.addSource(apiResponse) { response ->

            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                SUCCESS -> appExecutors?.diskIO()?.execute {

                    saveCallResult(response.body)

                    appExecutors.mainThread().execute {
                        result.addSource(
                            loadFromDB()
                        ) { newData -> result.setValue(
                            Resource.success(
                                newData
                            )
                        ) }
                    }
                }

                EMPTY -> appExecutors.mainThread().execute {
                    result.addSource(
                        loadFromDB()
                    ) { newData -> result.setValue(
                        Resource.success(
                            newData
                        )
                    ) }
                }
                ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error("Error cannot fetch!", newData)
                    }
                }
            }
        }


    }

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)
}