package com.gandan.giphyclone.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.util.SearchKeywordItemClickListener
import com.gandan.giphyclone.view.search.SearchFragment.Companion.TRENDING_KEYWORD
import kotlinx.android.synthetic.main.recycler_search_item.view.*

class SearchKeywordAdapter(private val searchKeywordItemClickListener: SearchKeywordItemClickListener) : PagedListAdapter<String, SearchKeywordAdapter.PopularHolder>(
    DIFF_CALLBACK) {


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var type = "trendingKeyword"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_search_item, parent, false)
        return PopularHolder(view)
    }

//    fun getData(keyWordList: ArrayList<String>, type: String){
//        this.type = type
//        this.keyWordList = keyWordList
//        notifyDataSetChanged()
//    }

    fun getType(type: String){
        this.type = type
    }



    override fun onBindViewHolder(holder: PopularHolder, position: Int) {
        holder.bind(getItem(position)!!, type, searchKeywordItemClickListener)
    }

    class PopularHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(keyword: String, type: String, searchKeywordItemClickListener: SearchKeywordItemClickListener){
            if(type == TRENDING_KEYWORD){
                itemView.apply {
                    searchText.text = "#"+keyword
                    searchItemImage.apply {
                        setImageResource(R.drawable.ic_trending_up_white_48dp)
                        setColorFilter(Color.parseColor("#00bcd4"))
                    }
                }
            } else {
                itemView.apply{
                    searchText.text = keyword
                    searchItemImage.apply{
                        setImageResource(R.drawable.ic_search_white_48dp)
                        clearColorFilter()
                    }
                }
            }
            itemView.setOnClickListener {
                searchKeywordItemClickListener.moveSearchResult(keyword)
            }

        }

    }
}