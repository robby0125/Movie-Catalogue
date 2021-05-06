package com.robby.moviecatalogue.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.vo.Resource

class ContentsViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMovieDiscover(): LiveData<Resource<PagedList<ContentEntity>>> = repo.getMovieDiscover()

    fun getFavoriteMovies(): LiveData<PagedList<ContentEntity>> = repo.getFavoriteMovies()

    fun getTvDiscover(): LiveData<Resource<PagedList<ContentEntity>>> = repo.getTvDiscover()

    fun getFavoriteTvShows(): LiveData<PagedList<ContentEntity>> = repo.getFavoriteTvShows()
}