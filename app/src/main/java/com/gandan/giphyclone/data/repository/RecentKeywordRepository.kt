package com.gandan.giphyclone.data.repository

import android.content.SharedPreferences
import android.util.Log

class RecentKeywordRepository(private val sharedPreferences: SharedPreferences) {

    fun getRecentSearchKeywords(): List<String>{
        var hello = "안녕하세요,여러분,만나서,반갑습니다,앞으로,잘부탁드립니다"
        return hello.split(",")
    }
}