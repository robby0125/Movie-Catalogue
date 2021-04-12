package com.robby.moviecatalogue.data

data class Content(
    val id: String,
    val title: String,
    val overview: String,
    val release_date: String,
    val genres: String,
    val status: String,
    val rate: String,
    val poster_path: String,
    val backdrop_path: String
)
