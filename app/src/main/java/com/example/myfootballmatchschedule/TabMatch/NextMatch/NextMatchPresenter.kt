package com.example.myfootballmatchschedule.TabMatch.NextMatch

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.TabMatch.MatchView
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(
    private val v: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val fixture: Int = 1,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getNextMatchpresenter(idNext: String?){
        v.showLoading()
        GlobalScope.launch(context.main) {

            val api = if (fixture == 1) ApiUrl.getNextMatch(idNext)
            else ApiUrl.getNextMatch(idNext)
            val data = gson.fromJson(
                apiRepository
                    .doRequest(api).await(),
                MatchResponse::class.java
            )
            data?.events?.let { v.showMatchList(it) }
            v.hideLoading()
        }
    }
}