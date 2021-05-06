package com.robby.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.robby.moviecatalogue.data.source.local.LocalDataSource
import com.robby.moviecatalogue.data.source.local.entity.ContentEntity
import com.robby.moviecatalogue.data.source.remote.RemoteDataSource
import com.robby.moviecatalogue.data.source.remote.api.ApiResponse
import com.robby.moviecatalogue.data.source.remote.response.GenresItem
import com.robby.moviecatalogue.data.source.remote.response.Movie
import com.robby.moviecatalogue.data.source.remote.response.TvShow
import com.robby.moviecatalogue.utils.AppExecutors
import com.robby.moviecatalogue.utils.ContentType
import com.robby.moviecatalogue.vo.Resource
import java.util.*
import kotlin.collections.ArrayList

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    companion object {
        private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource, appExecutors).apply {
                    instance = this
                }
            }
    }

    private var movieGenreList = ArrayList<GenresItem>()
    private var tvGenreList = ArrayList<GenresItem>()

    init {
        remoteDataSource.getGenreList(object : RemoteDataSource.LoadGenreListCallback {
            override fun onMovieGenreListLoaded(listGenreResponses: List<GenresItem>) {
                movieGenreList.addAll(listGenreResponses)
            }

            override fun onTvShowGenreListLoaded(listGenreResponses: List<GenresItem>) {
                tvGenreList.addAll(listGenreResponses)
            }
        })
    }

    override fun getMovieDiscover(): LiveData<Resource<PagedList<ContentEntity>>> {
        return object : NetworkBoundResource<PagedList<ContentEntity>, List<Movie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<ContentEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieDiscover(), config).build()
            }

            override fun shouldFetch(data: PagedList<ContentEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<Movie>>> =
                remoteDataSource.getMovieDiscover()

            override fun saveCallResult(data: List<Movie>) {
                val movieList = populateMovieList(data)
                localDataSource.insertListContent(movieList)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<ContentEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getTvDiscover(): LiveData<Resource<PagedList<ContentEntity>>> {
        return object : NetworkBoundResource<PagedList<ContentEntity>, List<TvShow>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<ContentEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvDiscover(), config).build()
            }

            override fun shouldFetch(data: PagedList<ContentEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShow>>> =
                remoteDataSource.getTvDiscover()

            override fun saveCallResult(data: List<TvShow>) {
                val tvShowList = populateTvShowList(data)
                localDataSource.insertListContent(tvShowList)
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<ContentEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun getMovieDetail(movieId: Int): LiveData<ContentEntity> =
        localDataSource.getMovieDetail(movieId)

    override fun getMovieDetailWithQuery(movieId: Int, query: String): LiveData<ContentEntity> {
        val movieResult = MutableLiveData<ContentEntity>()

        remoteDataSource.searchMovies(object : RemoteDataSource.SearchMoviesCallback {
            override fun onMoviesFound(listMovieResponses: List<Movie>) {
                movieResult.postValue(populateOnlineMovieDetail(listMovieResponses, movieId))
            }
        }, query)

        return movieResult
    }

    override fun getTvDetail(tvId: Int): LiveData<ContentEntity> = localDataSource.getTvDetail(tvId)

    override fun getTvDetailWithQuery(tvId: Int, query: String): LiveData<ContentEntity> {
        val tvResult = MutableLiveData<ContentEntity>()

        remoteDataSource.searchTvShows(object : RemoteDataSource.SearchTvShowsCallback {
            override fun onTvShowsFound(listTvShowResponses: List<TvShow>) {
                tvResult.postValue(populateOnlineTvShowDetail(listTvShowResponses, tvId))
            }
        }, query)

        return tvResult
    }

    override fun setContentFavorite(content: ContentEntity, state: Boolean) =
        appExecutors.diskIO().execute {
            localDataSource.setContentFavorite(content, state)
        }

    override fun searchMovies(query: String): LiveData<PagedList<ContentEntity>> {
        val movieResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchMovies(object : RemoteDataSource.SearchMoviesCallback {
            override fun onMoviesFound(listMovieResponses: List<Movie>) {
                movieResults.postValue(populateMovieList(listMovieResponses))
            }

        }, query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return Transformations.switchMap(movieResults) {
            ListDataSource(it).toLiveData(config)
        }
    }

    override fun searchTvShows(query: String): LiveData<PagedList<ContentEntity>> {
        val tvResults = MutableLiveData<List<ContentEntity>>()

        remoteDataSource.searchTvShows(object : RemoteDataSource.SearchTvShowsCallback {
            override fun onTvShowsFound(listTvShowResponses: List<TvShow>) {
                tvResults.postValue(populateTvShowList(listTvShowResponses))
            }

        }, query)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()

        return Transformations.switchMap(tvResults) {
            ListDataSource(it).toLiveData(config)
        }
    }

    override fun isContentAvailable(contentId: Int): Boolean {
        val listContent = ArrayList<ContentEntity>()
        appExecutors.diskIO().execute {
            listContent.addAll(localDataSource.getAllContents())
        }

        for (content in listContent) {
            if (content.id == contentId) return true
        }

        return false
    }

    override fun insertContent(content: ContentEntity) = appExecutors.diskIO().execute {
        localDataSource.insertContent(content)
    }

    private fun populateMovieList(movieList: List<Movie>): List<ContentEntity> {
        val movies = ArrayList<ContentEntity>()

        for (movieResponse in movieList) {
            with(movieResponse) {
                val genre = buildGenre(movieGenreList, genreIds)
                val language = Locale(originalLanguage).displayLanguage

                val movie = ContentEntity(
                    id,
                    ContentType.MOVIE,
                    title,
                    genre,
                    releaseDate,
                    voteAverage,
                    language,
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
                val language = Locale(originalLanguage).displayLanguage

                val tvShow = ContentEntity(
                    id,
                    ContentType.TV,
                    name,
                    genre,
                    firstAirDate,
                    voteAverage,
                    language,
                    overview,
                    "$BASE_IMAGE_URL$posterPath",
                    "$BASE_IMAGE_URL$backdropPath"
                )

                tvShows.add(tvShow)
            }
        }

        return tvShows
    }

    private fun populateOnlineMovieDetail(contentList: List<Movie>, movieId: Int): ContentEntity {
        lateinit var content: ContentEntity

        for (response in contentList) {
            if (response.id == movieId) {
                with(response) {
                    val genre = buildGenre(movieGenreList, genreIds)
                    val language = Locale(originalLanguage).displayLanguage

                    content = ContentEntity(
                        id,
                        ContentType.MOVIE,
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

        return content
    }

    private fun populateOnlineTvShowDetail(tvList: List<TvShow>, tvId: Int): ContentEntity {
        lateinit var tv: ContentEntity

        for (response in tvList) {
            if (response.id == tvId) {
                with(response) {
                    val genre = buildGenre(movieGenreList, genreIds)
                    val language = Locale(originalLanguage).displayLanguage

                    tv = ContentEntity(
                        id,
                        ContentType.TV,
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