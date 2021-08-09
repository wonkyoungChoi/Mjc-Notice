package com.example.mjcnotice

data class Notice(val data: Data)

data class Data(val content: ArrayList<NoticeItem>)

class NoticeItem(val title: String ,val date: String,val writer: String)
