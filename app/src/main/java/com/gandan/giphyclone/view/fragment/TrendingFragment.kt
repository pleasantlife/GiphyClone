package com.gandan.giphyclone.view.fragment

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
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.view.adapter.ResultDataAdapter
import com.gandan.giphyclone.view.viewmodelfactory.TrendingViewModelFactory
import com.gandan.giphyclone.view.viewmodel.TrendingViewModel
import kotlinx.android.synthetic.main.trending_fragment.view.*
import kotlin.math.roundToInt

class TrendingFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() =
            TrendingFragment()
    }

    private lateinit var viewModel: TrendingViewModel
    private lateinit var resultDataAdapter: ResultDataAdapter
    private lateinit var trendingDataRepository: TrendingDataRepository
    private lateinit var trendingView: View
    private var type = "gifs"
    private val apiService = RetrofitUtil().getRetrofitService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trendingView = inflater.inflate(R.layout.trending_fragment, container, false)
        trendingDataRepository = TrendingDataRepository(apiService, type)

        trendingView.textBtn.setOnClickListener {
            moveToSearchPage()
        }
        trendingView.searchBtn.setOnClickListener {
            moveToSearchPage()
        }

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

        return trendingView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            TrendingViewModelFactory(
                trendingDataRepository
            )
        ).get(
            TrendingViewModel::class.java)
        viewModel.trendingDataList.observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            if(it == NetworkState.ERROR){
                trendingView.errorText.visibility = View.VISIBLE
            } else {
                trendingView.errorText.visibility = View.GONE
            }
        })
    }



    private fun moveToSearchPage(){
        Navigation.findNavController(trendingView).navigate(R.id.action_trendingFragment_to_searchFragment)
    }

    override fun movePage(type: String, id: String, position: Int) {
        //현재 선택한 아이템에서 앞선 30개(최대)의 아이템과 뒤에 있는 30개(최대)의 아이템만 가져온다.
        //모두 가져오게 되면 Parcel 용량이 커져서 에러로 강제종
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
