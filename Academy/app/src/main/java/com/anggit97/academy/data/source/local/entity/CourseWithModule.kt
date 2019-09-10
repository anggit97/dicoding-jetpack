package com.anggit97.academy.data.source.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Anggit Prayogo on 2019-09-07.
 * Github : @anggit97
 */

data class CourseWithModule(
    @Embedded
    var courseEntity: CourseEntity? = null,
    @Relation(
        parentColumn = "courseId",
        entityColumn = "courseId"
    )
    var modules: List<ModuleEntity>? = null
)