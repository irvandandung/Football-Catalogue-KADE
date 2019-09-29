package com.example.myfootballmatchschedule.SearchTeam

import com.example.myfootballmatchschedule.ModelData.Teams

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Teams>)
}