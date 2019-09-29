package com.example.myfootballmatchschedule.TabMatch

import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.ModelData.ModelMatch

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<ModelMatch>)
    fun showLeague(data: List<ModelLeague>)
}