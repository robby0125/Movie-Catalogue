package com.robby.moviecatalogue.ui.detail

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.utils.ContentType
import com.robby.moviecatalogue.utils.DataDummy
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertNotNull

class DetailViewModelTest : Spek({
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
    val observer = mock<Observer<ContentEntity>>()

    val viewModel by memoized { DetailViewModel(movieRepository) }

    describe("Get movie detail") {
        val dummyMovie = DataDummy.getDummyMoviesAsContent().first()
        val dummyMovieId = dummyMovie.id
        val dummyMovieQuery = dummyMovie.title.split(" ").first()

        it("get movie detail with id") {
            val movieResult = MutableLiveData<ContentEntity>()
            movieResult.value = dummyMovie

            `when`(movieRepository.getMovieDetail(dummyMovieId)).thenReturn(movieResult)
            viewModel.setSelectedContentID(dummyMovieId)

            viewModel.movieDetail.observeForever(observer)
            verify(observer).onChanged(dummyMovie)
        }

        it("get movie detail with id and query") {
            val movieResult = MutableLiveData<ContentEntity>()
            movieResult.value = dummyMovie

            `when`(
                movieRepository.getMovieDetailWithQuery(
                    dummyMovieId,
                    dummyMovieQuery
                )
            ).thenReturn(movieResult)
            viewModel.setSelectedContentID(dummyMovieId)
            viewModel.setQuery(dummyMovieQuery)

            viewModel.movieDetail.observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyMovie)
        }
    }

    describe("Get tv show detail") {
        val dummyTvShow = DataDummy.getDummyTvShowsAsContent().first()
        val dummyTvShowId = dummyTvShow.id
        val dummyTvShowQuery = dummyTvShow.title.split(" ").first()

        it("get tv show detail with id") {
            val tvShowResult = MutableLiveData<ContentEntity>()
            tvShowResult.value = dummyTvShow

            `when`(movieRepository.getTvDetail(dummyTvShowId)).thenReturn(tvShowResult)
            viewModel.setSelectedContentID(dummyTvShowId)

            viewModel.tvShowDetail.observeForever(observer)
            verify(observer).onChanged(dummyTvShow)
        }

        it("get tv show detail with id and query") {
            val tvShowResult = MutableLiveData<ContentEntity>()
            tvShowResult.value = dummyTvShow

            `when`(
                movieRepository.getTvDetailWithQuery(
                    dummyTvShowId,
                    dummyTvShowQuery
                )
            ).thenReturn(tvShowResult)
            viewModel.setSelectedContentID(dummyTvShowId)
            viewModel.setQuery(dummyTvShowQuery)

            viewModel.tvShowDetail.observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyTvShow)
        }
    }

    describe("Check if setContentFavorite method is called") {
        it("movie must be not null and add movie favorite") {
            val dummyMovie = DataDummy.getDummyMoviesAsContent().first()
            val dummyMovieId = dummyMovie.id

            val movieResult = MutableLiveData<ContentEntity>()
            movieResult.value = dummyMovie

            `when`(movieRepository.getMovieDetail(dummyMovieId)).thenReturn(movieResult)
            viewModel.setSelectedContentID(dummyMovieId)
            viewModel.setContentType(ContentType.MOVIE)

            viewModel.movieDetail.observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyMovie)

            val movie = viewModel.movieDetail.value
            assertNotNull(movie)

            viewModel.setFavorite()
            verify(movieRepository, atLeastOnce()).setContentFavorite(any(), any())
        }

        it("tv show must be not null and add tv show favorite") {
            val dummyTvShow = DataDummy.getDummyTvShowsAsContent().first()
            val dummyTvShowId = dummyTvShow.id

            val tvShowResult = MutableLiveData<ContentEntity>()
            tvShowResult.value = dummyTvShow

            `when`(movieRepository.getTvDetail(dummyTvShowId)).thenReturn(tvShowResult)
            viewModel.setSelectedContentID(dummyTvShowId)
            viewModel.setContentType(ContentType.TV)

            viewModel.tvShowDetail.observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyTvShow)

            val tvShow = viewModel.tvShowDetail.value
            assertNotNull(tvShow)

            viewModel.setFavorite()
            verify(movieRepository, atLeastOnce()).setContentFavorite(any(), any())
        }
    }
})