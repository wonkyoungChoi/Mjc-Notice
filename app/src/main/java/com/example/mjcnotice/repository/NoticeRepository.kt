package com.example.mjcnotice.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mjcnotice.Data
import com.example.mjcnotice.NoticeItem
import com.example.mjcnotice.network.NoticeApi
import okhttp3.ResponseBody
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository {

    var _notice = MutableLiveData<Data>()
    val items = ArrayList<NoticeItem>()

    fun loadNotice(pageIndex: Int) {
        //parameter["page"] = page.toString()
        val call = NoticeApi.createApi().loadNotice(pageIndex.toString())

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    val doc: Document = Jsoup.parse(response.body()!!.string())

                    Log.d("PAGEINDEX", pageIndex.toString())

                    Log.d("DOCUMENT", doc.toString())

                    val title: Elements = doc.select("tr").select("td[class=cell_type01]")
                    val date: Elements = doc.select("tr").select("td:nth-child(5n)")
                    val writer: Elements = doc.select("tr").select("td:nth-child(4n)")
                    val link : Elements = doc.select("tr").select("td[class=cell_type01]").select("a[href]")

                    for (index: Int in 18..32) { //indices -> 0..2
                        println("item at $index is ${title[index].text()}")
                        println("item at $index is ${writer[index].text()}")
                        println("item at $index is ${date[index].text()}")
                        val href = link[index].attr("href")
                        Log.d("HREF1", href)
                        items.add(NoticeItem(href,title[index].text(), date[index].text(), writer[index].text()))
                    }

                    _notice.value = Data(items)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}