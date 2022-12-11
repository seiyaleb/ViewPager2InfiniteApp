package com.myapplication

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myapplication.fragment.FirstFragment
import com.myapplication.fragment.SecondFragment
import com.myapplication.fragment.ThirdFragment

class ViewPagerAdapter(mainActivity: MainActivity): FragmentStateAdapter(mainActivity) {

    private val realNumPage = 3

    override fun getItemCount(): Int = realNumPage + 2
    fun getRealCount() = realNumPage

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0,3 -> ThirdFragment()
            1,4 -> FirstFragment()
            2 -> SecondFragment()
            else -> ThirdFragment()
        }
    }
}