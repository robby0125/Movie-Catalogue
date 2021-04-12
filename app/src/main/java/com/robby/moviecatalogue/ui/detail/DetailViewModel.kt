package com.robby.moviecatalogue.ui.detail

import androidx.lifecycle.ViewModel
import com.robby.moviecatalogue.data.Content
import com.robby.moviecatalogue.utils.DataDummy

class DetailViewModel : ViewModel() {

    private lateinit var contentId: String

    fun setSelectedContentID(contentId: String) {
        this.contentId = contentId
    }

    fun getContent(): Content {
        lateinit var selectedContent: Content

        val allContents = DataDummy.getAllDummyContents()

        for (content in allContents) {
            if (content.id == contentId) {
                selectedContent = content
                break
            }
        }

        return selectedContent
    }
}