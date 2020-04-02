package com.gandan.giphyclone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gandan.giphyclone.data.model.FixedDownsampled
import com.gandan.giphyclone.util.ImageURLListener
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoadRepository() {

    fun getGifData() : MutableLiveData<List<FixedDownsampled>>{
        val urlList = MutableLiveData<List<FixedDownsampled>>()
        val forList = ArrayList<FixedDownsampled>()
        val dataList = RetrofitUtil().getRetrofitService().getGifTrending(RetrofitUtil.API_KEY, 500)
            .subscribeOn(Schedulers.newThread()).blockingFirst()

        for(data in dataList.data){
            forList.add(data.images.fixedWidthDownsampled)
        }
        urlList.value = forList

        return urlList
    }
}