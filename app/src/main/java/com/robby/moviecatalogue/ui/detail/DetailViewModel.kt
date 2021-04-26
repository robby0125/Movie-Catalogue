package com.robby.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.MovieRepository
import kotlin.properties.Delegates

class DetailViewModel(private val repo: MovieRepository) : ViewModel() {

    private var contentId by Delegates.notNull<Int>()
    private var query = ""

    fun setSelectedContentID(contentId: Int) {
        this.contentId = contentId
    }

    fun setQuery(query: String) {
        this.query = query
    }

    fun getMovieDetail(): LiveData<ContentEntity> = repo.getMovieDetail(contentId)

    fun getTvDetail(): LiveData<ContentEntity> = repo.getTvDetail(contentId)

    fun getMovieDetailWithQuery(): LiveData<ContentEntity> =
        repo.getMovieDetailWithQuery(contentId, query)

    fun getTvDetailWithQuery(): LiveData<ContentEntity> =
        repo.getTvDetailWithQuery(contentId, query)
}