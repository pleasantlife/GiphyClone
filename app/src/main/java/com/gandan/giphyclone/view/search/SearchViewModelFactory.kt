package com.gandan.giphyclone.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.TrendKeywordSourceRepository
import io.reactivex.disposables.CompositeDisposable

class SearchViewModelFactory(private val trendKeywordSourceRepository: TrendKeywordSourceRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(trendKeywordSourceRepository) as T
    }
}