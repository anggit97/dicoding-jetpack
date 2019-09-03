package com.anggit97.cataloguemovie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anggit97.cataloguemovie.data.local.entity.ResultMovieEntity
import com.anggit97.cataloguemovie.data.remote.RemoteMovieRepository
import com.anggit97.cataloguemovie.data.remote.ResultMovieResponse
import com.anggit97.cataloguemovie.utils.EspressoIdlingResource

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
class MovieRepository(
    private val remoteMovieRepository: RemoteMovieRepository
) : MovieDataSource {

    override fun getMovies(): LiveData<ArrayList<ResultMovieEntity>> {
        EspressoIdlingResource.increment()
        val movieResult = MutableLiveData<ArrayList<ResultMovieEntity>>()
        remoteMovieRepository.getAllMovies(object : RemoteMovieRepository.LoadMovieCallback {
            override fun onAllMovieRecieved(movieResponses: ArrayList<ResultMovieResponse>) {
                EspressoIdlingResource.decrement()
                val movieList = arrayListOf<ResultMovieEntity>()
                for (item in movieResponses) {
                    val movie = ResultMovieEntity(
                        item.backdropPath,
                        item.id?.toInt(),
                        item.overview,
                        item.popularity?.toDouble(),
                        item.releaseDate,
                        item.title,
                        item.voteAverage?.toDouble(),
                        item.voteCount?.toInt(),
                        item.genre
                    )
                    movieList.add(movie)
                }
                movieResult.postValue(movieList)
            }

            override fun onDataNotAvailable() {

            }
        })
        return movieResult
    }

    override fun getTvShows(): LiveData<ArrayList<ResultMovieEntity>> {
        EspressoIdlingResource.increment()
        val tvMovieResult = MutableLiveData<ArrayList<ResultMovieEntity>>()
        remoteMovieRepository.getAllTvShows(object : RemoteMovieRepository.LoadTvShowCallback{
            override fun onAllTvShowRecieved(tvShowResponses: ArrayList<ResultMovieResponse>) {
                EspressoIdlingResource.decrement()
                val tvShowList = arrayListOf<ResultMovieEntity>()
                for (item in tvShowResponses){
                    val tvShow = ResultMovieEntity(
                        item.backdropPath,
                        item.id?.toInt(),
                        item.overview,
                        item.popularity?.toDouble(),
                        item.releaseDate,
                        item.title,
                        item.voteAverage?.toDouble(),
                        item.voteCount?.toInt(),
                        item.genre
                    )
                    tvShowList.add(tvShow)
                }
                tvMovieResult.postValue(tvShowList)
            }

            override fun onDataNotAvailable() {

            }
        })
        return tvMovieResult
    }

    override fun getMovieDetail(movieType: Int, movieId: String): LiveData<ResultMovieEntity> {
        EspressoIdlingResource.increment()
        val detailMovieresult =  MutableLiveData<ResultMovieEntity>()
        if (movieType == 1){
            remoteMovieRepository.getDetailMovie(movieId, object: RemoteMovieRepository.LoadDetailMovieCallback{
                override fun onDetailMovieRecieved(detailMovie: ResultMovieResponse?) {
                    EspressoIdlingResource.decrement()
                    var movie : ResultMovieEntity? = null
                    with(detailMovie){
                        this?.let {
                            movie = ResultMovieEntity(
                                backdropPath,
                                id?.toInt(),
                                overview,
                                popularity?.toDouble(),
                                releaseDate,
                                title,
                                voteAverage?.toDouble(),
                                voteCount?.toInt(),
                                genre
                            )
                        }
                    }
                    detailMovieresult.postValue(movie)
                }

                override fun onDataNotAvailable() {

                }
            })
        }else{
            remoteMovieRepository.getDetailTvShow(movieId, object: RemoteMovieRepository.LoadDetailMovieCallback{
                override fun onDetailMovieRecieved(detailMovie: ResultMovieResponse?) {
                    EspressoIdlingResource.decrement()
                    var movie : ResultMovieEntity? = null
                    with(detailMovie){
                        this?.let {
                            movie = ResultMovieEntity(
                                backdropPath,
                                id?.toInt(),
                                overview,
                                popularity?.toDouble(),
                                releaseDate,
                                title,
                                voteAverage?.toDouble(),
                                voteCount?.toInt(),
                                genre
                            )
                        }
                    }
                    detailMovieresult.postValue(movie)
                }

                override fun onDataNotAvailable() {

                }
            })
        }
        return detailMovieresult
    }

    companion object {

        @Volatile
        private var INSTANCE: MovieRepository? = null

        fun getInstance(remoteData: RemoteMovieRepository): MovieRepository? {
            if (INSTANCE == null) {
                synchronized(MovieRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieRepository(remoteData)
                    }
                }
            }
            return INSTANCE
        }
    }
}
