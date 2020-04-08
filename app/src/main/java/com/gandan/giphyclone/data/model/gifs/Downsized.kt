package com.gandan.giphyclone.data.model.gifs

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Downsized(
    val height: String,
    val size: String,
    val url: String,
    val width: String
): Parcelable {
}