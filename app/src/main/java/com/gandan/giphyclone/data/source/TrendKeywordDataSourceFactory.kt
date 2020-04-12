package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.api.GiphyAPIService
import io.reactivex.disposables.CompositeDisposable

class TrendKeywordDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                    private val apiService: GiphyAPIService
): DataSource.Factory<Int, String>()  {

    val trendKeywordDataSource = TrendKeywordDataSource(compositeDisposable, apiService)

    override fun create(): DataSource<Int, String> {
        return trendKeywordDataSource
    }
}