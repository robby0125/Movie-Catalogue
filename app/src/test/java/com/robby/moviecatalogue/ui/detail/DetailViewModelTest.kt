package com.robby.moviecatalogue.ui.detail

import com.robby.moviecatalogue.utils.DataDummy
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object DetailViewModelTest : Spek({
    val viewModel by memoized { DetailViewModel() }
    val dummyContent = DataDummy.getDummyMovies()[0]
    val contentId = dummyContent.id

    describe("Check equivalent content") {
        it("content from view model must be equal with dummy content") {
            viewModel.setSelectedContentID(contentId)
            val content = viewModel.getContent()
            assertEquals(dummyContent.id, content.id)
            assertEquals(dummyContent.title, content.title)
            assertEquals(dummyContent.overview, content.overview)
            assertEquals(dummyContent.release_date, content.release_date)
            assertEquals(dummyContent.genres, content.genres)
            assertEquals(dummyContent.status, content.status)
            assertEquals(dummyContent.rate, content.rate)
        }
    }
})