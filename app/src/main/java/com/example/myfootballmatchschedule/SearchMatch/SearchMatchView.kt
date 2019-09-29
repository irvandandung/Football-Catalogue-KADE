package com.example.myfootballmatchschedule.SearchMatch

import com.example.myfootballmatchschedule.ModelData.ModelMatch

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<ModelMatch>)
}