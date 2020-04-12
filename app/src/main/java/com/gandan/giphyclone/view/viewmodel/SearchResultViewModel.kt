package com.gandan.giphyclone.view.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.SearchResultRepository
import com.gandan.giphyclone.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

class SearchResultViewModel(private val searchResultRepository: SearchResultRepository) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    fun getSearchResult(keyword: String, type: String): LiveData<PagedList<Data>> {
        return searchResultRepository.getSearchResultData(compositeDisposable, keyword, type)
    }

    fun getSuggestKeyword(term: String): LiveData<PagedList<String>> {
        return searchResultRepository.getSuggestKeywordData(compositeDisposable, term)
    }

    val networkState: LiveData<NetworkState> by lazy {
        searchResultRepository.getNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}