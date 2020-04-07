package com.gandan.giphyclone.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.paging.PagedList
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import com.gandan.giphyclone.view.trending.ResultDataAdapter
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.detail_fragment.view.*
import kotlin.math.roundToInt

class DetailFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var relatedAdapter: ResultDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailView = inflater.inflate(R.layout.detail_fragment, container, false)
        bindUI(detailView)
        return detailView
    }

    fun bindUI(detailView: View){
        val requestManager = Glide.with(this)
        relatedAdapter = ResultDataAdapter(requestManager, this, resources.displayMetrics.density.toInt())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun movePage(type: String, id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
