package com.mjc.mjcnotice.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KeywordDao {
    @Query("SELECT * FROM Keyword")
    fun getAll(): LiveData<List<Keyword>>

    @Insert
    fun insert(todo: Keyword)

    @Query("DELETE FROM Keyword WHERE keyword = :keyword")
    fun deleteKeywordByTitle(keyword: String)
}