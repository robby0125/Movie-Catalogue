package com.robby.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.utils.ContentType

class DetailViewModel(private val repo: MovieRepository) : ViewModel() {

    private val contentId = MutableLiveData<Int>()
    private var contentType: ContentType? = null
    private var query = ""

    fun setSelectedContentID(contentId: Int) {
        this.contentId.value = contentId
    }

    fun setContentType(contentType: ContentType) {
        this.contentType = contentType
    }

    fun setQuery(query: String) {
        this.query = query
    }

    var movieDetail: LiveData<ContentEntity> = Transformations.switchMap(contentId) {
        if (query.isEmpty()) repo.getMovieDetail(it)
        else repo.getMovieDetailWithQuery(it, query)
    }

    var tvShowDetail: LiveData<ContentEntity> = Transformations.switchMap(contentId) {
        if (query.isEmpty()) repo.getTvDetail(it)
        else repo.getTvDetailWithQuery(it, query)
    }

    fun setFavorite(): Boolean {
        val content =
            if (contentType == ContentType.MOVIE) movieDetail.value else tvShowDetail.value
        if (content != null) {
            if (!repo.isContentAvailable(content.id)) {
                repo.insertContent(content)
            }

            val newState = !content.isFavorite
            repo.setContentFavorite(content, newState)

            return newState
        }

        return false
    }
}