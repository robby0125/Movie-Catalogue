package com.robby.moviecatalogue.ui.content

import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.Content
import com.robby.moviecatalogue.utils.DataDummy

class ContentsViewModel : ViewModel() {

    fun getMovies(): ArrayList<Content> = DataDummy.getDummyMovies()

    fun getTvShows(): ArrayList<Content> = DataDummy.getDummyTvShows()
}