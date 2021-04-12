package com.robby.moviecatalogue.ui.search

import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.Content
import com.robby.moviecatalogue.utils.DataDummy

class SearchViewModel : ViewModel() {
    fun getContentResult(query: String, type: Int): ArrayList<Content> {
        val contentResult = ArrayList<Content>()

        val contents = if (type == 0) DataDummy.getDummyMovies() else DataDummy.getDummyTvShows()

        for (content in contents) {
            if (content.title.contains(query, true)) {
                contentResult.add(content)
            }
        }

        return contentResult
    }
}