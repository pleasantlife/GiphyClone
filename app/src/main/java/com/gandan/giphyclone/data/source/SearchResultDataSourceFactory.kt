package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.GiphyAPIService
import io.reactivex.disposables.CompositeDisposable

class SearchResultDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                    private val apiService: GiphyAPIService,
                                    private val keyword: String,
                                    private val type: String): DataSource.Factory<Int, Data>() {

    override fun create(): DataSource<Int, Data> {
        return SearchResultDataSource(
            compositeDisposable,
            apiService,
            keyword,
            type
        )
    }
}