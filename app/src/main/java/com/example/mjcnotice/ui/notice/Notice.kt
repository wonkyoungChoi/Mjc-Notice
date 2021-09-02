package com.example.mjcnotice.ui.notice

data class Notice(val data: Data)

data class Data(val content: ArrayList<NoticeItem>)

class NoticeItem(var link: String, val title: String ,val date: String,val writer: String, val bold: Boolean)