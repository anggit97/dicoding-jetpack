package com.anggit97.cataloguemovie.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anggit97.cataloguemovie.data.MovieRepository
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.utils.FakeDataTesting
import com.anggit97.cataloguemovie.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
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
class DetailMovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mMovieRepository: MovieRepository

    private lateinit var SUT: DetailMovieViewModel
    private var id = 1
    private var movieType = 1
    private var tvShowType = 2

    private lateinit var dummyMovieEntity: ResultMovieEntity
    private lateinit var dummyTvShowEntity: ResultMovieEntity

    @Before
    fun setUp() {
        SUT = DetailMovieViewModel(mMovieRepository)
        dummyMovieEntity = FakeDataTesting.generateMovies()[0]
        dummyTvShowEntity = FakeDataTesting.generateTvShow()[0]
    }

    /**
     * Load Movie Test Case
     * - Movie detail di movie repository terpanggil
     * - periksa apakah movie tidak null
     * - Periksa apakah nilai dari title movie sesuai dengan yang diharapkan
     */
    @Test
    fun getSelectedMovie_notNullResultMovie_trueReturned() {
        val movies = MutableLiveData<ResultMovieEntity>()
        movies.value = dummyMovieEntity

        `when`(mMovieRepository.getMovieDetail(movieType, dummyMovieEntity.id.toString()))
            .thenReturn(movies)

        val result = LiveDataTestUtil.getValue(
            mMovieRepository.getMovieDetail(
                movieType,
                dummyMovieEntity.id.toString()
            )
        )

        verify(mMovieRepository).getMovieDetail(movieType, dummyMovieEntity.id.toString())

        assertNotNull(result)

        assertEquals(dummyMovieEntity.title, result.title)
    }

    /**
     * Load TvShow Test Case
     * - TvShow detail di movie repository terpanggil
     * - periksa apakah tvshow tidak null
     * - Periksa apakah nilai dari title tvshow sesuai dengan yang diharapkan
     */
    @Test
    fun getSelectedTvShow_notNullResultTvShow_trueReturned() {
        val tvShow = MutableLiveData<ResultMovieEntity>()
        tvShow.value = dummyTvShowEntity

        `when`(mMovieRepository.getMovieDetail(tvShowType,dummyMovieEntity.id.toString()))
            .thenReturn(tvShow)

        val result = LiveDataTestUtil.getValue(
            mMovieRepository.getMovieDetail(
                tvShowType,
                dummyMovieEntity.id.toString()
            )
        )

        verify(mMovieRepository).getMovieDetail(tvShowType, dummyMovieEntity.id.toString())

        assertNotNull(result)

        assertEquals(dummyTvShowEntity.title, result.title)
    }

    /**
     * Load Live Data Movie Test Case
     * - Periksa apakah observer mengamati adanya perubahan dari livedata
     */
    @Test
    fun getSelectedMovie_liveDataMovie_trueReturned() {
        val movie = MutableLiveData<ResultMovieEntity>()
        movie.value = dummyMovieEntity

        `when`(mMovieRepository.getMovieDetail(movieType, dummyMovieEntity.id.toString()))
            .thenReturn(movie)

        val observer = mock(Observer::class.java)

        SUT.setMovieId(dummyMovieEntity.id!!)
        SUT.setMovieType(movieType)

        SUT.getSelectedMovie()!!.observeForever(observer as Observer<in ResultMovieEntity>)

        verify(observer).onChanged(dummyMovieEntity)
    }
}
