package com.anggit97.academy.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors


/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */

internal class DiskIOThreadExecutor : Executor {

    private val mDiskIO: Executor

    init {
        mDiskIO = Executors.newSingleThreadExecutor()
    }

    override fun execute(command: Runnable) {
        mDiskIO.execute(command)
    }
}