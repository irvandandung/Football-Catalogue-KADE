package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.ModelData.LeagueResponse
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.Prsesenter.LeaguePresenter
import com.example.myfootballmatchschedule.TabMatch.DetailLeague
import com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment.DetailLeaguePresenter
import com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment.DetailLeagueView
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {
    private lateinit var presenter : DetailLeaguePresenter

    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: DetailLeagueView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(
            view,
            apiRepository,
            gson,
            CoroutineContextProviderTest()
        )
    }

    @Test
    fun getMatchList() {
        val league: MutableList<ModelLeague> = mutableListOf()
        val response = LeagueResponse(league, league)
        val id = "Barcelona"

        GlobalScope.launch(contex.main) {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(ApiUrl.getDetailLeague(id)).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)
            presenter.getDetailLeague(id)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(league)
            Mockito.verify(view).hideLoading()
        }
    }

}