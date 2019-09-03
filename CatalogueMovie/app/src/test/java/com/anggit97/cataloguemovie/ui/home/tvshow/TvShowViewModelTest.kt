package com.anggit97.cataloguemovie.ui.home.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.FakeData
import com.anggit97.cataloguemovie.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Anggit Prayogo on 2019-08-27.
 * Github : @anggit97
 */
@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mMovieRepository: MovieRepository

    private lateinit var SUT: TvShowViewModel
    private val dummyTotalItem = 11

    @Before
    fun setUp() {
        SUT = TvShowViewModel(mMovieRepository)
    }

    /**
     * MovieViewModel Test Case :
     * - Periksa apakah list tv show tidak null
     * - Periksa apakah total list tv show berjumlah 11
     */
    @Test
    fun getTvShow_notNullTvShowList_returnTrue() {
        val dummyTvShows = FakeData.generateTvShow()
        val tvShows = MutableLiveData<ArrayList<ResultMovieEntity>>()
        tvShows.value = dummyTvShows

        `when`(mMovieRepository.getTvShows()).thenReturn(tvShows)

        val results = LiveDataTestUtil.getValue(mMovieRepository.getTvShows())

        verify(mMovieRepository).getTvShows()

        assertNotNull(results)

        assertEquals(dummyTvShows.size, results.size)
    }


    /**
     * TvShowViewModel Test Case :
     * - Periksa apakah observer mengamati adanya perubahan dari livedata
     */
    @Test
    fun getTvShow_notNullMutabelLivaData_returnTrue() {
        val dummyTvShows = FakeData.generateTvShow()
        val tvShows = MutableLiveData<ArrayList<ResultMovieEntity>>()
        tvShows.value = dummyTvShows

        `when`(mMovieRepository.getTvShows()).thenReturn(tvShows)

        val observer = mock(Observer::class.java)
        SUT.getTvShow().observeForever(observer as Observer<in ArrayList<ResultMovieEntity>>)

        verify(observer).onChanged(dummyTvShows)
    }
}