package com.robby.moviecatalogue.ui.content

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

object ContentsViewModelTest : Spek({
    val viewModel by memoized { ContentsViewModel() }

    describe("Get dummy movie data") {
        it("data is not null and total data is 10 items") {
            val movies = viewModel.getMovies()
            assertNotNull(movies)
            assertEquals(10, movies.size)
        }
    }

    describe("Get dummy tv show data") {
        it("data is not null and total data is 10 items") {
            val tvShows = viewModel.getTvShows()
            assertNotNull(tvShows)
            assertEquals(10, tvShows.size)
        }
    }

    describe("Check first movie id") {
        it("first movie id must be m01") {
            assertEquals("m01", viewModel.getMovies()[0].id)
        }
    }

    describe("Check first tv show id") {
        it("first movie id must be tv01") {
            assertEquals("tv01", viewModel.getTvShows()[0].id)
        }
    }
})