package com.example.myfootballmatchschedule.Prsesenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.Interface.MainView
import com.example.myfootballmatchschedule.ModelData.LeagueResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeaguePresenter(
    private val v: MainView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getLeaguee() {
        v.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getLeague()).await(),
                LeagueResponse::class.java)

            data?.countrys?.let { v.showLeagueList(it) }
            v.hideLoading()
        }
    }
}
