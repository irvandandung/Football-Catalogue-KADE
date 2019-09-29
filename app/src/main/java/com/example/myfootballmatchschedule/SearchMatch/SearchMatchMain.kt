package com.example.myfootballmatchschedule.SearchMatch

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.DetailMatch.DetailMatchActivity
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.R
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class SearchMatchMain : AppCompatActivity(), SearchMatchView {
    private lateinit var matchesList: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var matches: MutableList<ModelMatch> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: SearchMatchAdapter
    private var query: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        linearLayout {
            lparams(width = matchParent, height = matchParent)
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

                    matchesList = recyclerView {
                        id = R.id.rv_match_list
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(applicationContext)
                    }
                }
            }
        }
        adapter = SearchMatchAdapter(matches) {
            startActivity<DetailMatchActivity>(
                "idEvent" to "${it.idEvent}",
                "id_home_team" to "${it.idHomeTeam}",
                "id_away_team" to "${it.idAwayTeam}"
            )
        }
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this, request, gson)

        matchesList.adapter = adapter
        swipeRefresh.onRefresh {
            presenter.getSearchMatchEvent(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu?.findItem(R.id.search_match)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.isIconified = false
        searchView.requestFocusFromTouch()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                presenter.getSearchMatchEvent(text)
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showMatchList(data: List<ModelMatch>) {
        hideLoading()
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}