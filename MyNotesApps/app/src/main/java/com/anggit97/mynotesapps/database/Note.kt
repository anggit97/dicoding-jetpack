package com.anggit97.mynotesapps.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Anggit Prayogo on 2019-09-06.
 * Github : @anggit97
 */

@Entity
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,
    @ColumnInfo(name = "title")
    var title: String? = "title",
    @ColumnInfo(name = "description")
    var description: String? = "desc",
    @ColumnInfo(name = "date")
    var date: String? = "date"
) : Parcelable