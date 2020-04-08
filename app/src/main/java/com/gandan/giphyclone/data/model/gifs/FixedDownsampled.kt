package com.gandan.giphyclone.data.model.gifs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FixedDownsampled(
    val height: String,
    val size: String,
    val url: String,
    val webp: String,
    @SerializedName("webp_size")
    val webpSize: String,
    val width: String,
    var id: String
): Parcelable{
}