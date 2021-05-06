package com.robby.moviecatalogue.koin

import com.robby.moviecatalogue.data.MovieRepository
import com.robby.moviecatalogue.data.source.local.LocalDataSource
import com.robby.moviecatalogue.data.source.local.room.MovieDatabase
import com.robby.moviecatalogue.data.source.remote.RemoteDataSource
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.ui.detail.DetailViewModel
import com.robby.moviecatalogue.ui.search.SearchViewModel
import com.robby.moviecatalogue.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RemoteDataSource.getInstance() }
    single { MovieDatabase.getInstance(androidContext()).movieDao() }
    single { LocalDataSource.getInstance(get()) }
    single { MovieRepository.getInstance(get(), get(), AppExecutors()) }
    viewModel { ContentsViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}