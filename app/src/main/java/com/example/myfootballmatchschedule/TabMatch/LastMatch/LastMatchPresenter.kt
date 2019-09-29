package com.example.myfootballmatchschedule.TabMatch.LastMatch

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.TabMatch.MatchView
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LastMatchPresenter(
    private val v: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val fixture: Int = 1,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getLastMatchpresenter(idlast: String?){
        v.showLoading()
        GlobalScope.launch(context.main) {
            val api = if (fixture == 1) ApiUrl.getLastMatch(idlast)
            else ApiUrl.getNextMatch(idlast)
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