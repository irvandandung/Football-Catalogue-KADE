package com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment

import com.example.myfootballmatchschedule.ModelData.ModelLeague

interface DetailLeagueView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueDetail(data: List<ModelLeague>)
}
