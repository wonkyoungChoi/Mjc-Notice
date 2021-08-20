package com.example.mjcnotice.ui.setting.license

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mjcnotice.databinding.ActivityLicenseBinding

class LicenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLicenseBinding
    private lateinit var model: LicenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(LicenseViewModel::class.java)

        var licenseList = model.requestLicense()
        binding.rvLicense.adapter = LicenseAdapter(licenseList)
        binding.rvLicense.layoutManager = LinearLayoutManager(this)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}