package com.robby.moviecatalogue.ui.content

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.LocalRepository
import com.robby.moviecatalogue.utils.DataDummy
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ContentsViewModelTest : Spek({
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

    val viewModel by memoized { ContentsViewModel(localRepository) }

    describe("Get dummy movie data") {
        it("data is not null and total data is same with dummy items") {
            val dummyMovies = DataDummy.getDummyMoviesAsContent()
            val movies = MutableLiveData<List<ContentEntity>>()
            movies.value = dummyMovies

            `when`(localRepository.getMovieDiscover()).thenReturn(movies)
            val movieDiscover = viewModel.getMovieDiscover().value
            verify(localRepository).getMovieDiscover()
            assertNotNull(movieDiscover)
            assertEquals(dummyMovies.size, movieDiscover.size)

            viewModel.getMovieDiscover().observeForever(observer)
            verify(observer).onChanged(dummyMovies)
        }
    }

    describe("Get dummy tv show data") {
        it("data is not null and total data is same with dummy items") {
            val dummyTvShows = DataDummy.getDummyTvShowsAsContent()
            val tvShows = MutableLiveData<List<ContentEntity>>()
            tvShows.value = dummyTvShows

            `when`(localRepository.getTvDiscover()).thenReturn(tvShows)
            val tvShowDiscover = viewModel.getTvDiscover().value
            verify(localRepository).getTvDiscover()
            assertNotNull(tvShowDiscover)
            assertEquals(dummyTvShows.size, tvShowDiscover.size)

            viewModel.getTvDiscover().observeForever(observer)
            verify(observer).onChanged(dummyTvShows)
        }
    }
})