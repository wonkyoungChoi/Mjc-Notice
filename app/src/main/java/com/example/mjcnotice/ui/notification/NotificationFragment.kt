package com.example.mjcnotice.ui.notification

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mjcnotice.R
import com.example.mjcnotice.database.Keyword
import com.example.mjcnotice.databinding.FragmentNotificationBinding
import com.github.kimcore.inko.Inko
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

const val KEYWORD_LIMIT = 10

class NotificationFragment : Fragment(), DeleteButtonListener, SignUpListener {
    private lateinit var map: Map<String, String>// 서버에 있는 키워드를 가져와서 저장할 변수

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private var _viewBinding: FragmentNotificationBinding? = null

    private val viewBinding get() = _viewBinding!!
    private lateinit var model: KeywordViewModel

    private lateinit var adapter: KeywordAdapter
    private var myKeywordList = arrayListOf<Keyword>()
    private val inko = Inko()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNotificationBinding.inflate(inflater, container, false)
        model = ViewModelProvider(this).get(KeywordViewModel::class.java)

        initRecyclerView()

        FirebaseDatabase.getInstance().reference
            .child("keywords")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.value != null) map = p0.value as Map<String, String>

                }
            })

        model.getAll().observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            viewBinding.tvRegisteredKeyword.text = it.size.toString()
            myKeywordList.clear()
            myKeywordList.addAll(it)
        })

        model.getResult().observe(viewLifecycleOwner, Observer {
            var enteredKeyword = viewBinding.etKeyword.text.toString()

            if (it.content.isEmpty()) {
                val dialog = KeywordDialog(requireContext(), this)
                dialog.myDig(enteredKeyword)
            } else {
                if (isValidKeyword(enteredKeyword)) {
                    subscribe(enteredKeyword)
                }
            }
        })

        viewBinding.btnSubscribe.setOnClickListener {
            var enteredKeyword = viewBinding.etKeyword.text.toString()
            model.searchKeyword(enteredKeyword) // 공지 이력 확인
        }

        viewBinding.etKeyword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isNotEmpty()) {
                    viewBinding.btnSubscribe.isEnabled = true
                    viewBinding.btnSubscribe.setTextColor(
                            ContextCompat.getColor(
                                    requireContext(),
                                    R.color.mainBlue
                            )
                    )
                } else {
                    viewBinding.btnSubscribe.isEnabled = false
                    viewBinding.btnSubscribe.setTextColor(
                            ContextCompat.getColor(
                                    requireContext(),
                                    R.color.colorBlackDisabled2
                            )
                    )
                }
            }
        })

        return viewBinding.root
    }

    private fun initRecyclerView() {
        viewBinding.rvKeyword.layoutManager = LinearLayoutManager(context)
        adapter = KeywordAdapter(this)
        viewBinding.rvKeyword.adapter = adapter
    }

    private fun isValidKeyword(enteredKeyword: String): Boolean {
        return isNotExceed() && isCorrectType(enteredKeyword) && isNotRegistered(enteredKeyword)
    }

    private fun isNotExceed(): Boolean {
        if (KEYWORD_LIMIT > viewBinding.tvRegisteredKeyword.text.toString().toInt()) return true
        showMessage("키워드는 10개까지 등록 가능합니다.")
        return false
    }

    private fun isCorrectType(enteredKeyword: String): Boolean {
        var ps = Pattern.compile("^[0-9ㄱ-ㅎ가-힣]+$");
        if (ps.matcher(enteredKeyword).matches()) return true
        showMessage("한글과 숫자만 등록 가능합니다.")
        return false
    }

    private fun isNotRegistered(enteredKeyword: String): Boolean {
        if (!myKeywordList.contains(Keyword(enteredKeyword))) return true
        showMessage("이미 등록된 키워드입니다.")
        return false
    }

    private fun subscribe(enteredKeyword: String) {
        var englishKeyword = inko.ko2en(enteredKeyword)

        FirebaseMessaging.getInstance().subscribeToTopic(englishKeyword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var num = "1" // 기본값 1
                    if (map.containsKey(enteredKeyword)) {
                        num = (map.getValue(enteredKeyword).toInt() + 1).toString() // 구독자 수 +1
                    }

                    lifecycleScope.launch(Dispatchers.IO) {
                        databaseReference.child("keywords").child(enteredKeyword).setValue(num)
                        model.insert(Keyword(enteredKeyword))
                    }

                } else {
                    showMessage("네트워크 상태가 불안정 합니다.")
                }
            }
        viewBinding.etKeyword.text = null
    }

    override fun unSubscribe(keyword: String) {

        var englishKeyword = inko.ko2en(keyword)

        FirebaseMessaging.getInstance().unsubscribeFromTopic(englishKeyword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val num = map.getValue(keyword).toInt() - 1 // 구독자 수 -1
                        databaseReference.child("keywords").child(keyword).setValue(num.toString())
                        model.deleteKeywordByTitle(keyword)
                    }
                } else {
                    showMessage("네트워크 상태가 불안정 합니다.")
                }

            }
    }

    override fun signUpListener(keyword: String) {

        if (isValidKeyword(keyword)) {
            subscribe(keyword)
        } else {

        }
        viewBinding.etKeyword.text = null
    }



    private fun showMessage(msg: String) {
        Snackbar.make(viewBinding.layoutKeyword, msg, Snackbar.LENGTH_SHORT).show()
    }

}


