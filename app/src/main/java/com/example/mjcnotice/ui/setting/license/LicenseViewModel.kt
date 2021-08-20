package com.example.mjcnotice.ui.setting.license

import androidx.lifecycle.ViewModel
import com.example.mjcnotice.repository.LicenseRepository

class LicenseViewModel: ViewModel() {
    private val licenseRepository = LicenseRepository()

    fun requestLicense(): List<License> {
        return licenseRepository.requestLicense()
    }

}
