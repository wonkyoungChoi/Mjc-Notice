package com.example.mjcnotice.repository

import android.util.Log
import com.example.mjcnotice.NoticeFragment
import com.example.mjcnotice.NoticeItem
import com.example.mjcnotice.network.NoticeClient
import okhttp3.ResponseBody
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository {

    fun loadNotice(page: Int, mCallback: NoticeFragment) {
        val call = NoticeClient.service.loadNotice(page.toString())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.isSuccessful){
                    val doc : Document = Jsoup.parse(response.body()!!.string())
                    Log.d("DOCUMENT", doc.toString())
                    val items = ArrayList<NoticeItem>()
                    val title : Elements = doc.select("tr[class=cell_notice]").select("td[class=cell_type01]")
                    val date : Elements = doc.select("tr[class=cell_notice]").select("td:nth-child(5n)")
                    val writer : Elements = doc.select("tr[class=cell_notice]").select("td:nth-child(4n)")
                    for(index in title.indices) { //indices -> 0..2
                        println("item at $index is ${title[index].text()}")
                        println("item at $index is ${writer[index].text()}")
                        println("item at $index is ${date[index].text()}")
                        NoticeItem(title[index].text(), date[index].text(), writer[index].text())

                    }
                    mCallback.loadComplete(items)
                } else {
                    mCallback.responseIsNotSuccesful(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mCallback.loadFail()
            }
        })
    }
}