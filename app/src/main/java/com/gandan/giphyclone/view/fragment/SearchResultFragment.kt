package com.gandan.giphyclone.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide

import com.gandan.giphyclone.R
import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.repository.SearchResultRepository
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.NetworkState
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.SearchKeywordItemClickListener
import com.gandan.giphyclone.view.adapter.ResultDataAdapter
import com.gandan.giphyclone.view.adapter.SuggestKeywordRecyclerAdapter
import com.gandan.giphyclone.view.viewmodel.SearchResultViewModel
import com.gandan.giphyclone.view.viewmodelfactory.SearchResultViewModelFactory
import kotlinx.android.synthetic.main.search_result_fragment.view.*
import kotlinx.android.synthetic.main.search_result_fragment.view.backBtnImg
import kotlinx.android.synthetic.main.search_result_fragment.view.gifBtn
import kotlinx.android.synthetic.main.search_result_fragment.view.searchBtn
import kotlinx.android.synthetic.main.search_result_fragment.view.stickerBtn

class SearchResultFragment : Fragment(), GifItemClickListener, SearchKeywordItemClickListener{

    companion object {
        fun newInstance() =
            SearchResultFragment()
    }

    private lateinit var keyword: String
    private lateinit var viewModel: SearchResultViewModel
    private lateinit var searchResultView: View
    private lateinit var inputManager: InputMethodManager
    private lateinit var resultDataAdapter: ResultDataAdapter
    private lateinit var suggestKeywordRecyclerAdapter: SuggestKeywordRecyclerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var searchResultRepository: SearchResultRepository
    private var recentKeywordList = ArrayList<String>()
    private var type: String = "gifs"
    private val beforePage = "searchResult"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val apiService = RetrofitUtil().getRetrofitService()
        keyword = arguments?.get("keyword").toString()
        type = arguments?.get("type").toString()
        searchResultRepository = SearchResultRepository(apiService)
        searchResultView = inflater.inflate(R.layout.search_result_fragment, container, false)
        getRecentSearchKeyword()
        initSuggestKeywordRecycler()
        initSearchResultRecycler()
        initButtons()
        bindUI()

        return searchResultView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, SearchResultViewModelFactory(searchResultRepository)).get(SearchResultViewModel::class.java)
        viewModel.getSearchResult(keyword, type).observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
        viewModel.getSuggestKeyword(keyword).observe(viewLifecycleOwner, Observer {
            suggestKeywordRecyclerAdapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            if(it == NetworkState.ERROR){
                searchResultView.errorText.visibility = View.VISIBLE
            } else {
                searchResultView.errorText.visibility = View.GONE
            }
        })
        inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun getRecentSearchKeyword(){
        sharedPreferences = context?.getSharedPreferences("giphyclone", Context.MODE_PRIVATE)!!
        val recentKeywordStr = sharedPreferences.getString("recentKeyword", "")!!
        if (recentKeywordStr.isNotEmpty()) {
            recentKeywordList = ArrayList(recentKeywordStr.split(","))
        }
    }

    private fun initSuggestKeywordRecycler(){
        suggestKeywordRecyclerAdapter = SuggestKeywordRecyclerAdapter(this)
        val suggestLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        searchResultView.suggestKeywordRecycler.apply {
            adapter = suggestKeywordRecyclerAdapter
            layoutManager = suggestLayoutManager
            scrollToPosition(recentKeywordList.size-1)
        }
    }

    private fun initSearchResultRecycler(){
        val requestManager = Glide.with(this)
        resultDataAdapter = ResultDataAdapter(
            requestManager,
            this,
            resources.displayMetrics.density.toInt()
        )

        searchResultView.resultRecycler.apply {
            adapter = resultDataAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun bindUI(){

        searchResultView.searchResultTitle.text = keyword
        searchResultView.keywordText.text = keyword
        searchResultView.resultRecycler.setOnTouchListener { view, _ ->
            inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        when(type)
        {
            "gifs" -> clickGifBtn()
            "stickers" -> clickStickerBtn()
        }
    }

    private fun initButtons(){
        searchResultView.gifBtn.setOnClickListener {
            clickGifBtn()
        }
        searchResultView.stickerBtn.setOnClickListener {
            clickStickerBtn()
        }
        searchResultView.backBtnImg.setOnClickListener {
            Navigation.findNavController(searchResultView).navigateUp()
        }
        searchResultView.backBtnImg.setColorFilter(ContextCompat.getColor(context!!, R.color.blue))
        searchResultView.keywordText.setOnClickListener {
            moveToSearchPage()
        }
        searchResultView.searchBtn.setOnClickListener {
            moveToSearchPage()
        }
    }

    private fun moveToSearchPage(){
        val bundle = bundleOf("keyword" to keyword, "beforePage" to beforePage)
        Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_searchFragment, bundle)
    }

    private fun clickGifBtn(){
        type = "gifs"
        searchResultView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchResultView.stickerBtn.setTextColor(Color.WHITE)
        searchResultView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchResultView.gifBtn.setTextColor(Color.BLACK)
        if(::viewModel.isInitialized) {
            changeObserver(type)
        }
    }

    private fun clickStickerBtn(){
        type = "stickers"
        searchResultView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchResultView.stickerBtn.setTextColor(Color.BLACK)
        searchResultView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchResultView.gifBtn.setTextColor(Color.WHITE)
        if(::viewModel.isInitialized) {
            changeObserver(type)
        }
    }

    private fun changeObserver(type: String){
        resultDataAdapter.submitList(null)
        viewModel.getSearchResult(keyword, type).observe(viewLifecycleOwner, Observer {
            resultDataAdapter.submitList(it)
        })
    }

    override fun movePage(type: String, id: String, position: Int) {
        var startPoint = 0
        var newPosition = position
        if(position - 30 > 0){
            startPoint = position - 30
            newPosition = 30
        }
        val endPoint: Int = if(position + 30 > resultDataAdapter.currentList?.toList()!!.size){
            resultDataAdapter.currentList?.toList()!!.size-1
        } else {
            position+30
        }

        val gifList = ArrayList<Data>()
        for(i in startPoint..endPoint){
            gifList.add(resultDataAdapter.currentList!![i]!!)
        }

        val bundle = bundleOf("gifId" to id, "list" to gifList, "startPosition" to newPosition, "beforePage" to beforePage)
        Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_detailFragment, bundle)
    }

    override fun moveSearchResult(keyword: String) {
        if(!recentKeywordList.contains(keyword)) {
            registerRecentKeyword(keyword)
        }
        if(keyword != searchResultView.keywordText.text) {
            val bundle = bundleOf("keyword" to keyword, "type" to type)
            inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
            Navigation.findNavController(searchResultView)
                .navigate(R.id.action_searchResultFragment_self, bundle)
        }
    }

    private fun registerRecentKeyword(keyword:String){
        if (recentKeywordList.size == 5) {
            recentKeywordList.removeAt(0)
        }
        recentKeywordList.add(keyword)
        var keywordStr = ""
        for (i in 0 until recentKeywordList.size) {

            if (i == recentKeywordList.size - 1) {
                keywordStr += recentKeywordList[i]
            } else {
                keywordStr += recentKeywordList[i] + ","
            }
        }
        val editor = sharedPreferences.edit()
        editor.putString("recentKeyword", keywordStr)
        editor.apply()
    }
}
