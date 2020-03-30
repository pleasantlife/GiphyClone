package com.gandan.giphyclone.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.repository.LoadRepository
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadRepository(compositeDisposable).getGifData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
