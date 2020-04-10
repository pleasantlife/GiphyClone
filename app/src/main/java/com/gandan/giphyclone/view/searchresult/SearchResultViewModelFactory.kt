package com.gandan.giphyclone.view.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.SearchResultRepository

class SearchResultViewModelFactory(private val searchResultRepository: SearchResultRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SearchResultViewModel(searchResultRepository) as T
    }
}