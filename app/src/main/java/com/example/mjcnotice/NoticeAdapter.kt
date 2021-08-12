package com.example.mjcnotice

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mjcnotice.databinding.NoticeListItemBinding
import com.example.mjcnotice.databinding.NoticeLoadingBinding
import java.util.*

class NoticeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var items = ArrayList<NoticeItem>()

    private var baseUrl = "https://www.mjc.ac.kr/bbs/data/view.do?menu_idx=66&memberAuth=Y"


    inner class NoticeViewHolder(private val binding: NoticeListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(notice: NoticeItem){
            binding.title.text = notice.title
            binding.writer.text = notice.writer
            binding.date.text = notice.date

            binding.layoutNotice.setOnClickListener {
                val goUnivHomepage = Intent(it.context, WebViewActivity::class.java)

                goUnivHomepage.putExtra("urlStart", baseUrl)
                goUnivHomepage.putExtra("urlEnd", notice.link)

                it.context.startActivity(goUnivHomepage)
            }


        }
    }

    inner class LoadingViewHolder(private val binding: NoticeLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    // 뷰의 타입을 정해주는 곳이다.
    override fun getItemViewType(position: Int): Int {
        // 게시물과 프로그레스바 아이템뷰를 구분할 기준이 필요하다.
        return when (items[position].title) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoticeListItemBinding.inflate(layoutInflater, parent, false)
                NoticeViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoticeLoadingBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NoticeViewHolder){
            holder.bind(items[position])
        }else{

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(notice: ArrayList<NoticeItem>) {
        items = notice
        items.add(NoticeItem(" ", " ", " ", " ")) // progress bar 넣을 자리
    }

    fun deleteLoading(){
        items.removeAt(items.lastIndex) // 로딩이 완료되면 프로그레스바를 지움
    }

    fun resetList(menu_idx: String) {
        items.clear()
        baseUrl = "https://www.mjc.ac.kr/bbs/data/view.do?menu_idx=$menu_idx&memberAuth=Y"
    }

}

