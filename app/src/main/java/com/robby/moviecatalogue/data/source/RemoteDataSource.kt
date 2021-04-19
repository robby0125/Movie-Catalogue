package com.robby.moviecatalogue.data.source

import android.util.Log
import com.robby.moviecatalogue.data.api.ApiConfig
import com.robby.moviecatalogue.data.model.response.GenreListResponse
import com.robby.moviecatalogue.data.model.response.MoviesResponse
import com.robby.moviecatalogue.data.model.response.TvShowsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    companion object {
        private val TAG = RemoteDataSource::class.java.simpleName

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource = instance ?: synchronized(this) {
            instance ?: RemoteDataSource().apply { instance = this }
        }
    }

    fun getMovieDiscover(callback: LoadMovieDiscoverCallback) {
        val client = ApiConfig.getApiService().getMovieDiscover()
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMovieDiscoverLoaded(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTvDiscover(callback: LoadTvDiscoverCallback) {
        val client = ApiConfig.getApiService().getTvDiscover()
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvDiscoverLoaded(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getGenreList(callback: LoadGenreListCallback) {
        val movieClient = ApiConfig.getApiService().getMovieGenreList()
        movieClient.enqueue(object : Callback<GenreListResponse> {
            override fun onResponse(
                call: Call<GenreListResponse>,
                response: Response<GenreListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMovieGenreListLoaded(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

        val tvClient = ApiConfig.getApiService().getTvShowGenreList()
        tvClient.enqueue(object : Callback<GenreListResponse> {
            override fun onResponse(
                call: Call<GenreListResponse>,
                response: Response<GenreListResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvShowGenreListLoaded(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GenreListResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchMovies(callback: SearchMoviesCallback, query: String) {
        val client = ApiConfig.getApiService().searchMovies(query)
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMoviesFound(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchTvShows(callback: SearchTvShowsCallback, query: String) {
        val client = ApiConfig.getApiService().searchTvShows(query)
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvShowsFound(it) }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    interface LoadMovieDiscoverCallback {
        fun onMovieDiscoverLoaded(moviesResponse: MoviesResponse)
    }

    interface LoadTvDiscoverCallback {
        fun onTvDiscoverLoaded(tvShowsResponse: TvShowsResponse)
    }

    interface LoadGenreListCallback {
        fun onMovieGenreListLoaded(genreListResponse: GenreListResponse)
        fun onTvShowGenreListLoaded(genreListResponse: GenreListResponse)
    }

    interface SearchMoviesCallback {
        fun onMoviesFound(moviesResponse: MoviesResponse)
    }

    interface SearchTvShowsCallback {
        fun onTvShowsFound(tvShowsResponse: TvShowsResponse)
    }
}