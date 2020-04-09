package com.gandan.giphyclone.view.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import io.reactivex.disposables.CompositeDisposable

class TrendingViewModel(trendingSourceRepository: TrendingDataRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val trendingDataList : LiveData<PagedList<Data>> by lazy {
        trendingSourceRepository.getTrendingResultData(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return trendingDataList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
