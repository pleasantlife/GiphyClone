package com.gandan.giphyclone.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.source.TrendingDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class DetailViewModel(private val trendingDataRepository: TrendingDataRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val trendingDataList : LiveData<PagedList<Data>> by lazy {
        trendingDataRepository.getTrendingResultData(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return trendingDataList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
