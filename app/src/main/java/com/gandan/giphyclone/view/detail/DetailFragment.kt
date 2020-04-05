package com.gandan.giphyclone.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.detail_fragment.view.*
import kotlin.math.roundToInt

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val detailView = inflater.inflate(R.layout.detail_fragment, container, false)
        detailView.backBtnImg.setOnClickListener {
            Navigation.findNavController(detailView).navigateUp()
        }
        val gifId = arguments?.get("gifId").toString()
        Log.e("gifId", gifId.toString())

        val data = RetrofitUtil().getRetrofitService().getGifDetail(gifId, API_KEY).subscribeOn(Schedulers.newThread()).blockingGet().data
        val detailGifView = detailView.detailGifImage
        val densityInt = resources.displayMetrics.density.roundToInt()
        val width = data.images.original.width.toInt() * densityInt
        val height = data.images.original.height.toInt() * densityInt

        Glide.with(this).load(data.images.original.url).into(detailView.detailGifImage)

        return detailView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
