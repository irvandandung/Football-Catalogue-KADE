package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.DetailMatch.DetailMatchPresenter
import com.example.myfootballmatchschedule.DetailMatch.DetailMatchView
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchMain
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchPresenter
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {
    private lateinit var presenter: DetailMatchPresenter
    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: DetailMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(
            view,
            apiRepository,
            gson,
            CoroutineContextProviderTest()
        )
    }

    @Test
    fun getMatchList() {
        val matchs: MutableList<ModelMatch> = mutableListOf()
        val match: MutableList<ModelMatch> = mutableListOf()
        val response = MatchResponse(matchs, match)
        val id = "441613"

        GlobalScope.launch(contex.main) {
            Mockito.`when`(
                gson.fromJson(
                    apiRepository.doRequest(ApiUrl.getDetailMatch(id)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)
            presenter.getMatchDetaile(id)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(matchs)
            Mockito.verify(view).hideLoading()
        }
    }
}