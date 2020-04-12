package com.gandan.giphyclone.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.data.repository.FavoriteSourceRepository
import com.gandan.giphyclone.view.viewmodel.FavoriteViewModel

class FavoriteViewModelFactory(private val favoriteSourceRepository: FavoriteSourceRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FavoriteViewModel(
            favoriteSourceRepository
        ) as T
    }
}