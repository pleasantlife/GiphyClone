package com.gandan.giphyclone.view.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Context.MODE_PRIVATE
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.gandan.giphyclone.R
import com.gandan.giphyclone.util.SearchItemClickListener
import com.gandan.giphyclone.view.SearchKeywordAdapter
import kotlinx.android.synthetic.main.search_fragment.view.*
import kotlinx.android.synthetic.main.search_fragment.view.backBtnImg
import kotlinx.android.synthetic.main.search_fragment.view.gifBtn
import kotlinx.android.synthetic.main.search_fragment.view.keywordEditText
import kotlinx.android.synthetic.main.search_fragment.view.recentScrollView
import kotlinx.android.synthetic.main.search_fragment.view.recentTitle
import kotlinx.android.synthetic.main.search_fragment.view.searchBtn
import kotlinx.android.synthetic.main.search_fragment.view.stickerBtn

class SearchFragment : Fragment(), SearchItemClickListener {

    companion object {
        fun newInstance() = SearchFragment()
        val TRENDING_KEYWORD = "trendingkeyword"
        val SEARCHING_KEYWORD = "searchingkeyword"
    }

    private lateinit var searchingView: View
    private lateinit var viewModel: SearchViewModel
    private lateinit var searchKeywordAdapter: SearchKeywordAdapter
    private lateinit var inputManager: InputMethodManager
    private val keywordList = ArrayList<String>()
    private var beforePage: String? = ""
    private var keyword: String? = ""
    private var type: String = "gifs"
    private lateinit var countdownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        beforePage = arguments?.getString("beforePage", "")
        keyword = arguments?.getString("keyword", "")
        searchingView = inflater.inflate(R.layout.search_fragment, container, false)
        searchKeywordAdapter =
            SearchKeywordAdapter(keywordList, this)
        bindUI()
        searchingView.gifBtn.setOnClickListener {
            clickGifBtn()
        }
        searchingView.stickerBtn.setOnClickListener {
            clickStickerBtn()
        }
        return searchingView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        viewModel.getKeywords().observe(viewLifecycleOwner, Observer {
            setRecyclerData(ArrayList(it), TRENDING_KEYWORD)
        })
        viewModel.getRecentKeywords(context!!.getSharedPreferences("giphyclone", MODE_PRIVATE))
            .observe(viewLifecycleOwner, Observer{
                setRecentKeywordUI(ArrayList(it))
            })
        inputManager = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

    }

    private fun bindUI(){
        searchingView.clearBtn.setColorFilter(Color.RED)
        searchingView.backBtnImg.setOnClickListener {
            Navigation.findNavController(searchingView).navigateUp()
        }
        if(keyword != null){
            searchingView.keywordEditText.setText(keyword)
        }
        searchingView.keywordEditText.doAfterTextChanged { it ->
            if (it != null) {
                if(it.isNotEmpty()){
                    searchingView.clearBtn.visibility = View.VISIBLE
                } else {
                    searchingView.clearBtn.visibility = View.GONE
                }
                if(it.length > 2){
                    changeKeywordSuggest(it.toString(), SEARCHING_KEYWORD)
                } else {
                    changeKeywordSuggest(it.toString(), TRENDING_KEYWORD)
                }
            }
        }
        searchingView.clearBtn.setOnClickListener {
            searchingView.keywordEditText.text.clear()
        }
        searchingView.searchBtn.setOnClickListener {
            if(searchingView.keywordEditText.text.isNotEmpty()) {
                moveSearchResult(searchingView.keywordEditText.text.toString())
            }
        }

        searchingView.popularRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchKeywordAdapter
        }
    }

    fun clickGifBtn(){
        searchingView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchingView.stickerBtn.setTextColor(Color.WHITE)
        searchingView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchingView.gifBtn.setTextColor(Color.BLACK)
        type = "gifs"
    }

    fun clickStickerBtn(){
        searchingView.stickerBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))
        searchingView.stickerBtn.setTextColor(Color.BLACK)
        searchingView.gifBtn.setBackgroundColor(ContextCompat.getColor(context!!, R.color.gray))
        searchingView.gifBtn.setTextColor(Color.WHITE)
        type = "stickers"
    }


    private fun changeKeywordSuggest(keyword: String, type: String){
        if(this::countdownTimer.isInitialized){
            countdownTimer.cancel()
        }
        countdownTimer = object : CountDownTimer(500, 100) {
                override fun onFinish() {

                    if (keyword.length > 2) {
                        viewModel.getAutoKeywords(keyword)
                            .observe(viewLifecycleOwner, Observer { list ->
                                setRecyclerData(ArrayList(list), type)
                            })
                        setHeaderUI(searchingView, false)
                    } else {
                        viewModel.getKeywords()
                            .observe(viewLifecycleOwner, Observer { list ->
                                setRecyclerData(ArrayList(list), type)
                            })
                        setHeaderUI(searchingView, true)
                    }
                }

                override fun onTick(p0: Long) {
                }
            }
        countdownTimer.start()
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

    override fun moveSearchResult(keyword: String) {
        val bundle = bundleOf("keyword" to keyword, "type" to type)
        searchingView.keywordEditText.text.clear()
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
        Navigation.findNavController(searchingView).navigate(R.id.action_searchFragment_to_searchResultFragment, bundle)
    }

    override fun onPause() {
        super.onPause()
        if(this::countdownTimer.isInitialized){
            countdownTimer.cancel()
        }
    }
}
