package com.gandan.giphyclone.view.searchresult


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.gandan.giphyclone.data.GiphyAPIService
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.SearchResultRepository
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.data.source.SearchResultDataSourceFactory
import com.gandan.giphyclone.data.source.TrendingDataSource.Companion.ITEM_PER_PAGE
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.disposables.CompositeDisposable

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    fun getSearchResult(keyword: String, type: String): LiveData<PagedList<Data>> {
        return searchResultRepository.getSearchResultData(compositeDisposable, keyword, type)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}