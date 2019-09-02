package com.anggit97.academy.bookmark

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Created by Anggit PRayogo on 2019-08-24.
 * github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class BookmarkViewModelTest {

    private lateinit var SUT: BookmarkViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mAcademyRepository: AcademyRepository

    private val dummySize = 5

    @Before
    fun setUp() {
        SUT = BookmarkViewModel(mAcademyRepository)
    }

    //    Memuat Bookmarks:
//
//    Memanipulasi data ketika pemanggilan data course di kelas repository.
//
//    Memastikan metode di kelas repository terpanggil.
//
//    Melakukan pengecekan data course apakah null atau tidak.
//
//    Melakukan pengecekan jumlah data course apakah sudah sesuai atau belum.
    @Test
    fun generateBookmarks() {
        val dummyCourse = DataDummy.generateDummyCourses()
        val course = MutableLiveData<ArrayList<CourseEntity>>()
        course.value = dummyCourse

        `when`(mAcademyRepository.getAllCourses()).thenReturn(course)
        val observer : Observer<*> = mock(Observer::class.java)

        SUT.getBookmarks().observeForever(observer as Observer<in ArrayList<CourseEntity>>)
        verify(observer).onChanged(dummyCourse)
    }
}