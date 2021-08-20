package com.example.mjcnotice.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.example.mjcnotice.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url: String
    private lateinit var baseUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        finish()

        baseUrl = intent.getStringExtra("urlStart").toString()

        url = intent.getStringExtra("urlEnd").toString()

        val arr = url.split("('", "','", ",'")


        val urlClick = baseUrl + "&bbs_mst_idx=" + arr[1] + "&data_idx=" + arr[2]

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        builder.setShowTitle(true)
        customTabsIntent.launchUrl(this, Uri.parse(urlClick))


    }

}