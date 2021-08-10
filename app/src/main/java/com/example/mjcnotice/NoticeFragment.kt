package com.example.mjcnotice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mjcnotice.databinding.FramentNoticeBinding
import com.example.mjcnotice.repository.NoticeRepository
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class NoticeFragment : Fragment() {

    private var _viewBinding: FramentNoticeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var model: MainViewModel
    private var pageIndex = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _viewBinding = FramentNoticeBinding.inflate(inflater, container, false)

        model = ViewModelProvider(this).get(MainViewModel::class.java)

        model.loadNotice(pageIndex)
        Log.v("로그", "로그")

        noticeAdapter = NoticeAdapter()

        viewBinding.recyclerView.adapter = noticeAdapter
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)

        model.getAll().observe(viewLifecycleOwner, Observer{
            noticeAdapter.setList(it.content)
            noticeAdapter.notifyItemRangeInserted((pageIndex - 1) * 15 + 18, 15)
        })

        // 스크롤 리스너
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1

                // 스크롤이 끝에 도달했는지 확인
                if (!viewBinding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    noticeAdapter.deleteLoading()
                    model.loadNotice(++pageIndex)
                }
            }
        })

        return viewBinding.root
    }



    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}
