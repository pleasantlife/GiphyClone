package com.gandan.giphyclone.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.repository.TrendingDataSource.Companion.ITEM_PER_PAGE

class DetailViewModel : ViewModel() {

    private lateinit var searchResultDataSourceFactory: SearchResultDataSourceFactory
    private lateinit var liveData: LiveData<PagedList<Data>>

    fun getRelatedData(keyword: String): LiveData<PagedList<Data>> {
        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .setInitialLoadSizeHint(ITEM_PER_PAGE)
            .build()
        searchResultDataSourceFactory = SearchResultDataSourceFactory(keyword, "gifs")
        liveData = LivePagedListBuilder(searchResultDataSourceFactory, config).build()
        return liveData
    }
}
