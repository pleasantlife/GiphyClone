package com.gandan.giphyclone.data.repository

import androidx.lifecycle.MutableLiveData
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.schedulers.Schedulers

class KeywordAPIRepository() {

    private val getAPIService = RetrofitUtil().getRetrofitService()

    fun getTrendKeyword() : MutableLiveData<List<String>> {
        val keywordList = MutableLiveData<List<String>>()
        val tempList = getAPIService.getTrendKeywords(API_KEY)
            .subscribeOn(Schedulers.newThread()).blockingGet().data

        keywordList.value = tempList
        return keywordList
    }

    fun getSuggestionKeyWord(term: String) : MutableLiveData<List<String>> {
        val suggestionList = MutableLiveData<List<String>>()
        val tempList = ArrayList<String>()
        val nameList = getAPIService.getSuggestion(term, API_KEY)
            .subscribeOn(Schedulers.newThread()).blockingGet().data

        for(data in nameList){
            tempList.add(data.name)
        }

        suggestionList.value = tempList

        return suggestionList
    }
}