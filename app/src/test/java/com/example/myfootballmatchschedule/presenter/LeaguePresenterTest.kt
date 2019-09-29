package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.Interface.MainView
import com.example.myfootballmatchschedule.ModelData.LeagueResponse
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.Prsesenter.LeaguePresenter
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeaguePresenterTest {
    private lateinit var presenter: LeaguePresenter

    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: MainView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeaguePresenter(
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

        GlobalScope.launch(contex.main) {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(ApiUrl.getLeague()).await(),
                    LeagueResponse::class.java
                )
            ).thenReturn(response)
            presenter.getLeaguee()
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueList(league)
            Mockito.verify(view).hideLoading()
        }
    }
}