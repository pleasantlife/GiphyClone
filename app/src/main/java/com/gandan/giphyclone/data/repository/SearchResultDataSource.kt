package com.gandan.giphyclone.data.repository

import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.data.repository.TrendingDataSource.Companion.OFFSET
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.disposables.CompositeDisposable

class SearchResultDataSource(private val keyword: String,
                             private val type: String): PositionalDataSource<Data>() {

    fun loadTotalCount(): Int {
        return RetrofitUtil().getRetrofitService().getSearchResult(type, API_KEY, keyword, ITEM_PER_PAGE, OFFSET).blockingGet().pagination.totalCount
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {
        callback.onResult(loadRangeInternal(params.startPosition))
    }

    fun loadRangeInternal(startPosition: Int): List<Data> {
        return RetrofitUtil().getRetrofitService().getSearchResult(type, API_KEY, keyword, ITEM_PER_PAGE, startPosition).blockingGet().data
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {
        val totalCount = loadTotalCount()
        val position = computeInitialLoadPosition(params, totalCount)

        callback.onResult(loadRangeInternal(position), position, totalCount)
    }
}