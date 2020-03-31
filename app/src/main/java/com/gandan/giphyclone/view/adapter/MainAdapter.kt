package com.gandan.giphyclone.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.Downsized
import com.gandan.giphyclone.util.ItemClickListener
import kotlinx.android.synthetic.main.recycler_item.view.*

class MainAdapter(private var urlList: ArrayList<Downsized>,
                  private val requestManager: RequestManager,
                  private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<MainAdapter.MainHolder>() {


    fun addData(urlList: ArrayList<Downsized>){
        this.urlList = urlList
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

        holder.bind(urlList[position], requestManager, itemClickListener, position)
        //notifyItemInserted(position)
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(downsized: Downsized,
                 requestManager: RequestManager,
                 itemClickListener: ItemClickListener,
                 position: Int){

            var placeHolderImage = 0

            when(position%4) {
                0 -> placeHolderImage = R.drawable.loading_purple
                1 -> placeHolderImage = R.drawable.loading_blue
                2 -> placeHolderImage = R.drawable.loading_yellow
                3 -> placeHolderImage = R.drawable.loading_green
            }

            requestManager.load(downsized.url).override(downsized.width.toInt(), downsized.height.toInt()).placeholder(placeHolderImage).into(itemView.imageItem)
            itemView.imageItem.setOnClickListener {
                itemClickListener.setToast(downsized.url)
            }
        }
    }
}