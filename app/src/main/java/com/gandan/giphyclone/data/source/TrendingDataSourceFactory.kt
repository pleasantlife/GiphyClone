package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import io.reactivex.disposables.CompositeDisposable

class TrendingDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                private val apiService: GiphyAPIService,
                                private val type: String): DataSource.Factory<Int, Data>() {

    lateinit var trendingDataSource : DataSource<Int, Data>

    override fun create(): DataSource<Int, Data> {
        trendingDataSource =
            TrendingDataSource(compositeDisposable, apiService, type)
        return trendingDataSource
    }
}