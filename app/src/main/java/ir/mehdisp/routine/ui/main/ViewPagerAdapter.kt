package ir.mehdisp.routine.ui.main

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.mehdisp.routine.ui.home.HomeFragment

class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val pages = arrayOf(
        HomeFragment()
    )

    override fun getItemCount() = pages.size

    override fun createFragment(position: Int) = pages[position]

}