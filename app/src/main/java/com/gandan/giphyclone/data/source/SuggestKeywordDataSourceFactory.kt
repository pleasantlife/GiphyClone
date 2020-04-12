package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.api.GiphyAPIService
import io.reactivex.disposables.CompositeDisposable

class SuggestKeywordDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                      private val apiService: GiphyAPIService,
                                      private val term: String): DataSource.Factory<Int, String>() {

    private val suggestKeywordDataSource = SuggestKeywordDataSource(compositeDisposable, apiService, term)

    override fun create(): DataSource<Int, String> {
        return suggestKeywordDataSource
    }
}