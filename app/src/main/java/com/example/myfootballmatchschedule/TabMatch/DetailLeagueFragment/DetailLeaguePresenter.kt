package com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.LeagueResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(
    private val v: DetailLeagueView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getDetailLeague(idLeague: String) {
        v.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getDetailLeague(idLeague)).await(),
                LeagueResponse::class.java)

            data?.leagues?.let { v.showLeagueDetail(it) }
            v.hideLoading()
        }
    }
}