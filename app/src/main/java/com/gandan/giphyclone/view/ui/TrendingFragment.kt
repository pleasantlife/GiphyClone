package com.gandan.giphyclone.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.FixedDownsampled
import com.gandan.giphyclone.util.ItemClickListener
import com.gandan.giphyclone.view.adapter.TrendingAdapter
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlin.math.roundToInt

class TrendingFragment : Fragment(), ItemClickListener {

    companion object {
        fun newInstance() = TrendingFragment()
    }

    private lateinit var viewModel: TrendingViewModel
    private lateinit var trendingAdapter: TrendingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    private fun bindUI(urlList: List<FixedDownsampled>){
        Log.e("hello~~", "??")
        val testList = ArrayList(urlList)
        val requestManager = Glide.with(this)
        Log.e("density", resources.displayMetrics.density.roundToInt().toString())
        trendingAdapter = TrendingAdapter(testList, requestManager, this, resources.displayMetrics.density.roundToInt())
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerTrending.apply {
            layoutManager = gridLayoutManager
            adapter = trendingAdapter
        }
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getTredningGifData().observe(viewLifecycleOwner, Observer {
            bindUI(it)
        })
    }

    override fun setToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
