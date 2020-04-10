package com.gandan.giphyclone.data.source

import android.util.Log
import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.OFFSET
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchResultDataSource(private val compositeDisposable: CompositeDisposable,
                             private val apiService: GiphyAPIService,
                             private val keyword: String,
                             private val type: String): PositionalDataSource<Data>() {


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {
        compositeDisposable.add(
            apiService.getSearchResult(
                type, API_KEY, keyword, ITEM_PER_PAGE, params.startPosition).subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.data)
                },{
                    Log.e("Error", it.message.toString())
                })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {

        compositeDisposable.add(
        apiService.getSearchResult(
            type, API_KEY, keyword, ITEM_PER_PAGE, OFFSET).subscribeOn(Schedulers.io()).subscribe({
            val totalCount = it.pagination.totalCount
            val position = computeInitialLoadPosition(params, totalCount)
            callback.onResult(it.data, position, totalCount)
        },{
            Log.e("Error", it.message.toString())
        })
        )
    }


}