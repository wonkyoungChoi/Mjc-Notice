package com.example.mjcnotice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mjcnotice.databinding.NoticeListItemBinding
import java.util.*

class NoticeAdapter : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    private var items = ArrayList<NoticeItem>()

    inner class NoticeViewHolder(private val binding: NoticeListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(notice: NoticeItem){
            binding.title.text = notice.title
            binding.writer.text = notice.writer
            binding.date.text = notice.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = NoticeListItemBinding.inflate(layoutInflater, parent, false)
        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setList(notice: ArrayList<NoticeItem>) {
        items = notice
    }

}

