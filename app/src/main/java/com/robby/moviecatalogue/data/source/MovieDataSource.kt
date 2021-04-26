package com.robby.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.robby.moviecatalogue.data.model.local.ContentEntity

interface MovieDataSource {
    fun getMovieDiscover(): LiveData<List<ContentEntity>>

    fun getTvDiscover(): LiveData<List<ContentEntity>>

    fun getMovieDetail(movieId: Int): LiveData<ContentEntity>

    fun getMovieDetailWithQuery(movieId: Int, query: String): LiveData<ContentEntity>

    fun getTvDetail(tvId: Int): LiveData<ContentEntity>

    fun getTvDetailWithQuery(tvId: Int, query: String): LiveData<ContentEntity>

    fun searchMovies(query: String): LiveData<List<ContentEntity>>

    fun searchTvShows(query: String): LiveData<List<ContentEntity>>
}