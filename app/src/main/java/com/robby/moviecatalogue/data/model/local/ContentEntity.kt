package com.robby.moviecatalogue.data.model.local

data class ContentEntity(
    val id: Int,
    val title: String,
    val genre: String,
    val releaseDate: String,
    val voteAverage: Double,
    val originalLanguage: String,
    val overview: String,
    val posterPath: String,
    val backdropPath: String,
)
