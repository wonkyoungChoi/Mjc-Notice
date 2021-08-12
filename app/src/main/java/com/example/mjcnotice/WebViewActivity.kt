package com.example.mjcnotice

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mjcnotice.databinding.ActivityWebViewBinding
import java.net.URLDecoder


class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var url: String
    private lateinit var baseUrl: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

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