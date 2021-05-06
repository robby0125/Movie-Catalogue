package com.robby.moviecatalogue.ui.search

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.utils.ContentType
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SearchViewModelTest : Spek({
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean = true
        })
    }

    afterEachGroup { ArchTaskExecutor.getInstance().setDelegate(null) }

    val localRepository = mock<MovieRepository>()
    val observer = mock<Observer<List<ContentEntity>>>()
    val pagedList = mock<PagedList<ContentEntity>>()

    val viewModel by memoized { SearchViewModel(localRepository) }

    describe("Search movies and tv shows") {
        it("movies result must be not null and total item found is 1 item") {
            `when`(pagedList.size).thenReturn(1)

            val movieResult = MutableLiveData<PagedList<ContentEntity>>()
            movieResult.value = pagedList

            `when`(localRepository.searchMovies(anyString())).thenReturn(movieResult)
            viewModel.setQueryAndType(anyString(), ContentType.MOVIE)
            val movies = viewModel.getSearchResult().value
            verify(localRepository).searchMovies(any())
            assertNotNull(movies)
            assertEquals(1, movies.size)

            viewModel.getSearchResult().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(movieResult.value)
        }

        it("tv shows result must be not null and total item found is 1 item") {
            `when`(pagedList.size).thenReturn(1)

            val tvShowResult = MutableLiveData<PagedList<ContentEntity>>()
            tvShowResult.value = pagedList

            `when`(localRepository.searchTvShows(anyString())).thenReturn(tvShowResult)
            viewModel.setQueryAndType(anyString(), ContentType.TV)
            val tvShows = viewModel.getSearchResult().value
            verify(localRepository).searchTvShows(any())
            assertNotNull(tvShows)
            assertEquals(1, tvShows.size)

            viewModel.getSearchResult().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(tvShowResult.value)
        }
    }
})