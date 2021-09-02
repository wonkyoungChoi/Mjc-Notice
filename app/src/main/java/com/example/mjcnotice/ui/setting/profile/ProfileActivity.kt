package com.example.mjcnotice.ui.setting.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.mjcnotice.R
import com.example.mjcnotice.databinding.ActivityProfileBinding
import com.example.mjcnotice.ui.WebViewActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layout1.visibility = View.INVISIBLE
        binding.layout2.visibility = View.INVISIBLE
        binding.layout3.visibility = View.INVISIBLE

        val ani = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val ani2 = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val ani3 = AnimationUtils.loadAnimation(this, R.anim.fadein)

        binding.layout1.visibility = View.VISIBLE
        binding.layout1.startAnimation(ani)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.layout2.visibility = View.VISIBLE
            binding.layout2.startAnimation(ani2)
        }, 1000L)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.layout3.visibility = View.VISIBLE
            binding.layout3.startAnimation(ani3)
        }, 1500L)


        binding.ivGithub.setOnClickListener {
            var goPage = Intent(it.context, WebViewActivity::class.java)
            goPage.putExtra("url", "https://github.com/wonkyoungChoi/Mjc-Notice")
            it.context.startActivity(goPage)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvAppVersion.text = "현재 버전 " + getAppVersion(this)
    }

    private fun getAppVersion(context: Context): String? {
        var versionName = ""
        try {
            val pm = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = pm.versionName
        } catch (e: Exception) {

        }
        return versionName
    }
}