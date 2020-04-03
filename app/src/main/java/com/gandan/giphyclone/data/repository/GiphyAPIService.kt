package com.gandan.giphyclone.data.repository

import com.gandan.giphyclone.data.ResultTrendingWordsModel
import com.gandan.giphyclone.data.model.gifs.ResultDetailModel
import com.gandan.giphyclone.data.model.gifs.ResultModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyAPIService {


    @GET("gifs/trending")
    fun getGifTrending(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ) : Observable<ResultModel>

    @GET("stickers/trending")
    fun getStickerTrending(
        @Query("api_key") apiKey: String
    ) : Single<ResultModel>

    @GET("gifs/{id}")
    fun getGifDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : Single<ResultDetailModel>

    //Trending Search terms (in app: Popular searches)
    @GET("trending/searches")
    fun getTrendKeywords(
        @Query("api_key") apiKey: String
    ) : Single<ResultTrendingWordsModel>
}