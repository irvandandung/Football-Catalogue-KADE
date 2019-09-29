package com.example.myfootballmatchschedule.Team

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.TeamsResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(
private val view: TeamView,
private val apiRepository: ApiRepository,
private val gson : Gson,
private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getTeamList(idLeague: String?){
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getAllTeams(idLeague)).await(),
                TeamsResponse::class.java
            )
            data?.teams?.let { view.showTeamList(it) }
            view.hideLoading()
        }
    }
}