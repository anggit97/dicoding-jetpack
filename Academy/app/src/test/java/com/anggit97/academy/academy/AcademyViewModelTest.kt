package com.anggit97.academy.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Anggit PRayogo on 2019-08-24.
 * github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var SUT: AcademyViewModel

    @Mock
    private lateinit var mAcademyRepository: AcademyRepository

    private var dummySizeItem = 5

    @Before
    fun setup() {
        SUT = AcademyViewModel(mAcademyRepository)
    }

//    Memuat Courses:
//
//    Memanipulasi data ketika pemanggilan data course di kelas repository.
//
//    Memastikan metode di kelas repository terpanggil.
//
//    Melakukan pengecekan data course apakah null atau tidak.
//
//    Melakukan pengecekan jumlah data course apakah sudah sesuai atau belum.
    @Test
    fun getAllCourses() {
        val dummyCourses = DataDummy.generateDummyCourses()
        val courses = MutableLiveData<ArrayList<CourseEntity>>()
        courses.value = dummyCourses

        `when`(mAcademyRepository.getAllCourses()).thenReturn(courses)

        val observer : Observer<ArrayList<CourseEntity>> = mock(Observer::class.java) as Observer<ArrayList<CourseEntity>>

        SUT.getCourses().observeForever(observer)

        verify(observer).onChanged(dummyCourses)
    }
}