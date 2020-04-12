package com.gandan.giphyclone.data.api

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
     *  @param types: 'gifs' or 'stickers'
     *  @param api_Key: API_KEY
     *  @param limit: load size per page
     *  @param offset: load start position
     *
     */

    @GET("{types}/trending")
    fun getTrending(
        @Path("types") types: String,
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Single<ResultModel>

    /**
     *  키워드에 따른 검색결과 불러오기
     *
     *  @param types: 'gifs' or 'stickers'
     *  @param api_key: API_KEY
     *  @param q: query keyword
     *  @param limit: load size per page
     *  @param offset: load start position
     *
     */
    @GET("{types}/search")
    fun getSearchResult(
        @Path("types") types: String,
        @Query("api_key") apiKey: String,
        @Query("q") queryWord: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Single<ResultModel>

    /**
     *  gif id값에 따른 상세내용 불러오기
     *
     *  @param id: gif id
     *  @param api_key: API_KEY
     */

    @GET("gifs/{id}")
    fun getGifDetail(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ) : Single<ResultDetailModel>

    /**
     *  인기있는 검색 키워드 리스트 불러오기
     *
     *  @param api_key: API_KEY
     */
    @GET("trending/searches")
    fun getTrendKeywords(
        @Query("api_key") apiKey: String
    ) : Single<ResultTrendingWordsModel>

    /**
     *  검색어에 따른 자동완성 키워드 불러오기
     *
     *  @param q: query keyword
     *  @param api_key: API_KEY
     *
     */

    @GET("gifs/search/tags")
    fun getSuggestion(
        @Query("q") tags: String,
        @Query("api_key") apiKey: String
    ) : Single<AutoCompleteData>


    /**
     *  여러개의 각 id별 gif 상세항목 불러오기
     *
     *  @param ids: list of gif id
     *  @param api_key: API_KEY
     *  @param limit: load size per page
     *  @param offset: load start position
     */

    @GET("gifs")
    fun getFavoriteGifData(
        @Query("ids") id: String,
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Single<ResultModel>
}