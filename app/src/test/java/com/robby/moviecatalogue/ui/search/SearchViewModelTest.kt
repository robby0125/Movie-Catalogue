package com.robby.moviecatalogue.ui.search

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.LocalRepository
import com.robby.moviecatalogue.utils.ContentType
import com.robby.moviecatalogue.utils.DataDummy
import org.mockito.Mockito.`when`
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

    val localRepository = mock<LocalRepository>()
    val observer = mock<Observer<List<ContentEntity>>>()

    val viewModel by memoized { SearchViewModel(localRepository) }

    describe("Search movies and tv shows") {
        it("movies result must be not null and total item found is 1 item") {
            val dummyMovies = DataDummy.getDummyMoviesAsContent()
            val dummyMovieQuery = dummyMovies.first().title.split(" ").first()

            val movieResult = MutableLiveData<List<ContentEntity>>()
            movieResult.value = dummyMovies.filter { s -> s.title.contains(dummyMovieQuery, true) }

            `when`(localRepository.searchMovies(dummyMovieQuery)).thenReturn(movieResult)
            viewModel.setQueryAndType(dummyMovieQuery, ContentType.MOVIE)
            val movies = viewModel.getSearchResult().value
            verify(localRepository).searchMovies(dummyMovieQuery)
            assertNotNull(movies)
            assertEquals(1, movies.size)

            viewModel.getSearchResult().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(movieResult.value)
        }

        it("tv shows result must be not null and total item found is 1 item") {
            val dummyTvShows = DataDummy.getDummyTvShowsAsContent()
            val dummyTvShowQuery = dummyTvShows[1].title.split(" ").first()

            val tvShowResult = MutableLiveData<List<ContentEntity>>()
            tvShowResult.value =
                dummyTvShows.filter { s -> s.title.contains(dummyTvShowQuery, true) }

            `when`(localRepository.searchTvShows(dummyTvShowQuery)).thenReturn(tvShowResult)
            viewModel.setQueryAndType(dummyTvShowQuery, ContentType.TV)
            val tvShows = viewModel.getSearchResult().value
            verify(localRepository).searchTvShows(dummyTvShowQuery)
            assertNotNull(tvShows)
            assertEquals(1, tvShows.size)

            viewModel.getSearchResult().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(tvShowResult.value)
        }
    }
})