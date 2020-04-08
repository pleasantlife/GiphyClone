package com.gandan.giphyclone.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.FavoriteDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource
import io.reactivex.disposables.CompositeDisposable

class FavoriteSourceRepository (private val apiService: GiphyAPIService, private val idList: ArrayList<String>) {

    lateinit var favoriteDataList: LiveData<PagedList<Data>>
    lateinit var favoriteSourceFactory: FavoriteDataSourceFactory

    fun fetchFavoriteData (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Data>> {
        favoriteSourceFactory = FavoriteDataSourceFactory(compositeDisposable, apiService, idList)

        val config = PagedList.Config.Builder()
            .setPageSize(TrendingDataSource.ITEM_PER_PAGE)
            .setInitialLoadSizeHint(TrendingDataSource.ITEM_PER_PAGE)
            .build()

        favoriteDataList = LivePagedListBuilder(favoriteSourceFactory, config).build()

        return favoriteDataList
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
//    }
}