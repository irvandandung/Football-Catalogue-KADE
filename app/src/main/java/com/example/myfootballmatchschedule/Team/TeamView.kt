package com.example.myfootballmatchschedule.Team

import com.example.myfootballmatchschedule.ModelData.Teams

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Teams>)
}