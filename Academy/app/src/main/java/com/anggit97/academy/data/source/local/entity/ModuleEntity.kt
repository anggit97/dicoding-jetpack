package com.anggit97.academy.data.source.local.entity

import androidx.room.*

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
@Entity(
    tableName = "moduleentities",
    primaryKeys = ["moduleId", "courseId"],
    foreignKeys = [ForeignKey(
        entity = CourseEntity::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"]
    )],
    indices = [
        Index(
            value = ["courseId", "moduleId"]
        )
    ]
)
data class ModuleEntity(
    @Embedded
    var contentEntity: ContentEntity? = null,
    @ColumnInfo(
        name = "moduleId"
    )
    var moduleId: String,
    @ColumnInfo(
        name = "courseId"
    )
    var courseId: String,
    @ColumnInfo(
        name = "title"
    )
    var title: String?,
    @ColumnInfo(
        name = "position"
    )
    var position: Int?,
    @ColumnInfo(
        name = "read"
    )
    var read: Boolean? = false
)

