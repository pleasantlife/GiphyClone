package com.gandan.giphyclone.view.searchresult

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.view.trending.ResultDataAdapter
import kotlinx.android.synthetic.main.search_result_fragment.view.*

class SearchResultFragment : Fragment(), GifItemClickListener {

    companion object {
        fun newInstance() =
            SearchResultFragment()
    }

    private lateinit var keyword: String
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var searchResultView: View
    private lateinit var inputManager: InputMethodManager
    private lateinit var resultDataAdapter: ResultDataAdapter
    private var type: String = "gifs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchResultView = inflater.inflate(R.layout.search_result_fragment, container, false)

        keyword = arguments?.get("keyword").toString()
        type = arguments?.get("type").toString()
        viewModel = SearchResultViewModel()

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val requestManager = Glide.with(this)
        val density = resources.displayMetrics.density.toInt()
        resultDataAdapter = ResultDataAdapter(requestManager, this, density)

        searchResultView.resultRecycler.apply {
            adapter = resultDataAdapter
            layoutManager = staggeredGridLayoutManager
        }
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

        when(type)
        {
            "gifs" -> clickGifBtn()
            "stickers" -> clickStickerBtn()
        }
        searchResultView.gifBtn.setOnClickListener {
            clickGifBtn()
        }
        searchResultView.stickerBtn.setOnClickListener {
            clickStickerBtn()
        }
        searchResultView.searchBtn.setOnClickListener {
            val newKeyword = searchResultView.keywordEditText.text.toString()
            val bundle = bundleOf("keyword" to keyword)
//            if(newKeyword != keyword && newKeyword.isNotEmpty()) {
//                val bundle = bundleOf("keyword" to newKeyword)
//                Navigation.findNavController(searchResultView)
//                    .navigate(R.id.action_searchResultFragment_self, bundle)
//            }
            Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_searchFragment, bundle)
        }

        bindUI()

        return searchResultView
    }

    fun clickGifBtn(){
        type = "gifs"
        searchResultView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchResultView.stickerBtn.setTextColor(Color.WHITE)
        searchResultView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchResultView.gifBtn.setTextColor(Color.BLACK)
        changeObserver(type)
    }

    fun clickStickerBtn(){
        type = "stickers"
        searchResultView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchResultView.stickerBtn.setTextColor(Color.BLACK)
        searchResultView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchResultView.gifBtn.setTextColor(Color.WHITE)
        changeObserver(type)
    }

    fun bindUI(){

        searchResultView.resultRecycler.setOnTouchListener { view, motionEvent ->
            inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
    }

    fun changeObserver(type: String){
        resultDataAdapter.submitList(null)
        viewModel.getSearchResult(keyword, type).observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchResultViewModel::class.java)
        inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun movePage(type: String, id: String) {
        val bundle = bundleOf("gifId" to id, "list" to resultDataAdapter.currentList?.toList())
        Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_detailFragment, bundle)
    }

}
