package com.robby.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.vo.Resource

interface MovieDataSource {
    fun getMovieDiscover(): LiveData<Resource<PagedList<ContentEntity>>>

    fun getFavoriteMovies(): LiveData<PagedList<ContentEntity>>

    fun getTvDiscover(): LiveData<Resource<PagedList<ContentEntity>>>

    fun getFavoriteTvShows(): LiveData<PagedList<ContentEntity>>

    fun getMovieDetail(movieId: Int): LiveData<ContentEntity>

    fun getMovieDetailWithQuery(movieId: Int, query: String): LiveData<ContentEntity>

    fun getTvDetail(tvId: Int): LiveData<ContentEntity>

    fun getTvDetailWithQuery(tvId: Int, query: String): LiveData<ContentEntity>

    fun setContentFavorite(content: ContentEntity, state: Boolean)

    fun searchMovies(query: String): LiveData<PagedList<ContentEntity>>

    fun searchTvShows(query: String): LiveData<PagedList<ContentEntity>>

    fun isContentAvailable(contentId: Int): Boolean

    fun insertContent(content: ContentEntity)
}