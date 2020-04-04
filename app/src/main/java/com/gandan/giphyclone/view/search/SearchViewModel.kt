package com.gandan.giphyclone.view.search

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gandan.giphyclone.data.repository.KeywordAPIRepository
import com.gandan.giphyclone.data.repository.RecentKeywordRepository


class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun getKeywords() : MutableLiveData<List<String>> {
        return KeywordAPIRepository().getTrendKeyword()
    }

    fun getRecentKeywords(sharedPreferences: SharedPreferences): MutableLiveData<List<String>> {
        val keywordList = MutableLiveData<List<String>>()
        keywordList.value = RecentKeywordRepository(sharedPreferences).getRecentSearchKeywords()
        return keywordList
    }

    fun saveRecentKeywords(sharedPreferences: SharedPreferences) {

    }

    fun getAutoKeywords(term: String) : MutableLiveData<List<String>> {
        return KeywordAPIRepository().getSuggestionKeyWord(term)
    }
}
