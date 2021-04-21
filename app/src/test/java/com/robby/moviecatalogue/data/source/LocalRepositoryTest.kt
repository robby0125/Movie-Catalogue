package com.robby.moviecatalogue.data.source

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.nhaarman.mockitokotlin2.*
import com.robby.moviecatalogue.utils.DataDummy
import com.robby.moviecatalogue.utils.LiveDataTestUtil
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class LocalRepositoryTest : Spek({
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

    val remoteDataSource = mock(RemoteDataSource::class.java)

    val localRepository by memoized { FakeLocalRepository(remoteDataSource) }

    val dummyMovieResponses = DataDummy.getDummyMovies()
    val dummyMovieId = dummyMovieResponses.first().id
    val dummyMovieQuery = dummyMovieResponses.first().title.split(" ").first()
    val dummySearchMovieResult = dummyMovieResponses.filter { s ->
        s.title.contains(dummyMovieQuery, true)
    }

    val dummyTvShowResponses = DataDummy.getDummyTvShows()
    val dummyTvId = dummyTvShowResponses.first().id
    val dummyTvQuery = dummyTvShowResponses.first().name.split(" ").first()
    val dummySearchTvShowResult = dummyTvShowResponses.filter { s ->
        s.name.contains(dummyTvQuery, true)
    }

    group("Get content discover") {
        describe("Get all movies test") {
            it("retrieve all movies then check if results is not null and results size equals with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.LoadMovieDiscoverCallback).onMovieDiscoverLoaded(
                        dummyMovieResponses
                    )
                    null
                }.`when`(remoteDataSource).getMovieDiscover(any())
                val movieList = LiveDataTestUtil.getValue(localRepository.getMovieDiscover())
                verify(remoteDataSource).getMovieDiscover(any())
                assertNotNull(movieList)
                assertEquals(dummyMovieResponses.size, movieList.size)
            }
        }

        describe("Get all tv shows test") {
            it("retrieve all tv shows then check if results is not null and results size equals with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.LoadTvDiscoverCallback).onTvDiscoverLoaded(
                        dummyTvShowResponses
                    )
                    null
                }.`when`(remoteDataSource).getTvDiscover(any())
                val tvShowList = LiveDataTestUtil.getValue(localRepository.getTvDiscover())
                verify(remoteDataSource).getTvDiscover(any())
                assertNotNull(tvShowList)
                assertEquals(dummyTvShowResponses.size, tvShowList.size)
            }
        }
    }

    group("Get content detail") {
        describe("Get movie detail at first index") {
            it("result must be not null and check if movie title is the same with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.LoadMovieDiscoverCallback).onMovieDiscoverLoaded(
                        dummyMovieResponses
                    )
                    null
                }.`when`(remoteDataSource).getMovieDiscover(any())
                val movie = LiveDataTestUtil.getValue(localRepository.getMovieDetail(dummyMovieId))
                verify(remoteDataSource, atLeastOnce()).getMovieDiscover(any())
                assertNotNull(movie)
                assertEquals(dummyMovieResponses.first().title, movie.title)
            }
        }

        describe("Get movie detail at first index with query") {
            it("result must be not null and check if movie title is the same with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.SearchMoviesCallback).onMoviesFound(
                        dummySearchMovieResult
                    )
                    null
                }.`when`(remoteDataSource).searchMovies(any(), eq(dummyMovieQuery))
                val movie = LiveDataTestUtil.getValue(
                    localRepository.getMovieDetailWithQuery(
                        dummyMovieId,
                        dummyMovieQuery
                    )
                )
                verify(remoteDataSource).searchMovies(any(), eq(dummyMovieQuery))
                assertNotNull(movie)
                assertEquals(dummySearchMovieResult.first().title, movie.title)
            }
        }

        describe("Get tv detail at first index") {
            it("result must be not null and check if tv title is the same with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.LoadTvDiscoverCallback).onTvDiscoverLoaded(
                        dummyTvShowResponses
                    )
                    null
                }.`when`(remoteDataSource).getTvDiscover(any())
                val tv = LiveDataTestUtil.getValue(localRepository.getTvDetail(dummyTvId))
                verify(remoteDataSource, atLeastOnce()).getMovieDiscover(any())
                assertNotNull(tv)
                assertEquals(dummyTvShowResponses.first().name, tv.title)
            }
        }

        describe("Get tv detail at first index with query") {
            it("result must be not null and check if tv title is the same with dummy") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.SearchTvShowsCallback).onTvShowsFound(
                        dummySearchTvShowResult
                    )
                    null
                }.`when`(remoteDataSource).searchTvShows(any(), eq(dummyTvQuery))
                val tv = LiveDataTestUtil.getValue(
                    localRepository.getTvDetailWithQuery(
                        dummyTvId,
                        dummyTvQuery
                    )
                )
                verify(remoteDataSource).searchTvShows(any(), eq(dummyTvQuery))
                assertNotNull(tv)
                assertEquals(dummySearchTvShowResult.first().name, tv.title)
            }
        }
    }

    group("Search content") {
        describe("Search movie with dummy query") {
            it("result must be not null and expected result is 1 item") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.SearchMoviesCallback).onMoviesFound(
                        dummySearchMovieResult
                    )
                    null
                }.`when`(remoteDataSource).searchMovies(any(), eq(dummyMovieQuery))
                val movieResult =
                    LiveDataTestUtil.getValue(localRepository.searchMovies(dummyMovieQuery))
                verify(remoteDataSource, atLeastOnce()).searchMovies(any(), eq(dummyMovieQuery))
                assertNotNull(movieResult)
                assertEquals(1, movieResult.size)
            }
        }

        describe("Search tv with dummy query") {
            it("result must be not null and expected result is 2 item") {
                doAnswer { invocation ->
                    (invocation.arguments[0] as RemoteDataSource.SearchTvShowsCallback).onTvShowsFound(
                        dummySearchTvShowResult
                    )
                    null
                }.`when`(remoteDataSource).searchTvShows(any(), eq(dummyTvQuery))
                val tvResult =
                    LiveDataTestUtil.getValue(localRepository.searchTvShows(dummyTvQuery))
                verify(remoteDataSource, atLeastOnce()).searchTvShows(any(), eq(dummyTvQuery))
                assertNotNull(tvResult)
                assertEquals(2, tvResult.size)
            }
        }
    }
})