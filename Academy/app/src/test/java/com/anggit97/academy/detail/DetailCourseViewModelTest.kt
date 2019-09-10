package com.anggit97.academy.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.utils.DataDummy
import com.anggit97.academy.vo.Resource
import com.nhaarman.mockitokotlin2.mock
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito.*


/**
 * Created by Anggit PRayogo on 2019-08-24.
 * github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class DetailCourseViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mAcademyRepository: AcademyRepository

    private val dummySize = 7
    private val dummyTitle = "Menjadi Android Developer Expert"

    private lateinit var SUT: DetailCourseViewModel

    private val courseEntity = DataDummy.generateDummyCourses()[0]
    private val modulesFake = DataDummy.generateDummyModules(courseEntity.courseId!!)

    @Before
    fun setUp() {
        SUT = DetailCourseViewModel(mAcademyRepository)
        courseEntity.courseId.let { SUT.setCourseId(it) }
    }

    @Test
    fun loadCourseWithModules() {
        val resource =
            Resource.success(DataDummy.generateDummyCourseWithModules(courseEntity, true))
        val course = MutableLiveData<Resource<CourseWithModule>>()
        course.value = resource

        `when`(mAcademyRepository.getCourseWithModules(courseEntity.courseId))
            .thenReturn(course)

        val observer = mock(Observer::class.java)
        SUT.courseModules.observeForever(observer as Observer<in Resource<CourseWithModule>>)

        verify(observer).onChanged(resource)
    }

//    //    DetailCourseViewModelTest:
////
////    Memuat Course:
////
////    Memanipulasi data ketika pemanggilan data course di kelas repository.
////
////    Memastikan metode di kelas repository terpanggil.
////
////    Melakukan pengecekan data course apakah null atau tidak.
////
////    Membandingkan data course sudah sesuai dengan yang diharapkan atau tidak.
//
//    @Test
//    fun loadCourses() {
//        val course = MutableLiveData<CourseEntity>()
//        course.value = courseEntity
//        `when`(mAcademyRepository.getCourseWithModules(courseEntity.courseId!!))
//            .thenReturn(course)
//        val observer = mock(Observer::class.java)
//        SUT.getCourse().observeForever(observer as Observer<in CourseEntity?>)
//        verify(observer).onChanged(courseEntity)
//    }
//
////
////    //    Memuat Modules:
//////
//////    Memanipulasi data ketika pemanggilan data module di kelas repository.
//////
//////    Memastikan metode di kelas repository terpanggil.
//////
//////    Melakukan pengecekan data module apakah null atau tidak.
//////
//////    Melakukan pengecekan jumlah data module apakah sudah sesuai atau belum.
//    @Test
//    fun loadModules() {
//        val modules = MutableLiveData<ArrayList<ModuleEntity>>()
//        modules.value = modulesFake
//        `when`(mAcademyRepository.getAllModulesByCourse(courseEntity.courseId!!))
//            .thenReturn(modules)
//        val observer = mock(Observer::class.java)
//        SUT.getModules().observeForever(observer as Observer<in ArrayList<ModuleEntity>>)
//        verify(observer).onChanged(modulesFake)
//    }
}