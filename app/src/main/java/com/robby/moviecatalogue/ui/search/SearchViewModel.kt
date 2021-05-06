package com.robby.moviecatalogue.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.utils.ContentType

class SearchViewModel(private val repo: MovieRepository) : ViewModel() {

    private lateinit var query: String
    private lateinit var type: ContentType

    fun setQueryAndType(query: String, type: ContentType) {
        this.query = query
        this.type = type
    }

    fun getSearchResult(): LiveData<PagedList<ContentEntity>> =
        if (type == ContentType.MOVIE) repo.searchMovies(query) else repo.searchTvShows(query)
}