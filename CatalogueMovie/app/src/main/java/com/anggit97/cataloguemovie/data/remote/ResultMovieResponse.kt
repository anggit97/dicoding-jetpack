package com.anggit97.cataloguemovie.data.remote

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
data class ResultMovieResponse(
    var backdropPath: String?,
    var id: String?,
    var overview: String?,
    var popularity: String?,
    var releaseDate: String?,
    var title: String?,
    var voteAverage: String?,
    var voteCount: String?,
    var genre: String?
)