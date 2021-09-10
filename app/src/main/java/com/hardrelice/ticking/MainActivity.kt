package com.hardrelice.ticking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hardrelice.ticking.adapters.MainVPAdapter
import com.hardrelice.ticking.databinding.ActivityMainBinding
import com.hardrelice.ticking.ui.arrange.ArrangeFragment
import com.hardrelice.ticking.ui.dashboard.RecordFragment
import com.hardrelice.ticking.ui.notifications.AnalysisFragment
import com.hardrelice.ticking.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragments = ArrayList<Fragment>()
    private val navMenuIds = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // saving navView ids
        for (item in navView.menu) {
            navMenuIds.add(item.itemId)
        }

        // add fragments to list
        fragments.add(ArrangeFragment())
        fragments.add(RecordFragment())
        fragments.add(AnalysisFragment())
        fragments.add(SettingFragment())

        // setup vp
        val vp = binding.vp
        vp.adapter = MainVPAdapter(supportFragmentManager, fragments)
        vp.offscreenPageLimit = fragments.size
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) {
                navView.selectedItemId = navView.menu[position].itemId
            }

            override fun onPageScrollStateChanged(state: Int) { }
        })

        // switch page when navMenuButton is select
        navView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                else -> {
                    vp.currentItem = navMenuIds.indexOf(item.itemId)
                    true
                }
            }
        })
    }
}