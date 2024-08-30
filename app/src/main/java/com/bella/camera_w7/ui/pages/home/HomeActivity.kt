package com.bella.camera_w7.ui.pages.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.bella.camera_w7.R
import com.bella.camera_w7.databinding.ActivityHomeBinding
import com.bella.camera_w7.ui.core.BaseActivity
import com.bella.camera_w7.ui.pages.camera.CameraActivity
import com.bella.camera_w7.ui.pages.gallery.GalleryFragment
import com.bella.camera_w7.ui.pages.home.adapter.ViewPagerAdapter
import com.bella.camera_w7.ui.pages.qrcode.QRCodeGeneratorFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager = binding.viewPager
        val fragments = listOf(
            GalleryFragment(),
            QRCodeGeneratorFragment()
        )

        val adapter = ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TabTitle.entries[position].title
        }.attach()

        binding.toolbar.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.animate_slide_down_enter,
                R.anim.animate_slide_down_exit
            )

            startActivity(intent, options.toBundle())
            finish()
        }
    }

    enum class TabTitle(val title: String) {
        GALLERY("Gallery"),
        QR_CODE_GENERATOR("QR Code Generator")
    }
}