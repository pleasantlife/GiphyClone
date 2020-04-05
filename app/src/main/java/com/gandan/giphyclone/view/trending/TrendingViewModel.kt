package com.gandan.giphyclone.view.trending

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.repository.TrendingDataSourceFactory
import com.gandan.giphyclone.util.NetworkState

class TrendingViewModel : ViewModel() {

    lateinit var trendingDataSourceFactory: TrendingDataSourceFactory

    fun getNetworkState() {

    }

    fun getTrendingDataList(type: String): LiveData<PagedList<Data>> {
        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .build()

        trendingDataSourceFactory = TrendingDataSourceFactory(type)

        return LivePagedListBuilder(trendingDataSourceFactory, config).build()
    }
}
