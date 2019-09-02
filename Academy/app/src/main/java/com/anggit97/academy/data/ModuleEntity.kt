package com.anggit97.academy.data

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
data class ModuleEntity(
    var contentEntity: ContentEntity? = null,
    var moduleId: String?,
    var courseId: String?,
    var title: String?,
    var position: Int?,
    var read: Boolean? = false
)