package com.mjc.mjcnotice.ui.setting.license

import androidx.lifecycle.ViewModel
import com.mjc.mjcnotice.repository.LicenseRepository

class LicenseViewModel: ViewModel() {
    private val licenseRepository = LicenseRepository()

    fun requestLicense(): List<License> {
        return licenseRepository.requestLicense()
    }

}
