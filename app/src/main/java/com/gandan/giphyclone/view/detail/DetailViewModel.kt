package com.gandan.giphyclone.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.source.TrendingDataSourceFactory

class DetailViewModel : ViewModel() {

    private lateinit var trendingDataSourceFactory: TrendingDataSourceFactory
    private lateinit var liveData: LiveData<PagedList<Data>>

    fun getTrendingDataList(type: String): LiveData<PagedList<Data>> {
        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .build()

        trendingDataSourceFactory =
            TrendingDataSourceFactory(type)

        return LivePagedListBuilder(trendingDataSourceFactory, config).build()
    }
}
