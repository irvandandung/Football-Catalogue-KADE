package com.example.myfootballmatchschedule.presenter

import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Connection.ApiUrl
import com.example.myfootballmatchschedule.CoroutineContextProviderTest
import com.example.myfootballmatchschedule.ModelData.MatchResponse
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchMain
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchPresenter
import com.example.myfootballmatchschedule.TabMatch.NextMatch.NextMatchPresenter
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {
    private lateinit var presenter : SearchMatchPresenter
    private val contex: CoroutineContextProviderTest = CoroutineContextProviderTest()

    @Mock
    private lateinit var view: SearchMatchMain

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(
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
                    apiRepository.doRequest(ApiUrl.getSearchMatch(id)).await(),
                    MatchResponse::class.java
                )
            ).thenReturn(response)
            presenter.getSearchMatchEvent(id)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(match)
            Mockito.verify(view).hideLoading()
        }
    }
}