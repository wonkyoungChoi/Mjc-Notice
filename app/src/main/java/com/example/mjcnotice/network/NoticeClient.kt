package com.example.mjcnotice.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jaxb.JaxbConverterFactory


object NoticeClient {
    private const val baseUrl = "https://www.mjc.ac.kr/bbs/data/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JaxbConverterFactory.create())
        .build()

    val service = retrofit.create(NoticeService::class.java)!!
}