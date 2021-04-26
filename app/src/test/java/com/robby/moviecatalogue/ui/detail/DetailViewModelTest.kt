package com.robby.moviecatalogue.ui.detail

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.source.MovieRepository
import com.robby.moviecatalogue.utils.DataDummy
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
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

    val localRepository = mock<MovieRepository>()
    val observer = mock<Observer<ContentEntity>>()

    val viewModel by memoized { DetailViewModel(localRepository) }

    describe("Get movie detail") {
        val dummyMovie = DataDummy.getDummyMoviesAsContent().first()
        val dummyMovieId = dummyMovie.id
        val dummyMovieQuery = dummyMovie.title.split(" ").first()

        it("get movie detail with id, content value must be not null and equal with dummy") {
            val movieResult = MutableLiveData<ContentEntity>()
            movieResult.value = dummyMovie

            `when`(localRepository.getMovieDetail(dummyMovieId)).thenReturn(movieResult)
            viewModel.setSelectedContentID(dummyMovieId)
            val movie = viewModel.getMovieDetail().value
            verify(localRepository).getMovieDetail(dummyMovieId)
            assertNotNull(movie)
            assertEquals(dummyMovie.id, movie.id)
            assertEquals(dummyMovie.title, movie.title)
            assertEquals(dummyMovie.genre, movie.genre)
            assertEquals(dummyMovie.releaseDate, movie.releaseDate)
            assertEquals(dummyMovie.voteAverage, movie.voteAverage)
            assertEquals(dummyMovie.originalLanguage, movie.originalLanguage)
            assertEquals(dummyMovie.overview, movie.overview)

            viewModel.getMovieDetail().observeForever(observer)
            verify(observer).onChanged(dummyMovie)
        }

        it("get movie detail with id and query, content value must be not null and equal with dummy") {
            val movieResult = MutableLiveData<ContentEntity>()
            movieResult.value = dummyMovie

            `when`(
                localRepository.getMovieDetailWithQuery(
                    dummyMovieId,
                    dummyMovieQuery
                )
            ).thenReturn(movieResult)
            viewModel.setSelectedContentID(dummyMovieId)
            viewModel.setQuery(dummyMovieQuery)
            val movie = viewModel.getMovieDetailWithQuery().value
            verify(localRepository).getMovieDetailWithQuery(dummyMovieId, dummyMovieQuery)
            assertNotNull(movie)
            assertEquals(dummyMovie.id, movie.id)
            assertEquals(dummyMovie.title, movie.title)
            assertEquals(dummyMovie.genre, movie.genre)
            assertEquals(dummyMovie.releaseDate, movie.releaseDate)
            assertEquals(dummyMovie.voteAverage, movie.voteAverage)
            assertEquals(dummyMovie.originalLanguage, movie.originalLanguage)
            assertEquals(dummyMovie.overview, movie.overview)

            viewModel.getMovieDetailWithQuery().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyMovie)
        }
    }

    describe("Get tv show detail") {
        val dummyTvShow = DataDummy.getDummyTvShowsAsContent().first()
        val dummyTvShowId = dummyTvShow.id
        val dummyTvShowQuery = dummyTvShow.title.split(" ").first()

        it("get tv show detail with id, content value must be not null and equal with dummy") {
            val tvShowResult = MutableLiveData<ContentEntity>()
            tvShowResult.value = dummyTvShow

            `when`(localRepository.getTvDetail(dummyTvShowId)).thenReturn(tvShowResult)
            viewModel.setSelectedContentID(dummyTvShowId)
            val tvShow = viewModel.getTvDetail().value
            verify(localRepository).getTvDetail(dummyTvShowId)
            assertNotNull(tvShow)
            assertEquals(dummyTvShow.id, tvShow.id)
            assertEquals(dummyTvShow.title, tvShow.title)
            assertEquals(dummyTvShow.genre, tvShow.genre)
            assertEquals(dummyTvShow.releaseDate, tvShow.releaseDate)
            assertEquals(dummyTvShow.voteAverage, tvShow.voteAverage)
            assertEquals(dummyTvShow.originalLanguage, tvShow.originalLanguage)
            assertEquals(dummyTvShow.overview, tvShow.overview)

            viewModel.getTvDetail().observeForever(observer)
            verify(observer).onChanged(dummyTvShow)
        }

        it("get tv show detail with id and query, content value must be not null and equal with dummy") {
            val tvShowResult = MutableLiveData<ContentEntity>()
            tvShowResult.value = dummyTvShow

            `when`(
                localRepository.getTvDetailWithQuery(
                    dummyTvShowId,
                    dummyTvShowQuery
                )
            ).thenReturn(tvShowResult)
            viewModel.setSelectedContentID(dummyTvShowId)
            viewModel.setQuery(dummyTvShowQuery)
            val tvShow = viewModel.getTvDetailWithQuery().value
            verify(localRepository).getTvDetailWithQuery(dummyTvShowId, dummyTvShowQuery)
            assertNotNull(tvShow)
            assertEquals(dummyTvShow.id, tvShow.id)
            assertEquals(dummyTvShow.title, tvShow.title)
            assertEquals(dummyTvShow.genre, tvShow.genre)
            assertEquals(dummyTvShow.releaseDate, tvShow.releaseDate)
            assertEquals(dummyTvShow.voteAverage, tvShow.voteAverage)
            assertEquals(dummyTvShow.originalLanguage, tvShow.originalLanguage)
            assertEquals(dummyTvShow.overview, tvShow.overview)

            viewModel.getTvDetailWithQuery().observeForever(observer)
            verify(observer, atLeastOnce()).onChanged(dummyTvShow)
        }
    }
})