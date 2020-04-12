package com.gandan.giphyclone.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.view.viewmodel.TrendingViewModel

class TrendingViewModelFactory(private val trendingDataRepository: TrendingDataRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return TrendingViewModel(
            trendingDataRepository
        ) as T
    }
}