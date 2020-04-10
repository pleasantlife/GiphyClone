package com.gandan.giphyclone.view.searchresult

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
import com.gandan.giphyclone.util.GifItemClickListener
import com.gandan.giphyclone.util.SearchKeywordItemClickListener
import com.gandan.giphyclone.view.RecentKeywordRecyclerAdapter
import com.gandan.giphyclone.view.ResultDataAdapter
import kotlinx.android.synthetic.main.search_fragment.view.*
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
    private lateinit var sharedPreferences: SharedPreferences
    private var recentKeywordStr = ""
    private var recentKeywordList = ArrayList<String>()
    private var type: String = "gifs"
    private val beforePage = "searchResult"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = context?.getSharedPreferences("giphyclone", Context.MODE_PRIVATE)!!
        recentKeywordStr = sharedPreferences.getString("recentKeyword", "")!!
        if (recentKeywordStr.isNotEmpty()) {
            recentKeywordList = ArrayList(recentKeywordStr.split(","))
        }
        searchResultView = inflater.inflate(R.layout.search_result_fragment, container, false)

        keyword = arguments?.get("keyword").toString()
        type = arguments?.get("type").toString()
        viewModel = SearchResultViewModel()

        val recentKeywordAdapter = RecentKeywordRecyclerAdapter(recentKeywordList, this)
        val recentKeywordLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        searchResultView.searchRecentKeywordRecycler.apply {
            adapter = recentKeywordAdapter
            layoutManager = recentKeywordLayoutManager
        }

        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val requestManager = Glide.with(this)
        val density = resources.displayMetrics.density.toInt()
        resultDataAdapter = ResultDataAdapter(
            requestManager,
            this,
            density
        )

        searchResultView.resultRecycler.apply {
            adapter = resultDataAdapter
            layoutManager = staggeredGridLayoutManager
        }
        searchResultView.searchResultTitle.text = keyword
        searchResultView.keywordText.text = keyword
        searchResultView.backBtnImg.setOnClickListener {
            Navigation.findNavController(searchResultView).navigateUp()
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
            val bundle = bundleOf("keyword" to keyword, "beforePage" to beforePage)
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

        searchResultView.resultRecycler.setOnTouchListener { view, _ ->
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

    override fun movePage(type: String, id: String, position: Int) {
        var startPoint = 0
        val endPoint: Int
        var newPosition = position
        if(position - 30 > 0){
            startPoint = position - 30
            newPosition = 30
        }
        if(position + 30 > resultDataAdapter.currentList?.toList()!!.size){
            endPoint = resultDataAdapter.currentList?.toList()!!.size-1
        } else {
            endPoint = position+30
        }

        val gifList = ArrayList<Data>()
        for(i in startPoint..endPoint){
            gifList.add(resultDataAdapter.currentList!![i]!!)
        }

        val bundle = bundleOf("gifId" to id, "list" to gifList, "startPosition" to newPosition, "beforePage" to beforePage)
        Navigation.findNavController(searchResultView).navigate(R.id.action_searchResultFragment_to_detailFragment, bundle)
    }

    override fun moveSearchResult(keyword: String) {
        //currentKeyword = keyword
        //registerRecentKeyword()
        if(keyword != searchResultView.keywordText.text) {
            val bundle = bundleOf("keyword" to keyword, "type" to type)
            inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
            Navigation.findNavController(searchResultView)
                .navigate(R.id.action_searchResultFragment_self, bundle)
        }
    }
}
