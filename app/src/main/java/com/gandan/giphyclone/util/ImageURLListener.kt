package com.gandan.giphyclone.util

import com.gandan.giphyclone.data.model.FixedDownsampled

interface ImageURLListener {

    fun getImageUrlList(urlList: ArrayList<FixedDownsampled>)
}