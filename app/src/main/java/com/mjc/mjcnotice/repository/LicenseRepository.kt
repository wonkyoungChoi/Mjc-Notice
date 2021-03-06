package com.mjc.mjcnotice.repository

import com.mjc.mjcnotice.ui.setting.license.License

class LicenseRepository {
    fun requestLicense(): List<License> {
        return listOf(
            License(
                "firebase",
                "firebase",
                " "
            ),
            License(
                "jsoup",
                "mit",
                "Copyright © 2009 - 2021 Jonathan Hedley (https://jsoup.org/)"
            ),
            License(
                "retrofit2",
                "apache",
                "Copyright 2013 Square, Inc."
            ),
            License(
                "lottie",
                "lottie",
                "Copyright 2018 Airbnb, Inc."
            ),
            License(
                "inko",
                "mit",
                "Copyright (c) 2020 kimcore"
            )
        )
    }
}