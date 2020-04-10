package com.gandan.giphyclone.data.source

import android.util.Log
import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendKeywordDataSource(private val compositeDisposable: CompositeDisposable,
                             private val apiService: GiphyAPIService) : PositionalDataSource<String>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {
        compositeDisposable.add(
            apiService.getTrendKeywords(API_KEY).subscribeOn(Schedulers.io()).subscribe({
                Log.e("Success", it.data.size.toString())
                callback.onResult(it.data)
            },{
                Log.e("TrendingKeyRangeErr", it.message.toString())
            })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {
        compositeDisposable.add(
            apiService.getTrendKeywords(API_KEY).subscribeOn(Schedulers.io()).subscribe(
                {
                    Log.e("Success", it.data.size.toString())
                    val totalCount = it.data.size
                    val position = computeInitialLoadPosition(params, totalCount)
                    callback.onResult(it.data, position, totalCount)
                },
                {
                    Log.e("TrendingKeyErr", it.message.toString())
                }
            )
        )
    }
}