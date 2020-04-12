package com.gandan.giphyclone.view.fragment

import android.content.Context
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
import com.gandan.giphyclone.data.repository.FavoriteSourceRepository
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.view.adapter.ResultDataAdapter
import com.gandan.giphyclone.view.viewmodelfactory.FavoriteViewModelFactory
import com.gandan.giphyclone.view.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.favorite_fragment.view.*

class FavoriteFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() =
            FavoriteFragment()
    }

    private lateinit var favoriteView: View
    private lateinit var viewModel: FavoriteViewModel
    private val apiService = RetrofitUtil().getRetrofitService()
    private lateinit var resultDataAdapter: ResultDataAdapter
    private var idList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteView = inflater.inflate(R.layout.favorite_fragment, container, false)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val requestManager = Glide.with(this)
        resultDataAdapter = ResultDataAdapter(
            requestManager,
            this,
            resources.displayMetrics.density.toInt()
        )
        favoriteView.favoriteRecycler.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = resultDataAdapter
        }
        favoriteView.searchBtn.setOnClickListener {
            val beforePage = "favorite"
            val bundle = bundleOf("beforePage" to beforePage)
            Navigation.findNavController(favoriteView).navigate(R.id.action_favoriteFragment_to_searchFragment, bundle)
        }
        return favoriteView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sharedPreferences = context?.getSharedPreferences("giphyclone",
            Context.MODE_PRIVATE
        )!!
        val favoriteIdStr: String = sharedPreferences.getString("favoriteIdList", " ")!!
        if(favoriteIdStr.isNotEmpty()){
           idList = ArrayList(favoriteIdStr.trim().splitToSequence(',').filter { it.isNotEmpty() }.toList())
        }

        val favoriteSourceRepository = FavoriteSourceRepository(apiService, idList)
        viewModel = ViewModelProvider(this,
            FavoriteViewModelFactory(
                favoriteSourceRepository
            )
        ).get(
            FavoriteViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.favoriteDataList.observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            if(it == NetworkState.ERROR && idList.size > 0){
                favoriteView.errorText.visibility = View.VISIBLE
            } else {
                favoriteView.errorText.visibility = View.GONE
            }
        })
    }

    override fun movePage(type: String, id: String, position: Int) {
        var startPoint = 0
        val endPoint: Int
        var newPosition = position
        if(position - 30 > 0){
            startPoint = position - 30
            newPosition = 30
        }
        if(position + 30 > resultDataAdapter.currentList?.toList()!!.size){
            endPoint = resultDataAdapter.currentList?.toList()!!.size-1
        } else {
            endPoint = position+30
        }

        val gifList = ArrayList<Data>()
        for(i in startPoint..endPoint){
            gifList.add(resultDataAdapter.currentList!![i]!!)
        }
        val beforePage = "favorite"
        val bundle = bundleOf("gifId" to id, "list" to gifList, "startPosition" to newPosition, "beforePage" to beforePage)
        Navigation.findNavController(favoriteView).navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
    }

}
