package com.gandan.giphyclone.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.suggestion.Name
import com.gandan.giphyclone.data.repository.TrendKeywordSourceRepository
import io.reactivex.disposables.CompositeDisposable


class SearchViewModel(private val trendKeywordSourceRepository: TrendKeywordSourceRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val trendKeywordList by lazy {
        trendKeywordSourceRepository.getTrendKeywordData(compositeDisposable)
    }

    val networkState by lazy {
        trendKeywordSourceRepository.getTrendKeywordNetworkState()
    }

    fun getSuggestKeywordList(term: String) : LiveData<PagedList<String>>  {
        return trendKeywordSourceRepository.getSuggestKeywordData(compositeDisposable, term)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
