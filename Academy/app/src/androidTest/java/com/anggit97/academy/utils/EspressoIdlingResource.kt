package com.anggit97.academy.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource


/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */

object EspressoIdlingResource {

    private val RESOURCE = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    val espressoIdlingResourceForMainActivity: IdlingResource
        get() = espressoTestIdlingResource

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }
}