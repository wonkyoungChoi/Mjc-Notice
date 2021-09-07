package com.mjc.mjcnotice.ui.notification

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mjc.mjcnotice.database.Keyword
import com.mjc.mjcnotice.repository.KeywordRepository
import com.mjc.mjcnotice.repository.NoticeRepository
import com.mjc.mjcnotice.ui.notice.Data

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