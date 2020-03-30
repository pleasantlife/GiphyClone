package com.gandan.giphyclone.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.Downsized
import com.gandan.giphyclone.data.repository.LoadRepository
import com.gandan.giphyclone.util.ImageURLListener
import com.gandan.giphyclone.view.adapter.MainAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImageURLListener {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var mainAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val requestManager = Glide.with(this)
        val urlList = ArrayList<Downsized>()
        mainAdapter = MainAdapter(urlList, requestManager)



        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = mainAdapter
        }

        LoadRepository(compositeDisposable, this).getGifData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun getImageUrlList(urlList : ArrayList<Downsized>) {
        mainAdapter.addData(urlList)
        Log.e("urlSize", "${urlList.size}")
    }
}
