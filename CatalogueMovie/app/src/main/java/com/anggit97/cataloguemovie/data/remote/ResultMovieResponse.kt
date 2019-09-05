package com.anggit97.cataloguemovie.data.remote

import com.google.gson.annotations.SerializedName

/**
 * Created by Anggit Prayogo on 2019-09-02.
 * Github : @anggit97
 */
data class ResultMovieResponse(
    @SerializedName("poster_path")
    var backdropPath: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("overview")
    var overview: String?,
    @SerializedName("popularity")
    var popularity: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("vote_average")
    var voteAverage: String?,
    @SerializedName("vote_count")
    var voteCount: String?,
    @SerializedName("release_date")
    var genre: String?
)