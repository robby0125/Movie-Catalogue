package com.robby.moviecatalogue.data.source

import android.util.Log
import com.robby.moviecatalogue.data.api.ApiConfig
import com.robby.moviecatalogue.data.model.response.*
import com.robby.moviecatalogue.utils.EspressoIdlingResource
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
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getMovieDiscover()
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMovieDiscoverLoaded(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    EspressoIdlingResource.decrement()
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getTvDiscover(callback: LoadTvDiscoverCallback) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().getTvDiscover()
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvDiscoverLoaded(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    EspressoIdlingResource.decrement()
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
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
                    response.body()?.let { callback.onMovieGenreListLoaded(it.genres) }
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
                    response.body()?.let { callback.onTvShowGenreListLoaded(it.genres) }
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
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().searchMovies(query)
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onMoviesFound(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    EspressoIdlingResource.decrement()
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchTvShows(callback: SearchTvShowsCallback, query: String) {
        EspressoIdlingResource.increment()

        val client = ApiConfig.getApiService().searchTvShows(query)
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onTvShowsFound(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    EspressoIdlingResource.decrement()
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    interface LoadMovieDiscoverCallback {
        fun onMovieDiscoverLoaded(listMovieResponses: List<Movie>)
    }

    interface LoadTvDiscoverCallback {
        fun onTvDiscoverLoaded(listTvShowResponses: List<TvShow>)
    }

    interface LoadGenreListCallback {
        fun onMovieGenreListLoaded(listGenreReponses: List<GenresItem>)
        fun onTvShowGenreListLoaded(listGenreReponses: List<GenresItem>)
    }

    interface SearchMoviesCallback {
        fun onMoviesFound(listMovieResponses: List<Movie>)
    }

    interface SearchTvShowsCallback {
        fun onTvShowsFound(listTvShowResponses: List<TvShow>)
    }
}