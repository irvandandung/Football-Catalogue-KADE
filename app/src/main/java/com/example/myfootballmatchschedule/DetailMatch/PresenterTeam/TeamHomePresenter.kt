package com.example.myfootballmatchschedule.DetailMatch.PresenterTeam

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.DetailMatch.TeamView.TeamHomeView
import com.example.myfootballmatchschedule.ModelData.TeamsResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamHomePresenter(
    private val view: TeamHomeView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getHomeAway(idTeam: String?) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getTeam(idTeam)).await(),
                TeamsResponse::class.java)
                data?.teams?.let { view.showTeamHome(it) }
        }
    }
}