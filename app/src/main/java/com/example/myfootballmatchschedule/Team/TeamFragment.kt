package com.example.myfootballmatchschedule.Team

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
import com.example.myfootballmatchschedule.ModelData.Teams
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.invisible
import com.example.myfootballmatchschedule.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {
    private var teams :  MutableList<Teams> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private var leagueId: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(requireContext()))
    }
    override fun createView(ui: AnkoContext<Context>): LinearLayout {
        return with(ui) {
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

                        listTeam = recyclerView {
                            id = R.id.list_team
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = TeamAdapter(teams){
            startActivity<TeamDetailActivity>(
                "TEAM_PARCEL" to it
            )
        }
        listTeam.adapter = adapter
        leagueId = activity!!.intent.getStringExtra("id")
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        presenter.getTeamList(leagueId)

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueId)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Teams>) {
        hideLoading()
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}