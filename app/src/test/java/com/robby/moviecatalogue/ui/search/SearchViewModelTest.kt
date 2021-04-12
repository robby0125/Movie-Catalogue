package com.robby.moviecatalogue.ui.search

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object SearchViewModelTest : Spek({
    val viewModel by memoized { SearchViewModel() }

    describe("Search with result") {
        it("search result must be not null and not empty") {
            val result = viewModel.getContentResult("Attack on Titan", 1)
            assertEquals(1, result.size)
        }
    }

    describe("Search with no result") {
        it("search result must be empty") {
            val result = viewModel.getContentResult("No Result Content", 0)
            assertEquals(0, result.size)
        }
    }
})