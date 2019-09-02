package com.anggit97.academy.di

import android.app.Application
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.JsonHelper
import com.anggit97.academy.data.source.remote.RemoteRepository


/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
object Injection {

    fun provideRepository(application: Application): AcademyRepository? {
        val remoteRepository = RemoteRepository.getInstance(JsonHelper(application))
        return AcademyRepository.getInstance(remoteRepository)
    }
}