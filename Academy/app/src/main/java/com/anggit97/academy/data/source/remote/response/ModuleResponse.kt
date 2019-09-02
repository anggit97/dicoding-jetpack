package com.anggit97.academy.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Anggit Prayogo on 2019-08-30.
 * Github : @anggit97
 */
@Parcelize
data class ModuleResponse(
    var moduleId: String,
    var courseId: String,
    var title: String,
    var position: Int
) : Parcelable