package com.gandan.giphyclone.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.gandan.giphyclone.R
import com.gandan.giphyclone.view.adapter.PopularAdapter
import kotlinx.android.synthetic.main.search_fragment.view.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var popularAdapter: PopularAdapter
    private val keywordList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val searchingView = inflater.inflate(R.layout.search_fragment, container, false)
        popularAdapter = PopularAdapter(keywordList)

        searchingView.backBtnImg.setOnClickListener { Navigation.findNavController(searchingView).navigate(R.id.action_searchFragment_to_trendingFragment) }
        searchingView.keywordEditText.doAfterTextChanged {
            if(it!!.length > 2){

            }
        }

        searchingView.popularRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = popularAdapter
        }

        return searchingView
    }

    fun bindUI(keywordList: ArrayList<String>) {
        popularAdapter.getData(keywordList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getKeywords().observe(viewLifecycleOwner, Observer {
            bindUI(ArrayList(it))
        })
    }


}
