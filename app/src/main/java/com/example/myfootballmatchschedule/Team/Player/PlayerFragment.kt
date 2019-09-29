package com.example.myfootballmatchschedule.Team.Player

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.ModelData.Players
import com.example.myfootballmatchschedule.R
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), AnkoComponent<Context>, PlayerView {

    private var players: MutableList<Players> = mutableListOf()
    private lateinit var presenter: PlayerPresenter

    private lateinit var adapter: PlayersAdapter
    private lateinit var playerList: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var teamId: String

    companion object {
        fun newFragment(teamId: String): PlayerFragment {
            val fragment = PlayerFragment()
            fragment.teamId = teamId

            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getPlayer(teamId)

        adapter = PlayersAdapter(players) {
            //            requireContext().startActivity<PlayerDetailActivity>("PLAYER" to it)
            startActivity<DetailPlayerActivity>(
                "PLAYER_PARCEL" to it
            )
        }

        playerList.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getPlayer(teamId)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        linearLayout {
            orientation = LinearLayout.VERTICAL
            lparams(width = matchParent, height = matchParent)
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

                playerList = recyclerView {
                    id = R.id.rv_player
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showPlayer(data: List<Players>) {
        hideLoading()
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }
}