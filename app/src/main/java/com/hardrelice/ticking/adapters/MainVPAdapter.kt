package com.hardrelice.ticking.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainVPAdapter(fm: FragmentManager, private val fragments: ArrayList<Fragment>):
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int = fragments.size
    override fun getItem(position: Int): Fragment = fragments[position]
}