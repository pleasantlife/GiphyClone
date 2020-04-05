package com.gandan.giphyclone.data.repository

import androidx.paging.PositionalDataSource
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY

class TrendingDataSource(private val type: String) : PositionalDataSource<Data>() {

    companion object {
        const val ITEM_PER_PAGE = 50
        const val OFFSET = 0
    }

    private fun loadTotalCount(): Int {
        return RetrofitUtil().getRetrofitService()
            .getTrending(type, API_KEY, ITEM_PER_PAGE, OFFSET).blockingGet()
            .pagination.totalCount

    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Data>) {
        callback.onResult(loadRangeInternal(params.startPosition))
    }

    private fun loadRangeInternal(startPosition: Int): List<Data> {

        return RetrofitUtil().getRetrofitService()
            .getTrending(type, API_KEY, ITEM_PER_PAGE, startPosition).blockingGet().data

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Data>) {

        val totalCount = loadTotalCount()
        val position = computeInitialLoadPosition(params, totalCount)
        callback.onResult(loadRangeInternal(position), position, totalCount)

    }
}


