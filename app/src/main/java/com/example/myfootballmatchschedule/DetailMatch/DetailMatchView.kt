package com.example.myfootballmatchschedule.DetailMatch

import com.example.myfootballmatchschedule.ModelData.ModelMatch

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<ModelMatch>)
}