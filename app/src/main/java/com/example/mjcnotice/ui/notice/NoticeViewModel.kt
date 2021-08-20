package com.example.mjcnotice.ui.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mjcnotice.repository.NoticeRepository



class MainViewModel : ViewModel() {
    private val noticeRepository = NoticeRepository()
    private val notice: LiveData<Data>
        get() = noticeRepository._notice

    fun loadNotice(page: Int, bbs_mst_idx: String, menu_idx: String){
        noticeRepository.loadNotice(page, bbs_mst_idx, menu_idx)
    }

    fun getAll(): LiveData<Data> {
        return notice
    }
}
