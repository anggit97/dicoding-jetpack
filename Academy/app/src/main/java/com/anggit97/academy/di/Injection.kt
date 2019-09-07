package com.anggit97.academy.di

import android.app.Application
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.data.source.local.LocalRepository
import com.anggit97.academy.data.source.local.room.AcademyDatabase
import com.anggit97.academy.data.source.remote.RemoteRepository
import com.anggit97.academy.utils.AppExecutors
import com.anggit97.academy.utils.JsonHelper


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
object Injection {
    fun provideRepository(application: Application): AcademyRepository {

        val database = AcademyDatabase(application)

        val localRepository = LocalRepository.getInstance(database.academyDao())
        val remoteRepository = RemoteRepository.getInstance(JsonHelper(application))
        val appExecutors = AppExecutors()

        return AcademyRepository.getInstance(remoteRepository, localRepository, appExecutors)!!
    }
}
