package com.gandan.giphyclone.view.favorite

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.FavoriteSourceRepository
import io.reactivex.disposables.CompositeDisposable

class FavoriteViewModel(private val favoriteSourceRepository: FavoriteSourceRepository) : ViewModel() {


    private val compositeDisposable = CompositeDisposable()

    val  favoriteDataList : LiveData<PagedList<Data>> by lazy {
        favoriteSourceRepository.fetchFavoriteData(compositeDisposable)
    }

    fun listIsEmpty(): Boolean {
        return favoriteDataList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


//    val  networkState : LiveData<NetworkState> by lazy {
//        movieRepository.getNetworkState()
//    }




}
