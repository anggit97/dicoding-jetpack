package com.anggit97.academy.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.vo.Resource

/**
 * Created by Anggit PRayogo on 2019-08-23.
 * github : @anggit97
 */
class BookmarkViewModel(private val mAcademyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): LiveData<Resource<PagedList<CourseEntity>>> {
        return mAcademyRepository.getBookmarkedCourses()
    }

    fun setBookmark(courseEntity: CourseEntity) {
        courseEntity.bookmarked?.not()
            .also {
                it?.let { isBookmark ->
                    mAcademyRepository.setCourseBookmark(courseEntity, isBookmark)
                }
            }
    }
}