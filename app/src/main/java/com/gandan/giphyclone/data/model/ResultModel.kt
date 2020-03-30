package com.gandan.giphyclone.data.model

data class ResultModel(
    val data: List<Data>,
    val pagination: Pagination,
    val meta : Meta
)