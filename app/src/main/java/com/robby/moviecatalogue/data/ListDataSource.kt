package com.robby.moviecatalogue.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PositionalDataSource

class ListDataSource<T>(private val items: List<T>) : DataSource.Factory<Int, T>() {
    private val sourceLiveData = MutableLiveData<InnerListDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val source = InnerListDataSource(items)
        sourceLiveData.postValue(source)
        return source
    }

    class InnerListDataSource<T>(var items: List<T>) : PositionalDataSource<T>() {
        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<T>
        ) {
            val totalCount = items.size

            val position = computeInitialLoadPosition(params, totalCount)
            val loadSize = computeInitialLoadSize(params, position, totalCount)

            val sublist = items.subList(position, position + loadSize)
            callback.onResult(sublist, position, totalCount)
        }

        override fun loadRange(
            params: LoadRangeParams,
            callback: LoadRangeCallback<T>
        ) {
            callback.onResult(
                items.subList(
                    params.startPosition,
                    items.size
                )
            )
        }
    }
}