package com.anggit97.myidlingresource

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource



/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */

object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    internal val espressoIdlingResource: IdlingResource
        get() = espressoTestIdlingResource

    internal fun increment() {
        espressoTestIdlingResource.increment()
    }

    internal fun decrement() {
        espressoTestIdlingResource.decrement()
    }
}