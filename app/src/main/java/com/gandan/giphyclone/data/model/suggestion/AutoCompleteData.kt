package com.gandan.giphyclone.data.model.suggestion

import com.gandan.giphyclone.data.model.gifs.Meta
import com.gandan.giphyclone.data.model.gifs.Pagination

data class AutoCompleteData(
    val data: List<Name>,
    val pagination: Pagination,
    val meta: Meta
) {
}