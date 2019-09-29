package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.DetailMatch.PresenterTeam.TeamAwayPresenter
import com.example.myfootballmatchschedule.DetailMatch.TeamView.TeamAwayView
import com.example.myfootballmatchschedule.ModelData.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamAwayPresenterTest {

    private lateinit var presenter : TeamAwayPresenter
    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: TeamAwayView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamAwayPresenter(
            view,
            apiRepository,
            gson,
            CoroutineContextProviderTest()
        )
    }

    @Test
    fun getMatchList() {
        val team: MutableList<Teams> = mutableListOf()
        val response = TeamsResponse(team)
        val id = "441613"

        GlobalScope.launch(contex.main) {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(ApiUrl.getTeam(id)).await(),
                    TeamsResponse::class.java
                )
            ).thenReturn(response)
            presenter.getTeamAway(id)
        }
    }
}