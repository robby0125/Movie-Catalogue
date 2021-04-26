package com.robby.moviecatalogue.ui.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.MovieRepository

class ContentsViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getMovieDiscover(): LiveData<List<ContentEntity>> = repo.getMovieDiscover()

    fun getTvDiscover(): LiveData<List<ContentEntity>> = repo.getTvDiscover()
}