package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.data.source.SuggestKeywordDataSourceFactory
import com.gandan.giphyclone.data.source.TrendKeywordDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource
import com.gandan.giphyclone.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

class TrendKeywordSourceRepository(private val apiService: GiphyAPIService) {

    private lateinit var trendKeywordList: LiveData<PagedList<String>>
    private lateinit var trendKeywordDataSourceFactory: TrendKeywordDataSourceFactory

    private lateinit var suggestKeywordList: LiveData<PagedList<String>>
    private lateinit var suggestKeywordDataSourceFactory: SuggestKeywordDataSourceFactory

    fun getTrendKeywordData(compositeDisposable: CompositeDisposable): LiveData<PagedList<String>> {
        trendKeywordDataSourceFactory = TrendKeywordDataSourceFactory(compositeDisposable, apiService)

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        trendKeywordList = LivePagedListBuilder(trendKeywordDataSourceFactory, config).build()

        return trendKeywordList
    }

    fun getSuggestKeywordData(compositeDisposable: CompositeDisposable, term: String) : LiveData<PagedList<String>> {
        suggestKeywordDataSourceFactory = SuggestKeywordDataSourceFactory(compositeDisposable, apiService, term)

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        suggestKeywordList = LivePagedListBuilder(suggestKeywordDataSourceFactory, config).build()

        return suggestKeywordList
    }

    fun getTrendKeywordNetworkState(): LiveData<NetworkState> {
        return trendKeywordDataSourceFactory.trendKeywordDataSource.networkState
    }
}