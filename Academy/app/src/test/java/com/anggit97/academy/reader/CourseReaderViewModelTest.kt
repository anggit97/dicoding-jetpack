package com.anggit97.academy.reader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.academy.data.CourseEntity
import com.anggit97.academy.data.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Created by Anggit PRayogo on 2019-08-24.
 * github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class CourseReaderViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var academyRepository: AcademyRepository

    private var courseEntity = DataDummy.generateDummyCourses()[0]
    private var courseId = courseEntity.courseId
    private var modulesFake = DataDummy.generateDummyModules(courseId!!)
    private var moduleFake = modulesFake[0]
    private var moduleId = moduleFake.moduleId

    private var dummyModuleSize = 7

    private lateinit var SUT: CourseReaderViewModel

    @Before
    fun setUp() {
        SUT = CourseReaderViewModel(academyRepository)
        SUT.setCourseId(courseId!!)
    }

    //    CourseReaderViewModelTest:
//
//    Memuat Modules:
//
//    Memanipulasi data ketika pemanggilan data module di kelas repository.
//
//    Memastikan metode di kelas repository terpanggil.
//
//    Melakukan pengecekan data module apakah null atau tidak.
//
//    Melakukan pengecekan jumlah data module apakah sudah sesuai atau belum.
    @Test
    fun loadModules() {
        val modules = MutableLiveData<ArrayList<ModuleEntity>>()
        modules.value = modulesFake

        `when`(academyRepository.getAllModulesByCourse(courseId!!))
            .thenReturn(modules)
        val observer = mock(Observer::class.java)
        SUT.modules.observeForever(observer as Observer<in ArrayList<ModuleEntity>>)
        verify(observer).onChanged(modulesFake)
    }
//
//
//    //    Memuat Module yang dipilih:
////
////    Memanipulasi data ketika pemanggilan data content di kelas repository.
////
////    Memastikan metode di kelas repository terpanggil.
////
////    Melakukan pengecekan data content apakah null atau tidak.
////
////    Membandingkan data content sudah sesuai dengan yang diharapkan atau tidak.
//
    @Test
    fun loadSelectedModule() {
        val module = MutableLiveData<ModuleEntity>()
        module.value = moduleFake
        `when`(academyRepository.getContent(courseId!!, moduleId!!))
            .thenReturn(module)
        val observer = mock(Observer::class.java)
        SUT.setSelectedModule(moduleId!!)
        SUT.setCourseId(courseId!!)
        SUT.selectedModule.observeForever(observer as Observer<in ModuleEntity?>)
        verify(observer).onChanged(moduleFake)
    }
}

