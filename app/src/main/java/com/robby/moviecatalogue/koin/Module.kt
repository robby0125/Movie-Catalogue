package com.robby.moviecatalogue.koin

import com.robby.moviecatalogue.data.source.MovieRepository
import com.robby.moviecatalogue.data.source.RemoteDataSource
import com.robby.moviecatalogue.ui.content.ContentsViewModel
import com.robby.moviecatalogue.ui.detail.DetailViewModel
import com.robby.moviecatalogue.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { RemoteDataSource.getInstance() }
    single { MovieRepository.getInstance(get()) }
    viewModel { ContentsViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}