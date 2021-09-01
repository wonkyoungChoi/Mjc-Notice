package com.example.mjcnotice.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mjcnotice.ui.notice.Data
import com.example.mjcnotice.ui.notice.NoticeItem
import com.example.mjcnotice.network.KeywordApi
import com.example.mjcnotice.network.NoticeApi
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeRepository {

    var _notice = MutableLiveData<Data>()
    var _searchResult = MutableLiveData<Data>()
    val items = ArrayList<NoticeItem>()

    fun loadNotice(pageIndex: Int, bbs_mst_idx: String, menu_idx: String) {

        //parameter["page"] = page.toString()
        val call = NoticeApi.createApi().loadNotice(pageIndex.toString(), bbs_mst_idx, menu_idx)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    Log.d("PAGEINDEX", pageIndex.toString())

                    var startIndex = 0

                    val doc: Document = Jsoup.parse(response.body()!!.string())

                    val check : Elements = doc.select("tr[class=cell_notice]").select("td:nth-child(n)").select("img")

                    for(index: Int in check.indices) {
                        if(check[index].attr("alt").equals("공지")) {
                            startIndex++
                        }
                    }

                    val title: Elements = doc.select("tr").select("td[class=cell_type01]")
                    val date: Elements = doc.select("tr").select("td:nth-child(5n)")
                    val writer: Elements = doc.select("tr").select("td:nth-child(4n)")
                    val link : Elements = doc.select("tr").select("td[class=cell_type01]").select("a[href]")

                    Log.d("TITLELAST : " , title.lastIndex.toString())

                    if(pageIndex == 1) {
                        for (index: Int in title.indices) { //indices -> 0..2
                            println("item at $index is ${title[index].text()}")
                            println("item at $index is ${writer[index].text()}")
                            println("item at $index is ${date[index].text()}")
                            val href = link[index].attr("href")
                            Log.d("HREF1", href)

                            items.add(NoticeItem(href, title[index].text(), date[index].text(), writer[index].text()))
                        }
                    } else {
                        for (index: Int in startIndex..title.lastIndex) { //indices -> 0..2
                            println("item at $index is ${title[index].text()}")
                            println("item at $index is ${writer[index].text()}")
                            println("item at $index is ${date[index].text()}")
                            val href = link[index].attr("href")
                            Log.d("HREF1", href)

                            items.add(NoticeItem(href, title[index].text(), date[index].text(), writer[index].text()))
                        }

                    }

                    _notice.value = Data(items)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun searchKeyword(keyword: String){
        val item = ArrayList<NoticeItem>()

        val call = KeywordApi.createApi().loadNotice("66", "TITLE", keyword)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {

                    val doc: Document = Jsoup.parse(response.body()!!.string())

                    val title: Elements = doc.select("tr").select("td[class=cell_type01]")
                    val date: Elements = doc.select("tr").select("td:nth-child(5n)")
                    val writer: Elements = doc.select("tr").select("td:nth-child(4n)")
                    val link : Elements = doc.select("tr").select("td[class=cell_type01]").select("a[href]")


                    for (index: Int in title.indices) { //indices -> 0..2
                        println("item at $index is ${title[index].text()}")
                        println("item at $index is ${writer[index].text()}")
                        println("item at $index is ${date[index].text()}")
                        val href = link[index].attr("href")
                        Log.d("HREF1", href)

                        item.add(NoticeItem(href, title[index].text(), date[index].text(), writer[index].text()))

                    }



                    _searchResult.value = Data(item)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }
}