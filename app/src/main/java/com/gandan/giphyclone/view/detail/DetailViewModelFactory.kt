package com.gandan.giphyclone.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.TrendingDataRepository

class DetailViewModelFactory(private val trendingDataRepository: TrendingDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return DetailViewModel(trendingDataRepository) as T
    }
}