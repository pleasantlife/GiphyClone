package com.gandan.giphyclone.data.repository

import androidx.paging.DataSource
import com.gandan.giphyclone.data.model.gifs.Data

class SearchResultDataSourceFactory(private val keyword: String,
                                    private val type: String): DataSource.Factory<Int, Data>() {

    override fun create(): DataSource<Int, Data> {
        return SearchResultDataSource(keyword, type)
    }
}