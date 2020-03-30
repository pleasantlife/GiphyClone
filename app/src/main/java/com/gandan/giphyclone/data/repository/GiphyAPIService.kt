package com.gandan.giphyclone.data.repository

import com.gandan.giphyclone.data.model.ResultModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyAPIService {

    @GET("gifs/trending")
    fun getGifTrending(
        @Query("api_key") apiKey: String
    ) : Observable<ResultModel>

    @GET("stickers/trending")
    fun getStickerTrending(
        @Query("api_key") apiKey: String
    ) : Single<ResultModel>
}