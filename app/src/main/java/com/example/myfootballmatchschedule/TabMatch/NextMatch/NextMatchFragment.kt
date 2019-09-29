package com.example.myfootballmatchschedule.TabMatch.NextMatch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.DetailMatch.DetailMatchActivity
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.TabMatch.MatchAdapter
import com.example.myfootballmatchschedule.TabMatch.MatchView
import com.example.myfootballmatchschedule.invisible
import com.example.myfootballmatchschedule.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class NextMatchFragment : Fragment(), AnkoComponent<Context>, MatchView {
    private var matches: MutableList<ModelMatch> = mutableListOf()
    private val leagues: MutableList<ModelLeague> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: MatchAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var listMatch: RecyclerView
    private var leagueId: String? = ""
    private var fixture = 1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(requireContext()))
    }

    override fun showLeague(data: List<ModelLeague>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    android.R.color.darker_gray,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listMatch = recyclerView {
                        id = R.id.list_match_next
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = MatchAdapter(matches) {
            context?.startActivity<DetailMatchActivity>(
                "idEvent" to "${it.idEvent}",
                "id_home_team" to "${it.idHomeTeam}",
                "id_away_team" to "${it.idAwayTeam}"
            )
        }
        leagueId = activity!!.intent.getStringExtra("id")
        listMatch.adapter = adapter
        val request = ApiRepository()
        val gson = Gson()

            presenter = NextMatchPresenter(this, request, gson, fixture)
            presenter.getNextMatchpresenter(leagueId)

            swipeRefresh.onRefresh {
                presenter.getNextMatchpresenter(leagueId)

        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchList(data: List<ModelMatch>) {
        swipeRefresh.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }
}