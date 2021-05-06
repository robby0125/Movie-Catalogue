package com.robby.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource = INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllContents(): List<ContentEntity> = mMovieDao.getAllContents()

    fun getMovieDiscover(): DataSource.Factory<Int, ContentEntity> = mMovieDao.getMovieDiscover()

    fun getFavoriteMovies(): DataSource.Factory<Int, ContentEntity> = mMovieDao.getFavoriteMovies()

    fun getMovieDetail(movieId: Int): LiveData<ContentEntity> = mMovieDao.getMovieDetail(movieId)

    fun getTvDiscover(): DataSource.Factory<Int, ContentEntity> = mMovieDao.getTvDiscover()

    fun getFavoriteTvShows(): DataSource.Factory<Int, ContentEntity> =
        mMovieDao.getFavoriteTvShows()

    fun getTvDetail(tvId: Int): LiveData<ContentEntity> = mMovieDao.getTvShowDetail(tvId)

    fun insertListContent(contents: List<ContentEntity>) = mMovieDao.insertListContent(contents)

    fun insertContent(content: ContentEntity) = mMovieDao.insertContent(content)

    fun setContentFavorite(content: ContentEntity, newState: Boolean) {
        content.isFavorite = newState
        mMovieDao.updateContent(content)
    }
}