package com.robby.moviecatalogue.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

	@field:SerializedName("results")
	val results: List<Movie>
)

data class Movie(

	@field:SerializedName("backdrop_path")
	val backdropPath: String,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("original_language")
	val originalLanguage: String,

	@field:SerializedName("original_title")
	val originalTitle: String,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("adult")
	val adult: Boolean,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String
)
