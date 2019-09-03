package com.anggit97.cataloguemovie.utils

import android.app.Application
import com.anggit97.cataloguemovie.data.remote.ResultMovieResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
class JsonHelper(val application: Application) {

    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = application.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): ArrayList<ResultMovieResponse> {
        val list = arrayListOf<ResultMovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("json/MovieResponse.json"))
            val listArray = responseObject.getJSONArray("results")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val id = movie.getString("id")
                val backdropPath = movie.getString("backdrop_path")
                val overview = movie.getString("overview")
                val popularity = movie.getString("popularity")
                val releaseDate = movie.getString("release_date")
                val title = movie.getString("title")
                val voteAverage = movie.getString("vote_average")
                val voteCount = movie.getString("vote_count")
                val genre = movie.getString("genre")

                val movieResponse = ResultMovieResponse(
                    backdropPath,
                    id,
                    overview,
                    popularity,
                    releaseDate,
                    title,
                    voteAverage,
                    voteCount,
                    genre
                )

                list.add(movieResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadTvShows(): ArrayList<ResultMovieResponse> {
        val list = arrayListOf<ResultMovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("json/TvShowResponse.json"))
            val listArray = responseObject.getJSONArray("results")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                val id = movie.getString("id")
                val backdropPath = movie.getString("backdrop_path")
                val overview = movie.getString("overview")
                val popularity = movie.getString("popularity")
                val releaseDate = movie.getString("release_date")
                val title = movie.getString("title")
                val voteAverage = movie.getString("vote_average")
                val voteCount = movie.getString("vote_count")
                val genre = movie.getString("genre")

                val movieResponse = ResultMovieResponse(
                    backdropPath,
                    id,
                    overview,
                    popularity,
                    releaseDate,
                    title,
                    voteAverage,
                    voteCount,
                    genre
                )

                list.add(movieResponse)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun loadDetailMovie(movieId: String): ResultMovieResponse? {
        var movieResult : ResultMovieResponse? = null
        try {
            val responseObject = JSONObject(parsingFileToString("json/MovieResponse.json"))
            val listArray = responseObject.getJSONArray("results")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                if (movie.getString("id") == movieId){
                    val id = movie.getString("id")
                    val backdropPath = movie.getString("backdrop_path")
                    val overview = movie.getString("overview")
                    val popularity = movie.getString("popularity")
                    val releaseDate = movie.getString("release_date")
                    val title = movie.getString("title")
                    val voteAverage = movie.getString("vote_average")
                    val voteCount = movie.getString("vote_count")
                    val genre = movie.getString("genre")

                    val movieResponse = ResultMovieResponse(
                        backdropPath,
                        id,
                        overview,
                        popularity,
                        releaseDate,
                        title,
                        voteAverage,
                        voteCount,
                        genre
                    )
                    movieResult = movieResponse
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return movieResult
    }

    fun loadDetailTvShow(tvShowId: String): ResultMovieResponse? {
        var movieResult : ResultMovieResponse? = null
        try {
            val responseObject = JSONObject(parsingFileToString("json/TvShowResponse.json"))
            val listArray = responseObject.getJSONArray("results")
            for (i in 0 until listArray.length()) {
                val movie = listArray.getJSONObject(i)

                if (movie.getString("id") == tvShowId){
                    val id = movie.getString("id")
                    val backdropPath = movie.getString("backdrop_path")
                    val overview = movie.getString("overview")
                    val popularity = movie.getString("popularity")
                    val releaseDate = movie.getString("release_date")
                    val title = movie.getString("title")
                    val voteAverage = movie.getString("vote_average")
                    val voteCount = movie.getString("vote_count")
                    val genre = movie.getString("genre")

                    val movieResponse = ResultMovieResponse(
                        backdropPath,
                        id,
                        overview,
                        popularity,
                        releaseDate,
                        title,
                        voteAverage,
                        voteCount,
                        genre
                    )
                    movieResult = movieResponse
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return movieResult
    }
}