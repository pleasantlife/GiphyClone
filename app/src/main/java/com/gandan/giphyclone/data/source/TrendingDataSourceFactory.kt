package com.gandan.giphyclone.data.source

import androidx.paging.DataSource
import com.gandan.giphyclone.data.model.gifs.Data

class TrendingDataSourceFactory(private val type: String): DataSource.Factory<Int, Data>() {

    lateinit var trendingDataSource : DataSource<Int, Data>

    override fun create(): DataSource<Int, Data> {
        trendingDataSource =
            TrendingDataSource(type)
        return trendingDataSource
    }
}