package com.robby.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import com.robby.moviecatalogue.data.model.local.ContentEntity

interface LocalDataSource {
    fun getMovieDiscover(): LiveData<List<ContentEntity>>

    fun getTvDiscover(): LiveData<List<ContentEntity>>

    fun getMovieDetail(movieId: Int): LiveData<ContentEntity>

    fun getTvDetail(tvId: Int): LiveData<ContentEntity>

    fun searchMovies(query: String): LiveData<List<ContentEntity>>

    fun searchTvShows(query: String): LiveData<List<ContentEntity>>
}