package com.robby.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.robby.moviecatalogue.data.model.local.ContentEntity
import com.robby.moviecatalogue.data.model.response.*
import java.util.*
import kotlin.collections.ArrayList

class LocalRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    LocalDataSource {

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        @Volatile
        private var instance: LocalRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): LocalRepository =
            instance ?: synchronized(this) {
                instance ?: LocalRepository(remoteDataSource).apply { instance = this }
            }
    }

    private var movieGenreList = ArrayList<GenresItem>()
    private var tvGenreList = ArrayList<GenresItem>()

    init {
        remoteDataSource.getGenreList(object : RemoteDataSource.LoadGenreListCallback {
            override fun onMovieGenreListLoaded(genreListResponse: GenreListResponse) {
                movieGenreList.addAll(genreListResponse.genres)
            }

            override fun onTvShowGenreListLoaded(genreListResponse: GenreListResponse) {
                tvGenreList.addAll(genreListResponse.genres)
            }
        })
    }

    override fun getMovieDiscover(): LiveData<List<ContentEntity>> {
        val movieResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.getMovieDiscover(object : RemoteDataSource.LoadMovieDiscoverCallback {
            override fun onMovieDiscoverLoaded(moviesResponse: MoviesResponse) {
                movieResults.postValue(populateMovieList(moviesResponse.results))
            }
        })

        return movieResults
    }

    override fun getTvDiscover(): LiveData<List<ContentEntity>> {
        val tvResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.getTvDiscover(object : RemoteDataSource.LoadTvDiscoverCallback {
            override fun onTvDiscoverLoaded(tvShowsResponse: TvShowsResponse) {
                tvResults.postValue(populateTvShowList(tvShowsResponse.results))
            }
        })

        return tvResults
    }

    override fun getMovieDetail(movieId: Int): LiveData<ContentEntity> {
        val movieResult = MutableLiveData<ContentEntity>()

        remoteDataSource.getMovieDiscover(object : RemoteDataSource.LoadMovieDiscoverCallback {
            override fun onMovieDiscoverLoaded(moviesResponse: MoviesResponse) {
                movieResult.postValue(populateMovieDetail(moviesResponse.results, movieId))
            }
        })

        return movieResult
    }

    override fun getTvDetail(tvId: Int): LiveData<ContentEntity> {
        val tvResult = MutableLiveData<ContentEntity>()

        remoteDataSource.getTvDiscover(object : RemoteDataSource.LoadTvDiscoverCallback {
            override fun onTvDiscoverLoaded(tvShowsResponse: TvShowsResponse) {
                for (response in tvShowsResponse.results) {
                    if (response.id == tvId) {
                        with(response) {
                            val genre = buildGenre(tvGenreList, genreIds)
                            val language = Locale(originalLanguage).displayLanguage

                            val tv = ContentEntity(
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

                            tvResult.postValue(tv)
                        }

                        break
                    }
                }
            }
        })

        return tvResult
    }

    override fun searchMovies(query: String): LiveData<List<ContentEntity>> {
        val movieResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchMovies(object : RemoteDataSource.SearchMoviesCallback {
            override fun onMoviesFound(moviesResponse: MoviesResponse) {
                movieResults.postValue(populateMovieList(moviesResponse.results))
            }
        }, query)

        return movieResults
    }

    override fun searchTvShows(query: String): LiveData<List<ContentEntity>> {
        val tvResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchTvShows(object : RemoteDataSource.SearchTvShowsCallback {
            override fun onTvShowsFound(tvShowsResponse: TvShowsResponse) {
                tvResults.postValue(populateTvShowList(tvShowsResponse.results))
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