package com.example.myfootballmatchschedule.TabMatch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myfootballmatchschedule.Team.Favorite.FavoriteTeamFragment
import com.example.myfootballmatchschedule.Team.TeamFragment

class AdapterDetailLeagueTwo(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        TeamFragment(),
        FavoriteTeamFragment()
    )

    // menentukan fragment yang akan dibuka pada posisi tertentu
    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    // judul untuk tabs
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Team"
            else -> "Team Favorite "
        }
    }
}