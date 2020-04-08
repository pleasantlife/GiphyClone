package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.GiphyAPIService
import io.reactivex.disposables.CompositeDisposable

class FavoriteDataSourceFactory(private val compositeDisposable: CompositeDisposable,
                                private val apiService: GiphyAPIService,
                                private val idList: ArrayList<String>): DataSource.Factory<Int, Data>() {


    override fun create(): DataSource<Int, Data> {
        return FavoriteDataSource(compositeDisposable, apiService, idList)
    }
}