package com.anggit97.academy.utils

import java.util.concurrent.Executor

/**
 * Created by Anggit Prayogo on 2019-09-08.
 * Github : @anggit97
 */

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}
