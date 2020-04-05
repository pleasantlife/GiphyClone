package com.gandan.giphyclone.view.searchresult

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.marginTop
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.view.trending.ResultDataAdapter
import kotlinx.android.synthetic.main.fragment_trending.view.*
import kotlinx.android.synthetic.main.search_result_fragment.view.*
import kotlinx.android.synthetic.main.search_result_fragment.view.searchBtn
import kotlin.math.roundToInt

class SearchResultFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() =
            SearchResultFragment()
    }

    private lateinit var viewModel: SearchResultViewModel
    private lateinit var keyword: String
    private lateinit var resultDataAdapter: ResultDataAdapter
    private lateinit var searchResultView: View
    private lateinit var inputManager: InputMethodManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchResultView = inflater.inflate(R.layout.search_result_fragment, container, false)



        keyword = arguments?.get("keyword").toString()
        searchResultView.searchResultTitle.text = keyword
        searchResultView.keywordEditText.setText(keyword)
        searchResultView.backBtnImg.setOnClickListener {
            Navigation.findNavController(searchResultView).navigateUp()
        }
        searchResultView.keywordEditText.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if(!hasFocus){
                    inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
                }
            }
        searchResultView.searchBtn.setOnClickListener {
            val newKeyword = searchResultView.keywordEditText.text.toString()
            if(newKeyword != keyword && newKeyword.isNotEmpty()) {
                val bundle = bundleOf("keyword" to newKeyword)
                Navigation.findNavController(searchResultView)
                    .navigate(R.id.action_searchResultFragment_self, bundle)
            }
        }

        bindUI()

        //val recentLp = searchResultView.recentConstraint.layoutParams as ConstraintLayout.LayoutParams
        //val margin = searchResultView.recentConstraint.marginTop
        //var scrollAmountBefore = 0

        searchResultView.recyclerResult.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })

        return searchResultView
    }

    fun bindUI(){
        val requestManager = Glide.with(context!!)
        resultDataAdapter = ResultDataAdapter(
            requestManager,
            this,
            resources.displayMetrics.density.roundToInt()
        )
        val gridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        searchResultView.recyclerResult.apply {
            layoutManager = gridLayoutManager
            adapter = resultDataAdapter
        }
        searchResultView.recyclerResult.setOnTouchListener { view, motionEvent ->
            inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.getSearchResult(keyword, "gifs").observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
        inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun movePage(type: String, id: String) {
        val bundle = bundleOf("gifId" to id)
        Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_detailFragment, bundle)
    }

}
