package com.example.saini.mymobilegallery.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapter(fragmentManager: FragmentManager, private val fragmentList: ArrayList<Fragment>,private val mFragmentTitleList:ArrayList<String>) :
        FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int {
        return fragmentList.size
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment? {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList.get(position)
    }
}
