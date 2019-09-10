package com.anggit97.academy.reader

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.AcademyRepository
import com.anggit97.academy.utils.DataDummy
import com.anggit97.academy.vo.Resource
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Rule
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
    private var modulesFake = DataDummy.generateDummyModules(courseId)
    private var moduleFake = modulesFake[0]
    private var moduleId = moduleFake.moduleId

    private var dummyModuleSize = 7

    private lateinit var SUT: CourseReaderViewModel

    @Before
    fun setUp() {
        SUT = CourseReaderViewModel(academyRepository)
        SUT.setCourseId(courseId)
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
        val resource = Resource.success(modulesFake)
        val modules = MutableLiveData<Resource<List<ModuleEntity>>>()
        modules.value = resource

        `when`(academyRepository.getAllModulesByCourse(courseId))
            .thenReturn(modules)
        val observer = mock(Observer::class.java)

        SUT.modules.observeForever(observer as Observer<in Resource<List<ModuleEntity>>>)
        verify(observer).onChanged(resource)
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
        val resource = Resource.success(moduleFake)
        val module = MutableLiveData<Resource<ModuleEntity>>()
        module.value = resource
        `when`(academyRepository.getContent(moduleId))
            .thenReturn(module)
        val observer = mock(Observer::class.java)
        SUT.setSelectedModule(moduleId)
        SUT.setCourseId(courseId)
        SUT.selectedModule.observeForever(observer as Observer<in Resource<ModuleEntity>>)
        verify(observer).onChanged(resource)
    }
}

