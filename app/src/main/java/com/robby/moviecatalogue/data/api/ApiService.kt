package com.robby.moviecatalogue.data.api

import com.robby.moviecatalogue.BuildConfig
import com.robby.moviecatalogue.data.model.response.GenreListResponse
import com.robby.moviecatalogue.data.model.response.MoviesResponse
import com.robby.moviecatalogue.data.model.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getMovieDiscover(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<MoviesResponse>

    @GET("discover/tv")
    fun getTvDiscover(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<TvShowsResponse>

    @GET("genre/movie/list")
    fun getMovieGenreList(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<GenreListResponse>

    @GET("genre/tv/list")
    fun getTvShowGenreList(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<GenreListResponse>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<MoviesResponse>

    @GET("search/tv")
    fun searchTvShows(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Call<TvShowsResponse>
}