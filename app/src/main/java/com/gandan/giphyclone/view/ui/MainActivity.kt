package com.gandan.giphyclone.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.ImageURLListener
import com.gandan.giphyclone.util.ItemClickListener
import com.gandan.giphyclone.view.adapter.TrendingAdapter
import com.gandan.giphyclone.view.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(){

    //private val compositeDisposable = CompositeDisposable()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var trendingAdapter : TrendingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
}
