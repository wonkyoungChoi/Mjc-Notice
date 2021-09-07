package com.mjc.mjcnotice.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mjc.mjcnotice.ui.notice.NoticeFragment
import com.mjc.mjcnotice.R
import com.mjc.mjcnotice.ui.setting.SettingFragment
import com.mjc.mjcnotice.databinding.ActivityMainBinding
import com.mjc.mjcnotice.ui.notification.NotificationFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {


    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        userIdCheck()

        supportFragmentManager.beginTransaction().add(viewBinding.frameContainer.id, NoticeFragment()).commit()

        viewBinding.bottomNavigation.setOnItemSelectedListener {
            replaceFragment(
                when (it.itemId) {
                    R.id.action_notice -> NoticeFragment()
                    R.id.action_notification -> NotificationFragment()
                    else -> SettingFragment()
                }
            )
            true }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(viewBinding.frameContainer.id, fragment).commit()
    }

    private fun userIdCheck(){
        var auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if(user != null){ // 이미 가입한 회원인 경우
            //userId = user.uid
        }else{
            auth.signInAnonymously()
                .addOnCompleteListener(this){ task ->
                    if(task.isSuccessful){
                        //userId = auth.currentUser!!.uid
                    }else{
                        Toast.makeText(this, "네트워크를 연결한 뒤 실행시켜주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }



}