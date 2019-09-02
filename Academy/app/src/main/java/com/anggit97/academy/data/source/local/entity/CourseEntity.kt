package com.anggit97.academy.data.source.local.entity

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
data class CourseEntity(
    var courseId: String?,
    var title: String?,
    var description: String?,
    var deadline: String?,
    var bookmarked: Boolean? = false,
    var imagePath: String?
)