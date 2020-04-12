package com.gandan.giphyclone.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(private val trendingDataRepository: TrendingDataRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val trendingDataList : LiveData<PagedList<Data>> by lazy {
        trendingDataRepository.getTrendingResultData(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        trendingDataRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return trendingDataList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
