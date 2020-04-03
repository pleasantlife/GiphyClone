package com.gandan.giphyclone.util

import com.gandan.giphyclone.data.model.gifs.FixedDownsampled

interface ImageURLListener {

    fun getImageUrlList(urlList: ArrayList<FixedDownsampled>)
}