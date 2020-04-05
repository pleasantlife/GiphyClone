package com.gandan.giphyclone.data.model

import com.gandan.giphyclone.data.model.gifs.Meta

data class ResultTrendingWordsModel(
    val data: List<String>,
    val meta: Meta
) {
}