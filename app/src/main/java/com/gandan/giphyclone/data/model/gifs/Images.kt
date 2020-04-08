package com.gandan.giphyclone.data.model.gifs

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
    val original: Original,
    val downsized: Downsized,
    @SerializedName("fixed_height_downsampled")
    val fixedHightDownsampled: FixedDownsampled,
    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: FixedDownsampled
): Parcelable {
}