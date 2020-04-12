package com.gandan.giphyclone.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.TrendKeywordSourceRepository
import com.gandan.giphyclone.view.viewmodel.SearchViewModel

class SearchViewModelFactory(private val trendKeywordSourceRepository: TrendKeywordSourceRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchViewModel(
            trendKeywordSourceRepository
        ) as T
    }
}