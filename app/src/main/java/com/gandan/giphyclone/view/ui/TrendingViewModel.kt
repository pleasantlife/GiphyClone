package com.gandan.giphyclone.view.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gandan.giphyclone.data.model.gifs.FixedDownsampled
import com.gandan.giphyclone.data.repository.LoadRepository

class TrendingViewModel : ViewModel() {

    fun getTredningGifData() : MutableLiveData<List<FixedDownsampled>> {
        return LoadRepository().getGifData()
    }
}
