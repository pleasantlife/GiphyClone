package com.gandan.giphyclone.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.SearchItemClickListener
import com.gandan.giphyclone.view.search.SearchFragment.Companion.TRENDING_KEYWORD
import kotlinx.android.synthetic.main.recycler_search_item.view.*

class SearchKeywordAdapter(private var keyWordList: ArrayList<String>,
                           private val searchItemClickListener: SearchItemClickListener) : RecyclerView.Adapter<SearchKeywordAdapter.PopularHolder>() {

    private var type = "trendingKeyword"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_search_item, parent, false)
        return PopularHolder(view)
    }

    fun getData(keyWordList: ArrayList<String>, type: String){
        this.type = type
        this.keyWordList = keyWordList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return keyWordList.size
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.bind(keyWordList[position], type, searchItemClickListener)
    }

    class PopularHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(keyword: String, type: String, searchItemClickListener: SearchItemClickListener){
            if(type == TRENDING_KEYWORD){
                itemView.searchText.text = "#"+keyword
                itemView.searchItemImage.setImageResource(R.drawable.ic_trending_up_white_48dp)
                itemView.searchItemImage.setColorFilter(Color.parseColor("#00bcd4"))
            } else {
                itemView.searchText.text = keyword
                itemView.searchItemImage.setImageResource(R.drawable.ic_search_white_48dp)
                itemView.searchItemImage.colorFilter = null
            }
            itemView.setOnClickListener {
                searchItemClickListener.moveSearchResult(keyword)
            }

        }

    }
}