package com.example.mjcnotice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mjcnotice.repository.NoticeRepository



class MainViewModel : ViewModel() {
    private val noticeRepository = NoticeRepository()
    private val notice: LiveData<Data>
        get() = noticeRepository._notice

    fun loadNotice(page: Int){
        noticeRepository.loadNotice(page)
    }

    fun getAll(): LiveData<Data> {
        return notice
    }
}
