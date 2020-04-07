package com.gandan.giphyclone.view.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchResultViewModelFactory(private val keyword: String, private val types: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchResultViewModel() as T
    }
}