package com.gandan.giphyclone.view.trending

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.view.ResultDataAdapter
import kotlinx.android.synthetic.main.fragment_trending.view.*
import kotlin.math.roundToInt

class TrendingFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() =
            TrendingFragment()
    }

    private lateinit var viewModel: TrendingViewModel
    private lateinit var resultDataAdapter: ResultDataAdapter
    private lateinit var trendingView : View
    private var type = "gifs"
    private var offset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendingView = inflater.inflate(R.layout.fragment_trending, container, false)

        trendingView.textBtn.setOnClickListener {
            Navigation.findNavController(trendingView).navigate(R.id.action_trendingFragment_to_searchFragment)
        }
        trendingView.searchBtn.setOnClickListener {
            Navigation.findNavController(trendingView).navigate(R.id.action_trendingFragment_to_searchFragment)
        }

        bindUI()


        return trendingView
    }

    private fun bindUI(){
        val requestManager = Glide.with(context!!)
        resultDataAdapter = ResultDataAdapter(
            requestManager,
            this,
            resources.displayMetrics.density.roundToInt()
        )
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        trendingView.recyclerTrending.apply {
            layoutManager = gridLayoutManager
            adapter = resultDataAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrendingViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getTrendingDataList(type).observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
    }

    override fun movePage(type: String, id: String, position: Int) {
        var startPoint = 0
        var newPosition = position
        if(position - 30 > 0){
            startPoint = position - 30
            newPosition = 30
        }
        val endPoint = if(position + 30 > resultDataAdapter.currentList?.toList()!!.size){
            resultDataAdapter.currentList?.toList()!!.size-1
        } else {
            position+30
        }

        val gifList = ArrayList<Data>()
        for(i in startPoint..endPoint){
            gifList.add(resultDataAdapter.currentList!![i]!!)
        }
        val bundle = bundleOf("gifId" to id, "list" to gifList, "startPosition" to newPosition)
        Navigation.findNavController(trendingView).navigate(R.id.action_trendingFragment_to_detailFragment, bundle)
    }


}
