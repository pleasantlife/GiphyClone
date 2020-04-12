package com.gandan.giphyclone.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.SearchKeywordItemClickListener
import kotlinx.android.synthetic.main.recycler_recent_keyword_chip.view.*
import kotlinx.android.synthetic.main.recycler_related_keyword_chip.view.*

class SuggestKeywordRecyclerAdapter(private val searchKeywordItemClickListener: SearchKeywordItemClickListener):
    PagedListAdapter<String, SuggestKeywordRecyclerAdapter.SuggestKeywordHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestKeywordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_related_keyword_chip, parent, false)
        return SuggestKeywordHolder(
            view
        )
    }


    override fun onBindViewHolder(holder: SuggestKeywordHolder, position: Int) {
        holder.bind(getItem(position)!!, searchKeywordItemClickListener)
    }

    class SuggestKeywordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(keyword: String, searchKeywordItemClickListener: SearchKeywordItemClickListener){
            itemView.relatedKeywordChip.text = "#"+keyword
            itemView.relatedKeywordChip.setOnClickListener {
                searchKeywordItemClickListener.moveSearchResult(keyword)
            }
        }
    }
}