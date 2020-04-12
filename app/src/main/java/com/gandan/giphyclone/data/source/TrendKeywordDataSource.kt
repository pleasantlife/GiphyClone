package com.gandan.giphyclone.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.api.GiphyAPIService
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendKeywordDataSource(private val compositeDisposable: CompositeDisposable,
                             private val apiService: GiphyAPIService
) : PositionalDataSource<String>() {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getTrendKeywords(API_KEY).subscribeOn(Schedulers.io()).subscribe({
                callback.onResult(it.data)
                _networkState.postValue(NetworkState.LOADED)
            },{
                Log.e("TrendingKeyRangeErr", it.message)
                _networkState.postValue(NetworkState.ERROR)
            })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getTrendKeywords(API_KEY).subscribeOn(Schedulers.io()).subscribe(
                {
                    val totalCount = it.data.size
                    val position = computeInitialLoadPosition(params, totalCount)
                    callback.onResult(it.data, position, totalCount)
                    _networkState.postValue(NetworkState.LOADED)
                },
                {
                    Log.e("TrendingKeyErr", it.message)
                    _networkState.postValue(NetworkState.ERROR)
                }
            )
        )
    }
}