package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.TrendingDataSource
import com.gandan.giphyclone.data.source.TrendingDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class TrendingDataRepository(private val apiService: GiphyAPIService,
                             private val type: String) {

    private lateinit var trendingList : LiveData<PagedList<Data>>
    lateinit var trendingDataRepository: TrendingDataSourceFactory

    fun getTrendingResultData(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Data>> {
        trendingDataRepository =
            TrendingDataSourceFactory(
                compositeDisposable,
                apiService,
                type
            )

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        trendingList = LivePagedListBuilder(trendingDataRepository, config).build()

        return trendingList
    }
}