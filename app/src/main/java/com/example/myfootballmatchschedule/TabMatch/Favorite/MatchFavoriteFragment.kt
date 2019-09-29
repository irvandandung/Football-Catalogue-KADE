package com.example.myfootballmatchschedule.TabMatch.Favorite

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.DetailMatch.DetailMatchActivity
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.db.Favorite
import com.example.myfootballmatchschedule.db.database
import org.jetbrains.anko.db.select
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MatchFavoriteFragment : Fragment(), AnkoComponent<Context>  {
    private var favoritee : MutableList<Favorite> = mutableListOf()
//    private lateinit var database : MyDatabaseOpenHelper
    private lateinit var adapter: MatchFavoriteAdapter
    private lateinit var listMatch: RecyclerView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = MatchFavoriteAdapter(favoritee) {
            Log.d("idevent", it.idEvent)
            context?.startActivity<DetailMatchActivity>(
                "idEvent" to "${it.idEvent}",
                "id_home_team" to "${it.homeTeamId}",
                "id_away_team" to "${it.awayTeamId}"
            )
        }


        listMatch.adapter = adapter
        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favoritee.clear()
        context?.database?.use{
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favoritee.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
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

                listMatch = recyclerView {
                    id = R.id.rv_team_fav
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }
}