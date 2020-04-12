package com.gandan.giphyclone.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.OFFSET
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchResultDataSource(private val compositeDisposable: CompositeDisposable,
                             private val apiService: GiphyAPIService,
                             private val keyword: String,
                             private val type: String): PositionalDataSource<Data>() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = _networkState

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getSearchResult(
                type, API_KEY, keyword, ITEM_PER_PAGE, params.startPosition).subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.data)
                    _networkState.postValue(NetworkState.LOADED)
                },{
                    Log.e("SearchResultRangeErr", it.message)
                    _networkState.postValue(NetworkState.ERROR)
                })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
        apiService.getSearchResult(
            type, API_KEY, keyword, ITEM_PER_PAGE, OFFSET).subscribeOn(Schedulers.io()).subscribe({
            val totalCount = it.pagination.totalCount
            val position = computeInitialLoadPosition(params, totalCount)
            callback.onResult(it.data, position, totalCount)
            _networkState.postValue(NetworkState.LOADED)
        },{
            Log.e("SearchResultInitErr", it.message)
            _networkState.postValue(NetworkState.ERROR)
        })
        )
    }


}