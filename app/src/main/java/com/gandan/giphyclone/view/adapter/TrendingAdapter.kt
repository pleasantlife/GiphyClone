package com.gandan.giphyclone.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.FixedDownsampled
import com.gandan.giphyclone.util.ItemClickListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class TrendingAdapter(private var urlList: ArrayList<FixedDownsampled>,
                      private val requestManager: RequestManager,
                      private val itemClickListener: ItemClickListener,
                      private val density: Int)
    : RecyclerView.Adapter<TrendingAdapter.MainHolder>() {


    fun addData(urlList: ArrayList<FixedDownsampled>){
        this.urlList = urlList
        urlList.addAll(urlList)
        notifyItemRangeChanged(0, urlList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(view)
    }

    override fun getItemCount(): Int {
        return urlList.size
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        holder.bind(urlList[position], requestManager, itemClickListener, position, density)
        //notifyItemInserted(position)
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(fixedWidthDownsampled: FixedDownsampled,
                 requestManager: RequestManager,
                 itemClickListener: ItemClickListener,
                 position: Int,
                 density: Int){

            val width = (fixedWidthDownsampled.width.toInt() * density)
            val height = (fixedWidthDownsampled.height.toInt() * density)


            itemView.imageItem.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)


            var placeHolderImage = 0

            when(position%4) {
                0 -> placeHolderImage = R.drawable.loading_purple
                1 -> placeHolderImage = R.drawable.loading_blue
                2 -> placeHolderImage = R.drawable.loading_yellow
                3 -> placeHolderImage = R.drawable.loading_green
            }


            requestManager.load(fixedWidthDownsampled.url).override(width, height).placeholder(placeHolderImage).into(itemView.imageItem)
            itemView.imageItem.setOnClickListener {
                itemClickListener.movePage("gif", fixedWidthDownsampled.id)
            }
        }
    }
}