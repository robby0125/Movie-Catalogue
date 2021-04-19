package com.robby.moviecatalogue.data.api

import com.robby.moviecatalogue.BuildConfig
import com.robby.moviecatalogue.data.model.response.GenreListResponse
import com.robby.moviecatalogue.data.model.response.MoviesResponse
import com.robby.moviecatalogue.data.model.response.TvShowsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getMovieDiscover(): Call<MoviesResponse>

    @GET("discover/tv?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTvDiscover(): Call<TvShowsResponse>

    @GET("genre/movie/list?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getMovieGenreList(): Call<GenreListResponse>

    @GET("genre/tv/list?api_key=${BuildConfig.TMDB_API_KEY}")
    fun getTvShowGenreList(): Call<GenreListResponse>

    @GET("search/movie?api_key=${BuildConfig.TMDB_API_KEY}")
    fun searchMovies(@Query("query") query: String): Call<MoviesResponse>

    @GET("search/tv?api_key=${BuildConfig.TMDB_API_KEY}")
    fun searchTvShows(@Query("query") query: String): Call<TvShowsResponse>
}