package com.mjc.mjcnotice.network


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeService {
    @GET("list.do?data_idx=&memberAuth=Y&")
    fun loadNotice(@Query("pageIndex") pageIndex: String,
                   @Query("bbs_mst_idx") bbs_mst_idx: String,
                   @Query("menu_idx") menu_idx: String): Call<ResponseBody>
}

object NoticeApi {
    private const val baseUrl = "https://www.mjc.ac.kr/bbs/data/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JaxbConverterFactory.create())
        .build()

    fun createApi(): NoticeService {
        return retrofit.create(
                NoticeService::class.java)
    }
}


