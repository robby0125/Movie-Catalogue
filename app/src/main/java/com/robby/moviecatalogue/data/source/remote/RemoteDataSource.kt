package com.robby.moviecatalogue.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robby.moviecatalogue.data.source.remote.api.ApiConfig
import com.robby.moviecatalogue.data.source.remote.api.ApiResponse
import com.robby.moviecatalogue.data.source.remote.response.*
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

    fun getMovieDiscover(): LiveData<ApiResponse<List<Movie>>> {
        EspressoIdlingResource.increment()
        val movieResults = MutableLiveData<ApiResponse<List<Movie>>>()
        val client = ApiConfig.getApiService().getMovieDiscover()
        client.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResults.value = ApiResponse.success(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    movieResults.value = ApiResponse.empty(response.message(), emptyList())
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                movieResults.value =
                    ApiResponse.error(t.message ?: "Failed to Load Data!", emptyList())
                EspressoIdlingResource.decrement()
            }
        })
        return movieResults
    }

    fun getTvDiscover(): LiveData<ApiResponse<List<TvShow>>> {
        EspressoIdlingResource.increment()
        val tvResults = MutableLiveData<ApiResponse<List<TvShow>>>()
        val client = ApiConfig.getApiService().getTvDiscover()
        client.enqueue(object : Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { tvResults.value = ApiResponse.success(it.results) }
                    EspressoIdlingResource.decrement()
                } else {
                    tvResults.value = ApiResponse.empty(response.message(), emptyList())
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                tvResults.value =
                    ApiResponse.error(t.message ?: "Failed to Load Data!", emptyList())
                EspressoIdlingResource.decrement()
            }
        })
        return tvResults
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
                    Log.e(TAG, "onResponse: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
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
                    Log.e(TAG, "onResponse: ${response.message()}")
                    EspressoIdlingResource.decrement()
                }
            }

            override fun onFailure(call: Call<TvShowsResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
    }

    interface LoadGenreListCallback {
        fun onMovieGenreListLoaded(listGenreResponses: List<GenresItem>)
        fun onTvShowGenreListLoaded(listGenreResponses: List<GenresItem>)
    }

    interface SearchMoviesCallback {
        fun onMoviesFound(listMovieResponses: List<Movie>)
    }

    interface SearchTvShowsCallback {
        fun onTvShowsFound(listTvShowResponses: List<TvShow>)
    }
}