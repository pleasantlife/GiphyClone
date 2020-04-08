package com.gandan.giphyclone.view.searchresult


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.source.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.disposables.CompositeDisposable

class SearchResultViewModel : ViewModel() {

    private lateinit var searchDataSourceFactory: SearchResultDataSourceFactory
    private lateinit var liveData : LiveData<PagedList<Data>>
    private var compositeDisposable = CompositeDisposable()

    fun getSearchResult(keyword: String, type: String): LiveData<PagedList<Data>> {
        Log.e("keyword", keyword)
        val config = PagedList.Config.Builder()
            .setPageSize(ITEM_PER_PAGE)
            .setInitialLoadSizeHint(ITEM_PER_PAGE)
            .build()
        val apiService = RetrofitUtil().getRetrofitService()
        searchDataSourceFactory =
            SearchResultDataSourceFactory(
                compositeDisposable,
                apiService,
                keyword,
                type
            )
        liveData = LivePagedListBuilder(searchDataSourceFactory, config).build()
        return liveData
    }

//    fun refresh(){
//        if(liveData.value != null) {
//            liveData.value!!.dataSource.invalidate()
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}