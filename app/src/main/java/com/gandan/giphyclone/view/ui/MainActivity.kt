package com.gandan.giphyclone.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.Downsized
import com.gandan.giphyclone.data.repository.LoadRepository
import com.gandan.giphyclone.util.ImageURLListener
import com.gandan.giphyclone.util.ItemClickListener
import com.gandan.giphyclone.view.adapter.MainAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ImageURLListener, ItemClickListener {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var mainAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadRepository(compositeDisposable, this).getGifData()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun getImageUrlList(urlList : ArrayList<Downsized>) {
        val requestManager = Glide.with(this)
        mainAdapter = MainAdapter(urlList, requestManager, this)
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.apply {
            layoutManager = gridLayoutManager
            adapter = mainAdapter
        }
    }

    override fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
