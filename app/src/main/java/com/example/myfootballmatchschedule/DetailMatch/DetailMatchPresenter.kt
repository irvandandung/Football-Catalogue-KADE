package com.example.myfootballmatchschedule.DetailMatch

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(private val v: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()
){
    fun getMatchDetaile(idevent:String){
        v.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository
                .doRequest(ApiUrl.getDetailMatch(idevent)).await(),
                MatchResponse::class.java)

            data?.events?.let { v.showMatchDetail(it) }
            v.hideLoading()
        }
    }
}