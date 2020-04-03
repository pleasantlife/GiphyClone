package com.gandan.giphyclone.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gandan.giphyclone.R
import kotlinx.android.synthetic.main.recycler_search_item.view.*

class PopularAdapter(private var keyWordList: ArrayList<String>) : RecyclerView.Adapter<PopularAdapter.PopularHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_search_item, parent, false)
        return PopularHolder(view)
    }

    fun getData(keyWordList: ArrayList<String>){
        this.keyWordList = keyWordList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return keyWordList.size
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.bindText(keyWordList[position])
    }

    class PopularHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindText(keyword: String){
            itemView.searchText.text = "#"+keyword
        }

    }
}