package com.anggit97.cataloguemovie.data.local.entity


data class ResultMovieEntity(
    var backdropPath: String?,
    var id: Int?,
    var overview: String?,
    var popularity: Double?,
    var releaseDate: String?,
    var title: String?,
    var voteAverage: Double?,
    var voteCount: Int?,
    var genre: String?
)