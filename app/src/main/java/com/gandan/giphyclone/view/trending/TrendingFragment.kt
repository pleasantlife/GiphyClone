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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.GifItemClickListener
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

    override fun movePage(type: String, id: String) {
        //startActivity(Intent(context, DetailActivity::class.java).putExtra("id", id).putExtra("type", type))
        //DetailFragment().show(parentFragmentManager, "dialog")
        val bundle = bundleOf("gifId" to id, "list" to resultDataAdapter.currentList?.toList())
        Navigation.findNavController(trendingView).navigate(R.id.action_trendingFragment_to_detailFragment, bundle)
    }


}
