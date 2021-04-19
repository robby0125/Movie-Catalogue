package com.robby.moviecatalogue.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.LocalRepository
import com.robby.moviecatalogue.utils.ContentType

class SearchViewModel(private val repo: LocalRepository) : ViewModel() {

    private lateinit var query: String
    private lateinit var type: ContentType

    fun setQueryAndType(query: String, type: ContentType) {
        this.query = query
        this.type = type
    }

    fun getSearchResult(): LiveData<List<ContentEntity>> =
        if (type == ContentType.MOVIE) repo.searchMovies(query) else repo.searchTvShows(query)
}