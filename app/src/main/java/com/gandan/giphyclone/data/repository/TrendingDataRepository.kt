package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.TrendingDataSource
import com.gandan.giphyclone.data.source.TrendingDataSourceFactory
import com.gandan.giphyclone.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

class TrendingDataRepository(private val apiService: GiphyAPIService,
                             private val type: String) {

    private lateinit var trendingList : LiveData<PagedList<Data>>
    lateinit var trendingDataSourceFactory: TrendingDataSourceFactory

    fun getTrendingResultData(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Data>> {
        trendingDataSourceFactory =
            TrendingDataSourceFactory(
                compositeDisposable,
                apiService,
                type
            )

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        trendingList = LivePagedListBuilder(trendingDataSourceFactory, config).build()

        return trendingList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return trendingDataSourceFactory.trendingDataSource.networkState
    }
}