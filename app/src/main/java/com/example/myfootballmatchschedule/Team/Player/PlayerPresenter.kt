package com.example.myfootballmatchschedule.Team.Player

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.PlayersResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(
    private val view: PlayerView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getPlayer(id: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getPlayer(id)).await(),
                PlayersResponse::class.java
            )

            data?.player?.let { view.showPlayer(it) }
            view.hideLoading()
        }
    }
}