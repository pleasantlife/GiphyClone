package com.gandan.giphyclone.view.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.KeywordClickListener
import kotlinx.android.synthetic.main.search_fragment.view.*

class SearchFragment : Fragment(), KeywordClickListener {

    companion object {
        fun newInstance() = SearchFragment()
        private val PRIVATE_MODE = 0
        private val PREF_NAME = "giphyclone"
    }

    private lateinit var searchingView: View
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchKeywordAdapter: SearchKeywordAdapter
    private lateinit var inputManager: InputMethodManager
    private val keywordList = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchingView = inflater.inflate(R.layout.search_fragment, container, false)
        searchKeywordAdapter = SearchKeywordAdapter(keywordList, this)
        bindUI()
        return searchingView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.getKeywords().observe(viewLifecycleOwner, Observer {
            setRecyclerData(ArrayList(it), "trendingKeyword")
        })
        viewModel.getRecentKeywords(context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE))
            .observe(viewLifecycleOwner, Observer{
                setRecentKeywordUI(ArrayList(it))
            })
        inputManager = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

    }

    private fun bindUI(){
        searchingView.backBtnImg.setOnClickListener {
            Navigation.findNavController(searchingView).navigateUp()
        }
        searchingView.keywordEditText.doAfterTextChanged { it ->
            if(it!!.length > 2){
                viewModel.getAutoKeywords(it.toString()).observe(viewLifecycleOwner, Observer { list ->
                    setRecyclerData(ArrayList(list), "searchKeyword")
                })
                setHeaderUI(searchingView, false)
            } else {
                viewModel.getKeywords().observe(viewLifecycleOwner, Observer { list ->
                    setRecyclerData(ArrayList(list), "trendingKeyword")
                })
                setHeaderUI(searchingView, true)
            }
        }
        searchingView.searchBtn.setOnClickListener {
            if(searchingView.keywordEditText.text.isNotEmpty()) {

                getKeyword(searchingView.keywordEditText.text.toString())
            }
        }

        searchingView.popularRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchKeywordAdapter
        }
    }

    private fun setRecentKeywordUI(keywordList: ArrayList<String>){

    }

    private fun setRecyclerData(keywordList: ArrayList<String>, type: String) {
        searchKeywordAdapter.getData(keywordList, type)
    }

    private fun setHeaderUI(searchingView: View, isVisible: Boolean){
        if(isVisible){
            searchingView.recentTitle.visibility = View.VISIBLE
            searchingView.recentScrollView.visibility = View.VISIBLE
            searchingView.popularTitle.visibility = View.VISIBLE
        } else {
            searchingView.recentTitle.visibility = View.GONE
            searchingView.recentScrollView.visibility = View.GONE
            searchingView.popularTitle.visibility = View.GONE
        }
    }

    override fun getKeyword(keyword: String) {
        val bundle = bundleOf("keyword" to keyword)
        searchingView.keywordEditText.text.clear()
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        Navigation.findNavController(searchingView).navigate(R.id.action_searchFragment_to_searchResultFragment, bundle)
    }
}
