package com.gandan.giphyclone.data.model.gifs

import com.gandan.giphyclone.data.model.gifs.Data
import com.gandan.giphyclone.data.model.gifs.Meta
import com.gandan.giphyclone.data.model.gifs.Pagination

data class ResultModel(
    val data: List<Data>,
    val pagination: Pagination,
    val meta : Meta
)