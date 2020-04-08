package com.gandan.giphyclone.view.detail

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.view.ResultDataAdapter
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var relatedAdapter: ResultDataAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private var favoriteIdList = ArrayList<String>()
    private var gifId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailView = inflater.inflate(R.layout.detail_fragment, container, false)
        sharedPreferences = context?.getSharedPreferences("giphyclone", MODE_PRIVATE)!!
        val favoriteIdStr = sharedPreferences.getString("favoriteIdList", " ")!!
        if(favoriteIdStr.isNotEmpty()){
            favoriteIdList = ArrayList(favoriteIdStr.trim().splitToSequence(',').filter { it.isNotEmpty() }.toList())
        }
        gifId = arguments?.getString("gifId", "")!!
        val list = arguments?.get("list") as ArrayList<Data>
        val startPosition = arguments?.get("startPosition") as Int
        val requestManager= Glide.with(this)
        detailView.searchBtn.setOnClickListener {
            Navigation.findNavController(detailView).navigate(R.id.action_detailFragment_to_searchFragment)
        }

        checkFavorite(gifId, favoriteIdStr, detailView)


        detailView.detailViewPager.apply {
            adapter = DetailViewPagerAdapter(list, context!!, requestManager, resources.displayMetrics.density.toInt())
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
                checkFavorite(data.id, favoriteIdStr, detailView)
            }

        })
        detailView.detailViewPager.currentItem = startPosition
        if(detailView.detailViewPager.currentItem == 0){
            val data = list[0]
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
            if(favoriteIdList.contains(gifId)){
                favoriteIdList.remove(gifId)
                refreshFavoriteIdData()
                checkFavorite(gifId, favoriteIdStr, detailView)
            } else {
                favoriteIdList.add(gifId)
                refreshFavoriteIdData()
                checkFavorite(gifId, favoriteIdStr, detailView)
            }

        }
        bindUI(detailView)
        return detailView
    }

    fun checkFavorite(id: String, favoriteIdStr: String, detailView: View){
        detailView.favoriteBtn.clearColorFilter()
        if(favoriteIdStr.contains(id)){
            detailView.favoriteBtn.setColorFilter(Color.RED)
        } else {
            detailView.favoriteBtn.setColorFilter(Color.WHITE)
        }
    }

    fun refreshFavoriteIdData(){
        val editor = sharedPreferences.edit()
        editor.putString("favoriteIdList", favoriteIdList.joinToString(","))
        editor.apply()
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
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getTrendingDataList("gifs").observe(viewLifecycleOwner, Observer {
            relatedAdapter.submitList(it)
        })
    }

    override fun movePage(type: String, id: String, position: Int) {

    }

}
