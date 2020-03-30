package com.gandan.giphyclone.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    val type: String,
    val id: String,
    val url: String,
    val slug: String,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    val username: String,
    val source: String,
    val title: String,
    val rating: String,
    @SerializedName("content_url")
    val contentUrl: String,
    @SerializedName("source_tld")
    val sourceTld: String,
    @SerializedName("source_post_url")
    val sourcePostUrl: String,
    @SerializedName("is_sticker")
    val sticker: Int,
    @SerializedName("import_datetime")
    val importDateTime: String,
    @SerializedName("trending_datetime")
    val trendingDateTime: String,
    val images: Images
) {
}