package com.anggit97.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * Created by Anggit Prayogo on 2019-09-01.
 * Github : @anggit97
 */

class MainViewModel : ViewModel() {

    private val ONE_SECONDS: Long = 1000
    private var mInitialtime: Long? = null

    private var mElapsetime: MutableLiveData<Long> = MutableLiveData()

    init {
        mInitialtime = SystemClock.elapsedRealtime()
        val timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime().minus(mInitialtime!!)).div(100)
                mElapsetime.postValue(newValue)
            }
        }, ONE_SECONDS, ONE_SECONDS)
    }

    fun getElapseTime() : LiveData<Long> = mElapsetime
}