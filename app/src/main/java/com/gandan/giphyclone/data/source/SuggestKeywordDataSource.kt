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

class SuggestKeywordDataSource(private val compositeDisposable: CompositeDisposable,
                               private val apiService: GiphyAPIService,
                               private val term: String): PositionalDataSource<String>() {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
    get() = _networkState

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<String>) {

        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getSuggestion(term, API_KEY).subscribeOn(Schedulers.io())
                .subscribe({ keywordData ->
                    val keywordList = ArrayList<String>()
                    keywordData.data.forEach {
                        keywordList.add(it.name)
                    }
                    callback.onResult(keywordList)
                    _networkState.postValue(NetworkState.LOADED)
                }, {
                    Log.e("SuggestKeywordError", it.message)
                    _networkState.postValue(NetworkState.ERROR)
                })
        )
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<String>) {

        _networkState.postValue(NetworkState.LOADING)

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
                    _networkState.postValue(NetworkState.LOADED)
                }, {
                    Log.e("SuggestKeywordError", it.message)
                    _networkState.postValue(NetworkState.ERROR)
                })
        )
    }
}