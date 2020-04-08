package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource
import io.reactivex.disposables.CompositeDisposable

class SearchResultRepository(private val apiService: GiphyAPIService) {

    lateinit var searchResultList : LiveData<PagedList<Data>>
    lateinit var searchResultDataSourceFactory: SearchResultDataSourceFactory

    fun getSearchResultData(compositeDisposable: CompositeDisposable,
                            apiService: GiphyAPIService,
                            keyword: String,
                            type: String) : LiveData<PagedList<Data>> {
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
}