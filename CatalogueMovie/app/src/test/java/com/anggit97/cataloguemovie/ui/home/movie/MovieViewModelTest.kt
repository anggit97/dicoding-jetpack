package com.anggit97.cataloguemovie.ui.home.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.FakeData
import com.anggit97.cataloguemovie.utils.LiveDataTestUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mMovieRepository: MovieRepository

    private lateinit var SUT: MovieViewModel

    @Before
    fun setUp() {
        SUT = MovieViewModel(mMovieRepository)
    }

    /**
     * MovieViewModel Test Case :
     * - Periksa apakah observer mengamati adanya perubahan dari livedata
     */
    @Test
    fun getMovies_notNullMutabelLivaData_returnTrue() {
        val dummyMovieList = FakeData.generateMovies()
        val movies = MutableLiveData<ArrayList<ResultMovieEntity>>()
        movies.value = dummyMovieList

        `when`(mMovieRepository.getMovies()).thenReturn(movies)

        val observer = mock(Observer::class.java)

        SUT.getMovies().observeForever(observer as Observer<in ArrayList<ResultMovieEntity>>)

        verify(observer).onChanged(dummyMovieList)
    }


    /**
     * MovieViewModel Test Case :
     * - Periksa apakah list movie tidak null
     * - Periksa apakah total list movie berjumlah 10
     */
    @Test
    fun getMovies_notNullMovieList_returnTrue() {
        val dummyMovieList = FakeData.generateMovies()
        val movies = MutableLiveData<ArrayList<ResultMovieEntity>>()
        movies.value = dummyMovieList

        `when`(mMovieRepository.getMovies()).thenReturn(movies)

        val results = LiveDataTestUtil.getValue(mMovieRepository.getMovies())

        verify(mMovieRepository).getMovies()

        assertNotNull(results)

        assertEquals(dummyMovieList.size, results.size)
    }
}
