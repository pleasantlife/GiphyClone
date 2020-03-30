package com.gandan.giphyclone.util

import android.util.Log
import com.gandan.giphyclone.data.repository.GiphyAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUtil {

    companion object {
        const val BASE_URL = "https://api.giphy.com/v1/"
        const val API_KEY = "oQj9NoWtW9tRnFJjtWgS29uOuXZ6dXdr"
    }

    fun getRetrofitService(): GiphyAPIService {
        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor{
            Log.w("HttpLogging::","${it}")
        }.setLevel(HttpLoggingInterceptor.Level.BODY)).build()
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GiphyAPIService::class.java)
    }
}