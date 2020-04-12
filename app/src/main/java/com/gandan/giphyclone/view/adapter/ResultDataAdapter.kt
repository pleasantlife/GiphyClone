package com.gandan.giphyclone.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.GifItemClickListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class ResultDataAdapter(private val requestManager: RequestManager,
                        private val gifItemClickListener: GifItemClickListener,
                        private val density: Int)
    : PagedListAdapter<Data, ResultDataAdapter.MainHolder>(
    DIFF_CALLBACK
) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Data>() {
            override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MainHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        if (getItem(position) != null) {
            holder.bind(
                getItem(position)!!,
                requestManager,
                gifItemClickListener,
                position,
                density
            )
        }
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: Data,
                 requestManager: RequestManager,
                 gifItemClickListener: GifItemClickListener,
                 position: Int,
                 density: Int){

            val url = data.images.fixedWidthDownsampled.url
            val width = (data.images.fixedWidthDownsampled.width.toInt() * density)
            val height = (data.images.fixedWidthDownsampled.height.toInt() * density)


            itemView.imageItem.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)

            var placeHolderImage = 0

            when(position%4) {
                0 -> placeHolderImage = R.drawable.loading_purple
                1 -> placeHolderImage = R.drawable.loading_blue
                2 -> placeHolderImage = R.drawable.loading_yellow
                3 -> placeHolderImage = R.drawable.loading_green
            }

            requestManager.load(url).override(width, height).placeholder(placeHolderImage).into(itemView.imageItem)
            itemView.imageItem.setOnClickListener {
                gifItemClickListener.movePage("gifId", data.id, position)
            }
        }
    }
}