package com.example.mjcnotice

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mjcnotice.databinding.FragmentNoticeBinding
import com.google.android.material.tabs.TabLayout

class NoticeFragment : Fragment() {

    private var _viewBinding: FragmentNoticeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var model: MainViewModel
    private var isListEmpty = true
    private var pageIndex = 1
    private var menu_idx = "66"
    private var bbs_mst_idx = "BM0000000026"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNoticeBinding.inflate(inflater, container, false)

        model = ViewModelProvider(this).get(MainViewModel::class.java)

        model.loadNotice(pageIndex, bbs_mst_idx, menu_idx)
        Log.v("로그", "로그")

        initRecylerView()
        initTabLayout()


        model.getAll().observe(viewLifecycleOwner, Observer {
            noticeAdapter.setList(it.content)
            isListEmpty = false
            noticeAdapter.notifyItemRangeInserted((pageIndex - 1) * 15, 15)
        })

        // 스크롤 리스너
        viewBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                // 스크롤이 끝에 도달했는지 확인
                if (!viewBinding.recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount && !isListEmpty) {
                    noticeAdapter.deleteLoading()
                    model.loadNotice(++pageIndex, bbs_mst_idx, menu_idx)
                }
            }
        })

        return viewBinding.root
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    private fun initRecylerView() {
        noticeAdapter = NoticeAdapter()
        viewBinding.recyclerView.adapter = noticeAdapter
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initTabLayout() {
        viewBinding.tabLayoutNotice.tabTextColors =
            resources.getColorStateList(R.color.tap_icon, null)
        viewBinding.tabLayoutNotice.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // menu_idx, bbs_mst_idx
                // 공지사항(66, BM0000000026), 학사공지(169, BM0000000025), 장학공지(208, BM0000000032), 채용공지(2617, BM0000002077)
                when (tab!!.position) {
                    0 -> {
                        menu_idx = "66"
                        bbs_mst_idx = "BM0000000026"
                        tab.text = "공지사항"
                    }
                    1 -> {
                        menu_idx = "169"
                        bbs_mst_idx = "BM0000000025"
                        tab.text = "학사공지"
                    }
                    2 -> {
                        menu_idx = "208"
                        bbs_mst_idx = "BM0000000032"
                        tab.text = "장학공지"
                    }
                    3 -> {
                        menu_idx = "2617"
                        bbs_mst_idx = "BM0000002077"
                        tab.text = "채용공지"
                    }
                }


                pageIndex = 1
                noticeAdapter.resetList(menu_idx)
                isListEmpty = true
                noticeAdapter.notifyDataSetChanged()
                model.loadNotice(pageIndex, bbs_mst_idx, menu_idx)
            }
        })


    }
}
