package com.gandan.giphyclone.view.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gandan.giphyclone.data.repository.LoadRepository
import com.gandan.giphyclone.util.RetrofitUtil
import com.gandan.giphyclone.util.RetrofitUtil.Companion.API_KEY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    fun getKeywords() : MutableLiveData<List<String>> {
        return LoadRepository().getTrendKeyword()
    }
}
