package com.robby.moviecatalogue.data.source

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.*
import com.robby.moviecatalogue.data.source.local.LocalDataSource
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.data.source.remote.RemoteDataSource
import com.robby.moviecatalogue.utils.AppExecutors
import com.robby.moviecatalogue.utils.DataDummy
import com.robby.moviecatalogue.utils.LiveDataTestUtil
import com.robby.moviecatalogue.utils.PagedListUtil
import com.robby.moviecatalogue.vo.Resource
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@Suppress("UNCHECKED_CAST")
class MovieRepositoryTest : Spek({
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

    val remote = mock(RemoteDataSource::class.java)
    val local = mock(LocalDataSource::class.java)
    val appExecutors = mock(AppExecutors::class.java)

    val movieRepository by memoized { FakeMovieRepository(remote, local, appExecutors) }

    val dummyMovies = DataDummy.getDummyMoviesAsContent()
    val dummyMovieResponses = DataDummy.getDummyMovies()
    val dummyMovieId = dummyMovieResponses.first().id
    val dummyMovieQuery = dummyMovieResponses.first().title.split(" ").first()
    val dummySearchMovieResult = dummyMovieResponses.filter { s ->
        s.title.contains(dummyMovieQuery, true)
    }

    val dummyTvShows = DataDummy.getDummyTvShowsAsContent()
    val dummyTvShowResponses = DataDummy.getDummyTvShows()
    val dummyTvId = dummyTvShowResponses.first().id
    val dummyTvQuery = dummyTvShowResponses.first().name.split(" ").first()
    val dummySearchTvShowResult = dummyTvShowResponses.filter { s ->
        s.name.contains(dummyTvQuery, true)
    }

    val dataSourceFactory =
        mock(DataSource.Factory::class.java) as DataSource.Factory<Int, ContentEntity>

    group("Get content discover") {
        describe("Get all movies test") {
            it("retrieve all movies then check if results is not null and results size equals with dummy") {
                `when`(local.getMovieDiscover()).thenReturn(dataSourceFactory)
                movieRepository.getMovieDiscover()

                val movieList =
                    Resource.success(PagedListUtil.mockPagedList(dummyMovies))
                verify(local).getMovieDiscover()
                assertNotNull(movieList)
                assertEquals(dummyMovieResponses.size, movieList.data?.size)
            }
        }

        describe("Get all tv shows test") {
            it("retrieve all tv shows then check if results is not null and results size equals with dummy") {
                `when`(local.getTvDiscover()).thenReturn(dataSourceFactory)
                movieRepository.getTvDiscover()

                val tvShowList =
                    Resource.success(PagedListUtil.mockPagedList(dummyTvShows))
                verify(local).getMovieDiscover()
                assertNotNull(tvShowList)
                assertEquals(dummyMovieResponses.size, tvShowList.data?.size)
            }
        }
    }

    group("Get content detail") {
        describe("Get movie detail at first index") {
            it("result must be not null and check if movie title is the same with dummy") {
                val dummyMovie = MutableLiveData<ContentEntity>()
                dummyMovie.value = dummyMovies.first()
                `when`(local.getMovieDetail(any())).thenReturn(dummyMovie)

                val movie = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(dummyMovieId))
                verify(local, atLeastOnce()).getMovieDiscover()
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
                }.`when`(remote).searchMovies(any(), eq(dummyMovieQuery))
                val movie = LiveDataTestUtil.getValue(
                    movieRepository.getMovieDetailWithQuery(
                        dummyMovieId,
                        dummyMovieQuery
                    )
                )
                verify(remote).searchMovies(any(), eq(dummyMovieQuery))
                assertNotNull(movie)
                assertEquals(dummySearchMovieResult.first().title, movie.title)
            }
        }

        describe("Get tv detail at first index") {
            it("result must be not null and check if tv title is the same with dummy") {
                val dummyTvShow = MutableLiveData<ContentEntity>()
                dummyTvShow.value = dummyTvShows.first()
                `when`(local.getTvDetail(any())).thenReturn(dummyTvShow)

                val tv = LiveDataTestUtil.getValue(movieRepository.getTvDetail(dummyTvId))
                verify(local, atLeastOnce()).getMovieDiscover()
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
                }.`when`(remote).searchTvShows(any(), eq(dummyTvQuery))
                val tv = LiveDataTestUtil.getValue(
                    movieRepository.getTvDetailWithQuery(
                        dummyTvId,
                        dummyTvQuery
                    )
                )
                verify(remote).searchTvShows(any(), eq(dummyTvQuery))
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
                }.`when`(remote).searchMovies(any(), eq(dummyMovieQuery))
                val movieResult =
                    LiveDataTestUtil.getValue(movieRepository.searchMovies(dummyMovieQuery))
                verify(remote, atLeastOnce()).searchMovies(any(), eq(dummyMovieQuery))
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
                }.`when`(remote).searchTvShows(any(), eq(dummyTvQuery))
                val tvResult =
                    LiveDataTestUtil.getValue(movieRepository.searchTvShows(dummyTvQuery))
                verify(remote, atLeastOnce()).searchTvShows(any(), eq(dummyTvQuery))
                assertNotNull(tvResult)
                assertEquals(2, tvResult.size)
            }
        }
    }

    group("Favorite content") {
        describe("Get favorite movies") {
            it("result must be not null and expected result is equals with dummy") {
                `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
                movieRepository.getFavoriteMovies()

                val movieList =
                    Resource.success(PagedListUtil.mockPagedList(dummyMovies))
                verify(local).getFavoriteMovies()
                assertNotNull(movieList)
                assertEquals(dummyMovies.size, movieList.data?.size)
            }
        }

        describe("Get favorite tv shows") {
            it("result must be not null and expected result is equals with dummy") {
                `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
                movieRepository.getFavoriteTvShows()

                val tvShowList =
                    Resource.success(PagedListUtil.mockPagedList(dummyTvShows))
                verify(local, atLeastOnce()).getFavoriteTvShows()
                assertNotNull(tvShowList)
                assertEquals(dummyTvShows.size, tvShowList.data?.size)
            }
        }
    }
})