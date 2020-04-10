package com.gandan.giphyclone.data.source

import android.util.Log
import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SuggestKeywordDataSource(private val compositeDisposable: CompositeDisposable,
                               private val apiService: GiphyAPIService,
                               private val term: String): PositionalDataSource<String>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {
        compositeDisposable.add(
            apiService.getSuggestion(term, API_KEY).subscribeOn(Schedulers.io())
                .subscribe({
                    val keywordList = ArrayList<String>()
                    it.data.forEach {
                        keywordList.add(it.name)
                    }
                    callback.onResult(keywordList)
                }, {
                    Log.e("SuggestKeywordError", it.message)
                })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {
        compositeDisposable.add(
            apiService.getSuggestion(term, API_KEY).subscribeOn(Schedulers.io())
                .subscribe({
                    val totalCount = it.pagination.count
                    val position = computeInitialLoadPosition(params, totalCount)
                    val keywordList = ArrayList<String>()
                    it.data.forEach {
                        keywordList.add(it.name)
                    }
                    callback.onResult(keywordList, position, totalCount)
                }, {
                    Log.e("SuggestKeywordError", it.message.toString())
                })
        )
    }
}