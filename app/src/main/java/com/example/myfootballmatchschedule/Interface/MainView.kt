package com.example.myfootballmatchschedule.Interface

import com.example.myfootballmatchschedule.ModelData.ModelLeague

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showLeagueList(data: List<ModelLeague>)
}