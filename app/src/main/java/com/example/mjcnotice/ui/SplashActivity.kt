package com.example.mjcnotice.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mjcnotice.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity(){

    private val WAITING_TIME: Long = 1600
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userIdCheck()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, WAITING_TIME)
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