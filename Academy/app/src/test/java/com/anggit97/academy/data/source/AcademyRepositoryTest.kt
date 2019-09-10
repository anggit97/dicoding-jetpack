package com.anggit97.academy.data.source


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.anggit97.academy.academy.FakeAcademyRepository
import com.anggit97.academy.data.source.local.LocalRepository
import com.anggit97.academy.data.source.local.entity.CourseEntity
import com.anggit97.academy.data.source.local.entity.CourseWithModule
import com.anggit97.academy.data.source.local.entity.ModuleEntity
import com.anggit97.academy.data.source.remote.RemoteRepository
import com.anggit97.academy.utils.DataDummy
import com.anggit97.academy.utils.InstantAppExecutors
import com.anggit97.academy.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


/**
 * Created by Anggit Prayogo on 2019-08-31.
 * Github : @anggit97
 */
class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteRepository::class.java)
    private val local = mock(LocalRepository::class.java)
    private val instantAppExecutors = mock(InstantAppExecutors::class.java)
    private val academyRepository = FakeAcademyRepository(remote, local, instantAppExecutors)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

//    @Test
//    fun getAllCourse() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
//                .onAllCourseRecieved(courseResponses)
//            return@doAnswer null
//        }.`when`(remote).getAllCoursesAsLiveData(any())
//
//        val result = LiveDataTestUtil.getValue(academyRepository.getAllCourses())
//
//        verify(
//            remote,
//            times(1)
//        ).getAllCoursesAsLiveData(any())
//
//        assertNotNull(result)
//        assertEquals(courseResponses.size, result.size)
//    }
//
//    @Test
//    fun getBookmarkCourse() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
//                .onAllCourseRecieved(courseResponses)
//            return@doAnswer null
//        }.`when`(remote).getAllCoursesAsLiveData(any())
//
//        val results = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())
//
//        verify(remote, times(1)).getAllCoursesAsLiveData(any())
//
//        assertNotNull(results)
//        assertEquals(courseResponses.size, results.size)
//    }
//
//    @Test
//    fun getAllModulesByCourse() {
//        doAnswer {
//            (it.arguments[1] as RemoteRepository.LoadModuleCallback)
//                .onAllModuleRecieved(moduleResponses)
//            null
//        }.`when`(remote).getModules(
//            eq2(courseId),
//            any()
//        )
//
//        val result = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))
//
//        verify(remote, times(1)).getModules(eq2(courseId), any())
//
//        assertNotNull(result)
//        assertEquals(moduleResponses.size, result.size)
//    }
//
//    @Test
//    fun getContent() {
//        doAnswer {
//            (it.arguments[1] as RemoteRepository.LoadModuleCallback)
//                .onAllModuleRecieved(moduleResponses)
//            null
//        }.`when`(remote).getModules(eq2(courseId), any())
//
//        doAnswer {
//            (it.arguments[1] as RemoteRepository.GetContentCallback)
//                .onContentRecieved(content)
//            null
//        }.`when`(remote).getContent(eq2(moduleId), any())
//
//        val resultContent =
//            LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId))
//
//
//        verify(remote, times(1))
//            .getModules(eq2(courseId), any())
//
//        verify(remote, times(1)).getContent(eq2(moduleId), any())
//
//        assertNotNull(resultContent)
//        assertNotNull(resultContent.contentEntity)
//        assertNotNull(resultContent.contentEntity?.content)
//        assertEquals(content.content, resultContent.contentEntity?.content)
//    }
//
//    @Test
//    fun getCourseWithModule() {
//        doAnswer {
//            (it.arguments[0] as RemoteRepository.LoadCourseCallback)
//                .onAllCourseRecieved(courseResponses)
//            return@doAnswer null
//        }.`when`(remote).getAllCoursesAsLiveData(any())
//
//        val result = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
//
//        verify(remote, times(1)).getAllCoursesAsLiveData(any())
//
//        assertNotNull(result)
//        assertEquals(courseResponses[0].title, result?.title)
//    }

    @Test
    fun getAllCourses() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = DataDummy.generateDummyCourses()

        `when`(local.getAllCourses()).thenReturn(dummyCourses)

        val result = LiveDataTestUtil.getValue(academyRepository.getAllCourses())

        verify(local).getAllCourses()
        assertNotNull(result.data)
        assertEquals(courseResponses.size, result.data?.size)
    }

    @Test
    fun getAllModulesByCourse() {
        val dummyModules = MutableLiveData<List<ModuleEntity>>()
        dummyModules.value = DataDummy.generateDummyModules(courseId)

        `when`(local.getAllModulesByCourse(courseId)).thenReturn(dummyModules)

        val result = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))

        verify(local).getAllModulesByCourse(courseId)
        assertNotNull(result.data)
        assertEquals(moduleResponses.size, result.data?.size)
    }

    @Test
    fun getBookmarkedCourses() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = DataDummy.generateDummyCourses()

        `when`(local.getBookmarkCourses()).thenReturn(dummyCourses)

        val result = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())

        verify(local).getBookmarkCourses()
        assertNotNull(result.data)
        assertEquals(courseResponses.size, result.data?.size)
    }

    @Test
    fun getContent() {
        val dummyEntity = MutableLiveData<ModuleEntity>()
        dummyEntity.value = DataDummy.generateDummyModuleWithContent(moduleId)

        `when`(local.getModuleWithContent(courseId)).thenReturn(dummyEntity)

        val result = LiveDataTestUtil.getValue(academyRepository.getContent(courseId))

        verify(local).getModuleWithContent(courseId)
        assertNotNull(result)

        assertNotNull(result.data)
        assertNotNull(result.data?.contentEntity)
        assertNotNull(result.data?.contentEntity?.content)
        assertEquals(content.content, result.data?.contentEntity?.content)
    }

    @Test
    fun getCourseWithModules() {
        val dummyEntity = MutableLiveData<CourseWithModule>()
        dummyEntity.value = DataDummy.generateDummyCourseWithModules(
            DataDummy.generateDummyCourses()[0], false
        )

        `when`(local.getCourseWithModules(courseId)).thenReturn(dummyEntity)

        val result = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))

        verify(local).getCourseWithModules(courseId)
        assertNotNull(result.data)
        assertNotNull(result.data?.courseEntity?.title)
        assertEquals(courseResponses[0].title, result.data?.courseEntity?.title)
    }
}
