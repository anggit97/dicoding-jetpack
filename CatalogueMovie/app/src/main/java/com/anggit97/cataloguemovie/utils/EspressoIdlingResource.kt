package com.anggit97.cataloguemovie.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by Anggit Prayogo on 2019-09-03.
 * Github : @anggit97
 */
object EspressoIdlingResource {

    val RESOURCE = "GLOBAL"
    val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }

    fun getEspressoIdlingResource(): IdlingResource {
        return espressoTestIdlingResource
    }
}