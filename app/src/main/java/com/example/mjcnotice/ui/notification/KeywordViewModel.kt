package com.example.mjcnotice.ui.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mjcnotice.database.Keyword
import com.example.mjcnotice.repository.KeywordRepository
import com.example.mjcnotice.repository.NoticeRepository
import com.example.mjcnotice.ui.notice.Data

class KeywordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = KeywordRepository(application)
    private val items = repository.getAll()

    private val noticeRepository = NoticeRepository()
    private val searchResult: LiveData<Data>
        get() = noticeRepository._searchResult

    fun insert(keyword: Keyword) {
        repository.insert(keyword)
    }

    fun deleteKeywordByTitle(keyword: String) {
        repository.deleteKeywordByTitle(keyword)
    }

    fun getAll(): LiveData<List<Keyword>> {
        return items
    }

    fun searchKeyword(keyword: String){
        noticeRepository.searchKeyword(keyword)
    }

    fun getResult(): LiveData<Data> {
        return searchResult
    }
}