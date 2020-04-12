package com.gandan.giphyclone.view.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.TrendingDataRepository
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.view.adapter.ResultDataAdapter
import com.gandan.giphyclone.view.viewmodelfactory.DetailViewModelFactory
import com.gandan.giphyclone.view.adapter.DetailViewPagerAdapter
import com.gandan.giphyclone.view.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var relatedAdapter: ResultDataAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var trendingDataRepository: TrendingDataRepository
    private lateinit var detailView: View
    private var favoriteIdList = ArrayList<String>()
    private var gifId = ""
    private var currentId = ""
    private val apiService = RetrofitUtil().getRetrofitService()

    fun loadIdToList() {
        val favoriteIdStr = sharedPreferences.getString("favoriteIdList", "")!!
        if(favoriteIdStr.isNotEmpty()){
            favoriteIdList = ArrayList(favoriteIdStr.split(","))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailView = inflater.inflate(R.layout.detail_fragment, container, false)
        sharedPreferences = context?.getSharedPreferences("giphyclone", MODE_PRIVATE)!!
        loadIdToList()
        gifId = arguments?.getString("gifId", "")!!
        val list = arguments?.get("list") as ArrayList<Data>
        val startPosition = arguments?.get("startPosition") as Int
        val requestManager= Glide.with(this)
        trendingDataRepository = TrendingDataRepository(apiService, "gifs")
        detailView.searchBtn.setOnClickListener {
            Navigation.findNavController(detailView).navigate(R.id.action_detailFragment_to_searchFragment)
        }

        checkFavorite(gifId, detailView)


        detailView.detailViewPager.apply {
            adapter = DetailViewPagerAdapter(
                list,
                context!!,
                requestManager,
                resources.displayMetrics.density.toInt()
            )
            pageMargin = resources.getDimension(R.dimen.viewPagerMargin).toInt()
        }
        detailView.detailViewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                val data = list[position]
                currentId = data.id
                if(data.username.isEmpty()){
                    detailView.profileNick.text = resources.getText(R.string.source)
                } else {
                    detailView.profileNick.text = data.username
                }
                if(data.source.isEmpty()){
                    detailView.sourceTxt.text = data.sourcePostUrl
                } else {
                    detailView.sourceTxt.text = data.source
                }
                checkFavorite(currentId, detailView)
            }

        })
        detailView.detailViewPager.currentItem = startPosition
        if(detailView.detailViewPager.currentItem == 0){
            val data = list[0]
            currentId = data.id
            if(data.username.isEmpty()){
                detailView.profileNick.text = resources.getText(R.string.source)
            } else {
                detailView.profileNick.text = data.username
            }
            if(data.source.isEmpty()){
                detailView.sourceTxt.text = data.sourcePostUrl
            } else {
                detailView.sourceTxt.text = data.source
            }
        }
        detailView.favoriteBtn.setOnClickListener {
            if(favoriteIdList.contains(currentId)){
                favoriteIdList.remove(currentId)
                detailView.favoriteBtn.setColorFilter(Color.WHITE)
                refreshFavoriteIdData()
            } else {
                favoriteIdList.add(currentId)
                detailView.favoriteBtn.setColorFilter(Color.RED)
                refreshFavoriteIdData()
            }

        }
        bindUI(detailView)
        return detailView
    }

    fun checkFavorite(id: String, detailView: View){
        detailView.favoriteBtn.clearColorFilter()
        if(favoriteIdList.contains(id)){
            detailView.favoriteBtn.setColorFilter(Color.RED)
        } else {
            detailView.favoriteBtn.setColorFilter(Color.WHITE)
        }
    }

    fun refreshFavoriteIdData(){

        var favoriteIdStr = "";
        for(i in 0 until favoriteIdList.size){
            favoriteIdStr += if(i == favoriteIdList.size-1){
                favoriteIdList[i]
            } else {
                favoriteIdList[i]+","
            }
        }
        val editor = sharedPreferences.edit()
        editor.putString("favoriteIdList", favoriteIdStr)
        editor.apply()
        loadIdToList()
    }

    fun bindUI(detailView: View){
        val requestManager = Glide.with(this)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        relatedAdapter = ResultDataAdapter(
            requestManager,
            this,
            resources.displayMetrics.density.toInt()
        )
        detailView.backBtnImg.setOnClickListener {
            Navigation.findNavController(detailView).navigateUp()
        }
        detailView.relatedRecycler.apply {
            layoutManager = staggeredGridLayoutManager
            adapter = relatedAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,
            DetailViewModelFactory(
                trendingDataRepository
            )
        ).get(
            DetailViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.trendingDataList.observe(viewLifecycleOwner, Observer {
            relatedAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            if(it == NetworkState.ERROR){
                detailView.errorText.visibility = View.VISIBLE
            } else {
                detailView.errorText.visibility = View.GONE
            }
        })
    }

    override fun movePage(type: String, id: String, position: Int) {
        var startPoint = 0
        var newPosition = position
        if(position - 30 > 0){
            startPoint = position - 30
            newPosition = 30
        }
        val endPoint = if(position + 30 > relatedAdapter.currentList?.toList()!!.size){
            relatedAdapter.currentList?.toList()!!.size-1
        } else {
            position+30
        }

        val gifList = ArrayList<Data>()
        for(i in startPoint..endPoint){
            gifList.add(relatedAdapter.currentList!![i]!!)
        }
        val bundle = bundleOf("gifId" to id, "list" to gifList, "startPosition" to newPosition)
        Navigation.findNavController(detailView).navigate(R.id.action_detailFragment_self, bundle)
    }

}
