package com.gandan.giphyclone.view.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.repository.TrendingDataSourceFactory

class TrendingViewModel : ViewModel() {

    fun getTrendingDataList(type: String): LiveData<PagedList<Data>> {
        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .build()

        return LivePagedListBuilder(TrendingDataSourceFactory(type), config).build()
    }
}
