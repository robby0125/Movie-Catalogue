package com.robby.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM contents")
    fun getAllContents(): List<ContentEntity>

    @Query("SELECT * FROM contents WHERE type='MOVIE'")
    fun getMovieDiscover(): DataSource.Factory<Int, ContentEntity>

    @Query("SELECT * FROM contents WHERE type = 'MOVIE' AND isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, ContentEntity>

    @Query("SELECT * FROM contents WHERE type = 'MOVIE' AND id = :movieId")
    fun getMovieDetail(movieId: Int): LiveData<ContentEntity>

    @Query("SELECT * FROM contents WHERE type = 'TV'")
    fun getTvDiscover(): DataSource.Factory<Int, ContentEntity>

    @Query("SELECT * FROM contents WHERE type = 'TV' AND isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, ContentEntity>

    @Query("SELECT * FROM contents WHERE type = 'TV' AND id = :tvId")
    fun getTvShowDetail(tvId: Int): LiveData<ContentEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListContent(contents: List<ContentEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertContent(content: ContentEntity)

    @Update
    fun updateContent(content: ContentEntity)
}