package com.example.mjcnotice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mjcnotice.databinding.NoticeListItemBinding
import java.util.*

class NoticeAdapter(private val list: ArrayList<NoticeItem>) : RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
    private var items = ArrayList<NoticeItem>()

    class NoticeViewHolder(binding: NoticeListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        return NoticeViewHolder(
            NoticeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        NoticeListItemBinding.bind(holder.itemView).apply {
            title.text = list[position].title
            date.text = list[position].date
            writer.text = list[position].writer
        }
    }

    override fun getItemCount() = list.count()

    fun setList(notice: ArrayList<NoticeItem>) {
        items = notice
    }
}

