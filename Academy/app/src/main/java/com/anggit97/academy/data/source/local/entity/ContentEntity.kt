package com.anggit97.academy.data.source.local.entity

import androidx.room.ColumnInfo

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
data class ContentEntity(
    @ColumnInfo(name = "content")
    var content: String?
)