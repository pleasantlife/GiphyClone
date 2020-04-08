package com.gandan.giphyclone.view.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.RequestManager
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import kotlinx.android.synthetic.main.viewpager_gif_image.view.*

class DetailViewPagerAdapter(private val list: ArrayList<Data>,
                             private val context: Context,
                             private val requestManager: RequestManager,
                             private val density: Int): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.viewpager_gif_image, container, false)
        val data = list[position]
        val width = data.images.original.width.toInt() * density
        val height = data.images.original.height.toInt() * density
        val url = data.images.original.url
        view.detailGifImage.layoutParams = ViewGroup.LayoutParams(width, height)
        requestManager.load(url).override(width, height).into(view.detailGifImage)
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }
}