package com.example.myfootballmatchschedule.Team.Player

import com.example.myfootballmatchschedule.ModelData.Players

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayer(data: List<Players>)
}