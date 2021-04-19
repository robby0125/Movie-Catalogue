package com.robby.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.LocalRepository
import kotlin.properties.Delegates

class DetailViewModel(private val repo: LocalRepository) : ViewModel() {

    private var contentId by Delegates.notNull<Int>()

    fun setSelectedContentID(contentId: Int) {
        this.contentId = contentId
    }

    fun getMovieDetail(): LiveData<ContentEntity> = repo.getMovieDetail(contentId)

    fun getTvDetail(): LiveData<ContentEntity> = repo.getTvDetail(contentId)

    fun getMovieDetailWithQuery(): LiveData<ContentEntity> = repo.getMovieDetail(contentId)

    fun getTvDetailWithQuery(): LiveData<ContentEntity> = repo.getTvDetail(contentId)
}