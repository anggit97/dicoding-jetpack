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
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import com.anggit97.academy.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull


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
        }.`when`(remote).getAllCourses(any())

        val result = LiveDataTestUtil.getValue(academyRepository.getAllCourses())

        verify(
            remote,
            times(1)
        ).getAllCourses(any())

        assertNotNull(result)
        assertEquals(courseResponses.size, result.size)
    }
}
