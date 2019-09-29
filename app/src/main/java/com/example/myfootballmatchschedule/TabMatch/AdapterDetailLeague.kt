package com.example.myfootballmatchschedule.TabMatch

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment.DetailLeagueFragment
import com.example.myfootballmatchschedule.TabMatch.Favorite.MatchFavoriteFragment
import com.example.myfootballmatchschedule.TabMatch.LastMatch.LastMatchFragment
import com.example.myfootballmatchschedule.TabMatch.NextMatch.NextMatchFragment
import com.example.myfootballmatchschedule.Team.Favorite.FavoriteTeamFragment
import com.example.myfootballmatchschedule.Team.TeamFragment

class AdapterDetailLeague(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val pages = listOf(
        DetailLeagueFragment(),
        NextMatchFragment(),
        LastMatchFragment(),
        MatchFavoriteFragment(),
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
            0 -> "Detail League"
            1 -> "Next Match"
            2 -> "Last Match"
            3 -> "Favorite Match"
            4 -> "Team"
            else -> "Team Favorite "
        }
    }
}