package com.anggit97.mynotesapps.helper

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */
object DateHelper {

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}