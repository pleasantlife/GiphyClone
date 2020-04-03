package com.gandan.giphyclone.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.gandan.giphyclone.data.model.gifs.FixedDownsampled
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoadRepository() {

    fun getGifData() : MutableLiveData<List<FixedDownsampled>>{
        val urlList = MutableLiveData<List<FixedDownsampled>>()
        val tempList = ArrayList<FixedDownsampled>()
        val dataList = RetrofitUtil().getRetrofitService().getGifTrending(RetrofitUtil.API_KEY, 500)
            .subscribeOn(Schedulers.newThread()).blockingFirst()

        for(data in dataList.data){
            val downSampledData = data.images.fixedWidthDownsampled
            downSampledData.id = data.id
            tempList.add(downSampledData)
        }
        urlList.value = tempList

        return urlList
    }

    fun getTrendKeyword() : MutableLiveData<List<String>> {
        val keywordList = MutableLiveData<List<String>>()
        val tempList = RetrofitUtil().getRetrofitService().getTrendKeywords(RetrofitUtil.API_KEY)
            .subscribeOn(Schedulers.newThread()).blockingGet().data

        keywordList.value = tempList
        return keywordList
    }
}