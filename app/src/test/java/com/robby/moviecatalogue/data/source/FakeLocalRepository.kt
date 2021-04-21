package com.robby.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.model.response.GenresItem
import com.robby.moviecatalogue.data.model.response.Movie
import com.robby.moviecatalogue.data.model.response.TvShow
import java.util.*
import kotlin.collections.ArrayList

class FakeLocalRepository(private val remoteDataSource: RemoteDataSource) :
    LocalDataSource {

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    private var movieGenreList = ArrayList<GenresItem>()
    private var tvGenreList = ArrayList<GenresItem>()

    init {
        remoteDataSource.getGenreList(object : RemoteDataSource.LoadGenreListCallback {
            override fun onMovieGenreListLoaded(listGenreReponses: List<GenresItem>) {
                movieGenreList.addAll(listGenreReponses)
            }

            override fun onTvShowGenreListLoaded(listGenreReponses: List<GenresItem>) {
                tvGenreList.addAll(listGenreReponses)
            }
        })
    }

    override fun getMovieDiscover(): LiveData<List<ContentEntity>> {
        val movieResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.getMovieDiscover(object : RemoteDataSource.LoadMovieDiscoverCallback {
            override fun onMovieDiscoverLoaded(listMovieResponses: List<Movie>) {
                movieResults.postValue(populateMovieList(listMovieResponses))
            }
        })

        return movieResults
    }

    override fun getTvDiscover(): LiveData<List<ContentEntity>> {
        val tvResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.getTvDiscover(object : RemoteDataSource.LoadTvDiscoverCallback {
            override fun onTvDiscoverLoaded(listTvShowResponses: List<TvShow>) {
                tvResults.postValue(populateTvShowList(listTvShowResponses))
            }
        })

        return tvResults
    }

    override fun getMovieDetail(movieId: Int): LiveData<ContentEntity> {
        val movieResult = MutableLiveData<ContentEntity>()

        remoteDataSource.getMovieDiscover(object : RemoteDataSource.LoadMovieDiscoverCallback {
            override fun onMovieDiscoverLoaded(listMovieResponses: List<Movie>) {
                movieResult.postValue(populateMovieDetail(listMovieResponses, movieId))
            }
        })

        return movieResult
    }

    override fun getMovieDetailWithQuery(movieId: Int, query: String): LiveData<ContentEntity> {
        val movieResult = MutableLiveData<ContentEntity>()

        remoteDataSource.searchMovies(object : RemoteDataSource.SearchMoviesCallback {
            override fun onMoviesFound(listMovieResponses: List<Movie>) {
                movieResult.postValue(populateMovieDetail(listMovieResponses, movieId))
            }
        }, query)

        return movieResult
    }

    override fun getTvDetail(tvId: Int): LiveData<ContentEntity> {
        val tvResult = MutableLiveData<ContentEntity>()

        remoteDataSource.getTvDiscover(object : RemoteDataSource.LoadTvDiscoverCallback {
            override fun onTvDiscoverLoaded(listTvShowResponses: List<TvShow>) {
                tvResult.postValue(populateTvShowDetail(listTvShowResponses, tvId))
            }
        })

        return tvResult
    }

    override fun getTvDetailWithQuery(tvId: Int, query: String): LiveData<ContentEntity> {
        val tvResult = MutableLiveData<ContentEntity>()

        remoteDataSource.searchTvShows(object : RemoteDataSource.SearchTvShowsCallback {
            override fun onTvShowsFound(listTvShowResponses: List<TvShow>) {
                tvResult.postValue(populateTvShowDetail(listTvShowResponses, tvId))
            }
        }, query)

        return tvResult
    }

    override fun searchMovies(query: String): LiveData<List<ContentEntity>> {
        val movieResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchMovies(object : RemoteDataSource.SearchMoviesCallback {
            override fun onMoviesFound(listMovieResponses: List<Movie>) {
                movieResults.postValue(populateMovieList(listMovieResponses))
            }

        }, query)

        return movieResults
    }

    override fun searchTvShows(query: String): LiveData<List<ContentEntity>> {
        val tvResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchTvShows(object : RemoteDataSource.SearchTvShowsCallback {
            override fun onTvShowsFound(listTvShowResponses: List<TvShow>) {
                tvResults.postValue(populateTvShowList(listTvShowResponses))
            }

        }, query)

        return tvResults
    }

    private fun populateMovieList(movieList: List<Movie>): List<ContentEntity> {
        val movies = ArrayList<ContentEntity>()

        for (movieResponse in movieList) {
            with(movieResponse) {
                val genre = buildGenre(movieGenreList, genreIds)

                val movie = ContentEntity(
                    id,
                    title,
                    genre,
                    releaseDate,
                    voteAverage,
                    originalLanguage,
                    overview,
                    "$BASE_IMAGE_URL$posterPath",
                    "$BASE_IMAGE_URL$backdropPath"
                )

                movies.add(movie)
            }
        }

        return movies
    }

    private fun populateTvShowList(tvShowList: List<TvShow>): List<ContentEntity> {
        val tvShows = ArrayList<ContentEntity>()

        for (tvShowResponse in tvShowList) {
            with(tvShowResponse) {
                val genre = buildGenre(tvGenreList, genreIds)

                val movie = ContentEntity(
                    id,
                    name,
                    genre,
                    firstAirDate,
                    voteAverage,
                    originalLanguage,
                    overview,
                    "$BASE_IMAGE_URL$posterPath",
                    "$BASE_IMAGE_URL$backdropPath"
                )

                tvShows.add(movie)
            }
        }

        return tvShows
    }

    private fun populateMovieDetail(movieList: List<Movie>, movieId: Int): ContentEntity {
        lateinit var movie: ContentEntity

        for (response in movieList) {
            if (response.id == movieId) {
                with(response) {
                    val genre = buildGenre(movieGenreList, genreIds)
                    val language = Locale(originalLanguage).displayLanguage

                    movie = ContentEntity(
                        id,
                        title,
                        genre,
                        releaseDate,
                        voteAverage,
                        language,
                        overview,
                        "$BASE_IMAGE_URL$posterPath",
                        "$BASE_IMAGE_URL$backdropPath"
                    )
                }

                break
            }
        }

        return movie
    }

    private fun populateTvShowDetail(tvList: List<TvShow>, tvId: Int): ContentEntity {
        lateinit var tv: ContentEntity

        for (response in tvList) {
            if (response.id == tvId) {
                with(response) {
                    val genre = buildGenre(movieGenreList, genreIds)
                    val language = Locale(originalLanguage).displayLanguage

                    tv = ContentEntity(
                        id,
                        name,
                        genre,
                        firstAirDate,
                        voteAverage,
                        language,
                        overview,
                        "$BASE_IMAGE_URL$posterPath",
                        "$BASE_IMAGE_URL$backdropPath"
                    )
                }

                break
            }
        }

        return tv
    }

    private fun buildGenre(requiredId: List<GenresItem>, availableId: List<Int>): String {
        return buildString {
            if (requiredId.isNotEmpty()) {
                for (i in availableId.indices) {
                    for (genreItem in requiredId) {
                        if (availableId[i] == genreItem.id) {
                            append(genreItem.name)
                            break
                        }
                    }

                    if (i < availableId.size - 1) append(", ")
                }
            }
        }
    }
}