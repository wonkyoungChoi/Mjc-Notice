package com.example.mjcnotice.network

import com.example.mjcnotice.MainActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeService {
    @GET("list.do?menu_idx=66")
    fun loadNotice(@Query("menu_idx")page: String): Call<ResponseBody>
}

