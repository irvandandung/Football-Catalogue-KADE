package com.example.myfootballmatchschedule.Team.Favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.ModelData.Teams
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.Team.TeamDetailActivity
import com.example.myfootballmatchschedule.db.TeamObject
import com.example.myfootballmatchschedule.db.database
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavoriteTeamFragment : Fragment() {

    private var favorites: MutableList<TeamObject> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteTeamAdapter(favorites) {
            val teams = Teams(
                it.teamId,
                it.teamBadge,
                it.teamName,
                it.teamStadium,
                it.teamYear,
                it.teamDesc
            )
            startActivity<TeamDetailActivity>(
                "TEAM_PARCEL" to teams
            )
        }

        listTeam.adapter = adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(TeamObject.TABLE_TEAM)
            val favorite = result.parseList(classParser<TeamObject>())
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
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

                listTeam = recyclerView {
                    id = R.id.rv_team_fav
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}