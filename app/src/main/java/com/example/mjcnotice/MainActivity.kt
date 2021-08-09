package com.example.mjcnotice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.mjcnotice.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

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

}