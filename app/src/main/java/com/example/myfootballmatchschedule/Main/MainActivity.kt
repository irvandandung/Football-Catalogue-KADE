package com.example.myfootballmatchschedule.Main

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.Interface.MainView
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.Prsesenter.LeaguePresenter
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.TabMatch.DetailLeague
import com.example.myfootballmatchschedule.invisible
import com.example.myfootballmatchschedule.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainActivity : AppCompatActivity(), MainView {
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


    private lateinit var presenter: LeaguePresenter
    private lateinit var adapter: MainAdapter
    lateinit var listleague: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    var items: MutableList<ModelLeague> = mutableListOf()
    override fun showLeagueList(data: List<ModelLeague>) {
        swipeRefresh.isRefreshing = false
        items.clear()
        items.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listleague = recyclerView {
                        id = R.id.list_liga
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }

            adapter = MainAdapter(items) {
                context.startActivity<DetailLeague>(
                    "id" to "${it.leagueId}",
                    "image" to "${it.leagueImage}",
                    "name" to "${it.leagueName}",
                    "descrition" to "${it.leagueDescription}"

                )
            }

            listleague.adapter = adapter


            val request = ApiRepository()
            val gson = Gson()
            presenter = LeaguePresenter(this@MainActivity, request, gson)
            presenter.getLeaguee()
            swipeRefresh.onRefresh {
                presenter.getLeaguee()
            }
        }

    }
}
