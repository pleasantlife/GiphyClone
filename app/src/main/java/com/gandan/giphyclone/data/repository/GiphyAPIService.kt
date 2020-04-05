package com.gandan.giphyclone.data.repository

import com.gandan.giphyclone.data.model.ResultTrendingWordsModel
import com.gandan.giphyclone.data.model.gifs.ResultDetailModel
import com.gandan.giphyclone.data.model.gifs.ResultModel
import com.gandan.giphyclone.data.model.suggestion.AutoCompleteData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiphyAPIService {

    /**
     *  트랜드 GIF/스티커 불러오기
     *
     *  types에는 'gifs', 'stickers'만 가능
     *
     */

    @GET("{types}/trending")
    fun getTrending(
        @Path("types") types: String,
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Single<ResultModel>

    //Search endpoint
    @GET("{types}/search")
    fun getSearchResult(
        @Path("types") types: String,
        @Query("api_key") apiKey: String,
        @Query("q") queryWord: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
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

    //Auto suggestions keyword
    @GET("gifs/search/tags")
    fun getSuggestion(
        @Query("q") tags: String,
        @Query("api_key") apiKey: String
    ) : Single<AutoCompleteData>
}