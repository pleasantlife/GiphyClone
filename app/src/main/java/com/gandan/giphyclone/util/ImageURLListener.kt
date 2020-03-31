package com.gandan.giphyclone.util

import com.gandan.giphyclone.data.model.Downsized

interface ImageURLListener {

    fun getImageUrlList(urlList: ArrayList<Downsized>)
}