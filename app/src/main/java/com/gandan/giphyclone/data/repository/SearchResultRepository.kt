package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.source.SuggestKeywordDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource
import com.gandan.giphyclone.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchResultRepository(private val apiService: GiphyAPIService) {

    private lateinit var searchResultList : LiveData<PagedList<Data>>
    private lateinit var searchResultDataSourceFactory: SearchResultDataSourceFactory


    fun getSearchResultData(compositeDisposable: CompositeDisposable, keyword: String, type: String) : LiveData<PagedList<Data>> {
        searchResultDataSourceFactory =
            SearchResultDataSourceFactory(
                compositeDisposable,
                apiService,
                keyword,
                type
            )

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        searchResultList = LivePagedListBuilder(searchResultDataSourceFactory, config).build()

        return searchResultList
    }

    fun getSuggestKeywordData(compositeDisposable: CompositeDisposable, term: String) : LiveData<PagedList<String>> {
        val suggestKeywordDataSourceFactory = SuggestKeywordDataSourceFactory(compositeDisposable, apiService, term)

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        val suggestKeywordList = LivePagedListBuilder(suggestKeywordDataSourceFactory, config).build()

        return suggestKeywordList
    }

    fun getNetworkState(): LiveData<NetworkState> {
       return searchResultDataSourceFactory.searchResultDataSource.networkState
    }
}