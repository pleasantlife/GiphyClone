package com.gandan.giphyclone.data.repository

import android.util.Log
import com.gandan.giphyclone.data.model.FixedDownsampled
import com.gandan.giphyclone.util.ImageURLListener
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoadRepository(private val compositeDisposable: CompositeDisposable,
                     private val imageURLListener: ImageURLListener) {

    fun getGifData(){
        val urlList = ArrayList<FixedDownsampled>()
        compositeDisposable.add(
            RetrofitUtil().getRetrofitService().getGifTrending(RetrofitUtil.API_KEY, 500)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    for(data in it.data){
                        urlList.add(data.images.fixedWidthDownsampled)
                    }
            },{
                Log.e("Error", "${it.message}")
            },{
                    imageURLListener.getImageUrlList(urlList)
                })
        )
    }
}