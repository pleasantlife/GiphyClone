package com.gandan.giphyclone.view.searchresult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.gandan.giphyclone.R
import kotlinx.android.synthetic.main.search_result_fragment.view.*

class SearchResultFragment : Fragment() {

    companion object {
        fun newInstance() =
            SearchResultFragment()
    }

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var keyword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val searchResultView = inflater.inflate(R.layout.search_result_fragment, container, false)

        keyword = arguments?.get("keyword").toString()
        searchResultView.searchResultTitle.text = keyword
        searchResultView.keywordEditText.setText(keyword)
        searchResultView.searchBtn.setOnClickListener {

            val bundle = bundleOf("keyword" to keyword+1)
            Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_self, bundle)
        }

        return searchResultView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
