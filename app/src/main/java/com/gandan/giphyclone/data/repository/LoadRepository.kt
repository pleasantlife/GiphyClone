package com.gandan.giphyclone.data.repository

import android.util.Log
import com.gandan.giphyclone.util.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoadRepository(private val compositeDisposable: CompositeDisposable) {

    fun getGifData(){
        compositeDisposable.add(
            RetrofitUtil().getRetrofitService().getGifTrending(RetrofitUtil.API_KEY)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                Log.e("string", "${it.data[0].bitlyUrl}")
            },{
                Log.e("Error", "${it.message}")
            })
        )
    }
}