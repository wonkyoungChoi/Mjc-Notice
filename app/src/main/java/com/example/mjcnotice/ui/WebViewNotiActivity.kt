package com.example.mjcnotice.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.mjcnotice.databinding.ActivityWebViewBinding

class WebViewNotiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url: String


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        finish()

        url = intent.getStringExtra("url").toString()

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setShowTitle(true)
        customTabsIntent.launchUrl(this, Uri.parse(url))


    }

}