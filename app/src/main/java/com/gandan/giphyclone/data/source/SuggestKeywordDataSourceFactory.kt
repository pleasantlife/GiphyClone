package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.GiphyAPIService
import io.reactivex.disposables.CompositeDisposable

class SuggestKeywordDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                      private val apiService: GiphyAPIService,
                                      private val term: String): DataSource.Factory<Int, String>() {

    override fun create(): DataSource<Int, String> {
        return SuggestKeywordDataSource(compositeDisposable, apiService, term)
    }
}