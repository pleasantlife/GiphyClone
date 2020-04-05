package com.gandan.giphyclone.view.searchresult

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.repository.TrendingDataSource

class SearchResultViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun getSearchResult(keyword: String, type: String): LiveData<PagedList<Data>> {
        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        return LivePagedListBuilder(SearchResultDataSourceFactory(keyword, type), config).build()
    }
}
