package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import io.reactivex.disposables.CompositeDisposable

class TrendingDataSourceFactory(
    compositeDisposable: CompositeDisposable,
    apiService: GiphyAPIService,
    type: String): DataSource.Factory<Int, Data>() {

    val trendingDataSource = TrendingDataSource(compositeDisposable, apiService, type)

    override fun create(): DataSource<Int, Data> {
        return trendingDataSource
    }
}