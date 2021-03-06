package com.gandan.giphyclone.data.model.gifs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Original(
    val frames: String,
    val hash: String,
    val width: String,
    val height: String,
    val mp4: String,
    @SerializedName("mp4_size")
    val mp4Size: String,
    val size: String,
    val url: String,
    val webp: String,
    @SerializedName("webp_size")
    val webpSize: String
): Parcelable {
}