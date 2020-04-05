package com.gandan.giphyclone.data.repository

import androidx.paging.DataSource
import com.gandan.giphyclone.data.model.gifs.Data

class TrendingDataSourceFactory(private val type: String): DataSource.Factory<Int, Data>() {

    lateinit var trendingDataSource : DataSource<Int, Data>

    override fun create(): DataSource<Int, Data> {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        trendingDataSource = TrendingDataSource(type)
        return trendingDataSource
    }
}