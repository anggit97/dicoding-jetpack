package com.anggit97.academy.data.source


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anggit97.academy.academy.FakeAcademyRepository
import com.anggit97.academy.data.source.remote.RemoteRepository
import com.anggit97.academy.utils.DataDummy
import com.nhaarman.mockitokotlin2.any
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.anggit97.academy.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.eq as eq2
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.mockito.Mockito.*


/**
 * Created by Anggit Prayogo on 2019-08-31.
 * Github : @anggit97
 */
class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteRepository::class.java)
    private val academyRepository = FakeAcademyRepository(remote)

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

    @Test
    fun getAllCourse() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCourseRecieved(courseResponses)
            return@doAnswer null
        }.`when`(remote).getAllCoursesAsLiveData(any())

        val result = LiveDataTestUtil.getValue(academyRepository.getAllCourses())

        verify(
            remote,
            times(1)
        ).getAllCoursesAsLiveData(any())

        assertNotNull(result)
        assertEquals(courseResponses.size, result.size)
    }

    @Test
    fun getBookmarkCourse() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCourseRecieved(courseResponses)
            return@doAnswer null
        }.`when`(remote).getAllCoursesAsLiveData(any())

        val results = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())

        verify(remote, times(1)).getAllCoursesAsLiveData(any())

        assertNotNull(results)
        assertEquals(courseResponses.size, results.size)
    }

    @Test
    fun getAllModulesByCourse() {
        doAnswer {
            (it.arguments[1] as RemoteRepository.LoadModuleCallback)
                .onAllModuleRecieved(moduleResponses)
            null
        }.`when`(remote).getModules(
            eq2(courseId),
            any()
        )

        val result = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))

        verify(remote, times(1)).getModules(eq2(courseId), any())

        assertNotNull(result)
        assertEquals(moduleResponses.size, result.size)
    }

    @Test
    fun getContent() {
        doAnswer {
            (it.arguments[1] as RemoteRepository.LoadModuleCallback)
                .onAllModuleRecieved(moduleResponses)
            null
        }.`when`(remote).getModules(eq2(courseId), any())

        doAnswer {
            (it.arguments[1] as RemoteRepository.GetContentCallback)
                .onContentRecieved(content)
            null
        }.`when`(remote).getContent(eq2(moduleId), any())

        val resultContent =
            LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId))


        verify(remote, times(1))
            .getModules(eq2(courseId), any())

        verify(remote, times(1)).getContent(eq2(moduleId), any())

        assertNotNull(resultContent)
        assertNotNull(resultContent.contentEntity)
        assertNotNull(resultContent.contentEntity?.content)
        assertEquals(content.content, resultContent.contentEntity?.content)
    }

    @Test
    fun getCourseWithModule() {
        doAnswer {
            (it.arguments[0] as RemoteRepository.LoadCourseCallback)
                .onAllCourseRecieved(courseResponses)
            return@doAnswer null
        }.`when`(remote).getAllCoursesAsLiveData(any())

        val result = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))

        verify(remote, times(1)).getAllCoursesAsLiveData(any())

        assertNotNull(result)
        assertEquals(courseResponses[0].title, result?.title)
    }
}
