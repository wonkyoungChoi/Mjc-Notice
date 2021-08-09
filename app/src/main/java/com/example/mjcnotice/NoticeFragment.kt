package com.example.mjcnotice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mjcnotice.databinding.FramentNoticeBinding
import com.example.mjcnotice.repository.NoticeRepository
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class NoticeFragment : Fragment() {

    private var _viewBinding: FramentNoticeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var noticeAdapter: NoticeAdapter
    private val noticeRepository = NoticeRepository()
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _viewBinding = FramentNoticeBinding.inflate(inflater, container, false)

        noticeRepository.loadNotice(page, this)
        Log.v("로그", "로그")

        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)

        return viewBinding.root
    }
/*
    private fun fetchList(): ArrayList<NoticeItem> {
        val list = arrayListOf<NoticeItem>()
        list.add(NoticeItem("User 1"))
        list.add(NoticeItem("User 2"))
        list.add(NoticeItem("User 3"))
        list.add(NoticeItem("User 4"))
        list.add(NoticeItem("User 5"))

        return list
    }

 */


    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }


    // 성공적으로 로드한 경우
    fun loadComplete(data: ArrayList<NoticeItem>) {
        noticeAdapter.setList(data)
        noticeAdapter.notifyDataSetChanged()
        Toast.makeText(activity, "로드 성공", Toast.LENGTH_SHORT).show()
    }

    // 응답에 문제가 있는 경우
    fun responseIsNotSuccesful(code: Int) {
        Toast.makeText(activity, "사이트가 응답하지 않습니다", Toast.LENGTH_SHORT).show()
        Log.v("로그", code.toString())
    }

    // 로드에 실패한 경우
    fun loadFail() {
        Toast.makeText(activity, "인터넷 연결을 확인하세요", Toast.LENGTH_SHORT).show()
    }




}
