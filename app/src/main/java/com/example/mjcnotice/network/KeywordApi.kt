package com.example.mjcnotice.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface KeywordService {
    @GET("list.do?data_idx=&memberAuth=Y&")
    fun loadNotice(@Query("menu_idx") menu_idx: String,
                   @Query("SC_KEY") SC_KEY: String,
                   @Query("SC_KEYWORD") SC_KEYWORD: String): Call<ResponseBody>
}

object KeywordApi {
    private const val baseUrl = "https://www.mjc.ac.kr/bbs/data/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JaxbConverterFactory.create())
        .build()

    fun createApi(): KeywordService {
        return retrofit.create(
            KeywordService::class.java)
    }
}