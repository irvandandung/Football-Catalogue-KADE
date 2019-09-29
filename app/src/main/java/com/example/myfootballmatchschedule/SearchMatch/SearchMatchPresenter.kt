package com.example.myfootballmatchschedule.SearchMatch

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.coroutines.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(
    private val v: SearchMatchMain,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getSearchMatchEvent(query: String?) {
        v.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository
                    .doRequest(ApiUrl.getSearchMatch(query)).await(),
                MatchResponse::class.java)

            data?.event?.let { v.showMatchList(it) }
            v.hideLoading()
        }
    }
}