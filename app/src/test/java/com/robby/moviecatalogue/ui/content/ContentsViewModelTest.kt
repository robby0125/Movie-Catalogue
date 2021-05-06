package com.robby.moviecatalogue.ui.content

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.vo.Resource
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

    val movieRepository = mock<MovieRepository>()
    val observer = mock<Observer<Resource<PagedList<ContentEntity>>>>()
    val pagedList = mock<PagedList<ContentEntity>>()

    val viewModel by memoized { ContentsViewModel(movieRepository) }

    describe("Get dummy movie data") {
        it("data is not null and total data is same with dummy items") {
            val dummyMovies = Resource.success(pagedList)
            `when`(dummyMovies.data?.size).thenReturn(5)
            val movies = MutableLiveData<Resource<PagedList<ContentEntity>>>()
            movies.value = dummyMovies

            `when`(movieRepository.getMovieDiscover()).thenReturn(movies)
            val movieDiscover = viewModel.getMovieDiscover().value
            verify(movieRepository).getMovieDiscover()
            assertNotNull(movieDiscover)
            assertEquals(dummyMovies.data?.size, movieDiscover.data?.size)

            viewModel.getMovieDiscover().observeForever(observer)
            verify(observer).onChanged(dummyMovies)
        }
    }

    describe("Get dummy tv show data") {
        it("data is not null and total data is same with dummy items") {
            val dummyTvShows = Resource.success(pagedList)
            `when`(dummyTvShows.data?.size).thenReturn(5)
            val tvShows = MutableLiveData<Resource<PagedList<ContentEntity>>>()
            tvShows.value = dummyTvShows

            `when`(movieRepository.getTvDiscover()).thenReturn(tvShows)
            val tvShowDiscover = viewModel.getTvDiscover().value
            verify(movieRepository).getTvDiscover()
            assertNotNull(tvShowDiscover)
            assertEquals(dummyTvShows.data?.size, tvShowDiscover.data?.size)

            viewModel.getTvDiscover().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyTvShows)
        }
    }
})