package com.gandan.giphyclone.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.SearchKeywordItemClickListener
import kotlinx.android.synthetic.main.recycler_recent_keyword_chip.view.*

class RecentKeywordRecyclerAdapter(private val keywordList: ArrayList<String>,
                                   private val searchKeywordItemClickListener: SearchKeywordItemClickListener): RecyclerView.Adapter<RecentKeywordRecyclerAdapter.RecentKeywordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentKeywordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_recent_keyword_chip, parent, false)
        return RecentKeywordHolder(view)
    }

    override fun getItemCount(): Int {
        return keywordList.size
    }

    override fun onBindViewHolder(holder: RecentKeywordHolder, position: Int) {
        holder.bind(keywordList[position], searchKeywordItemClickListener)
    }

    class RecentKeywordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(keyword: String, searchKeywordItemClickListener: SearchKeywordItemClickListener){
            itemView.recentChip.text = keyword
            itemView.recentChip.setOnClickListener {
                searchKeywordItemClickListener.moveSearchResult(keyword)
            }
        }
    }
}