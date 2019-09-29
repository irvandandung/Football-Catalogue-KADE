package com.example.myfootballmatchschedule.DetailMatch

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.myfootballmatchschedule.*
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.DetailMatch.PresenterTeam.TeamAwayPresenter
import com.example.myfootballmatchschedule.DetailMatch.PresenterTeam.TeamHomePresenter
import com.example.myfootballmatchschedule.DetailMatch.TeamView.TeamAwayView
import com.example.myfootballmatchschedule.DetailMatch.TeamView.TeamHomeView
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.ModelData.Teams
import com.example.myfootballmatchschedule.db.Favorite
import com.example.myfootballmatchschedule.db.database
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), DetailMatchView, TeamHomeView, TeamAwayView {
    override fun showTeamHome(data: List<Teams>) {
        teams = Teams(
            data[0].teamId,
            data[0].teamBadge
        )
        Glide.with(applicationContext).load(data[0].teamBadge).into(teamBadgeHome)
    }

    override fun showTeamAway(data: List<Teams>) {
        teams = Teams(
            data[0].teamId,
            data[0].teamBadge
        )
        Glide.with(applicationContext).load(data[0].teamBadge).into(teamBadgeAway)
    }
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchDetail(data: List<ModelMatch>) {
        match = ModelMatch(
            data[0].idEvent,
            data[0].eventName,
            data[0].eventDate,
            data[0].eventTime,
            data[0].idHomeTeam,
            data[0].idAwayTeam,
            data[0].homeTeamName,
            data[0].awayTeamName,
            data[0].homeScore,
            data[0].awayScore,
            data[0].homeGoalsDetail,
            data[0].awayGoalsDetail,
            data[0].homeShots,
            data[0].awayShots,
            data[0].homeLineupGoalKeeper,
            data[0].homeLineupDefense,
            data[0].homeLineupMidfield,
            data[0].awayLineupGoalKeeper,
            data[0].awayLineupDefense,
            data[0].awayLineupMidfield
        )
        swipeRefresh.isRefreshing = false

        val date = strTodate(data[0].eventDate)
        val dateTime = toGMTFormat(data[0].eventDate, data[0].eventTime)
        dateEvent.text = changeFormatDate(date)
        timeEvent.text = SimpleDateFormat("HH:mm").format(dateTime)

        homeName.text = data[0].homeTeamName
        awayName.text = data[0].awayTeamName
        goalsHome.text = data[0].homeGoalsDetail
        goalsAway.text = data[0].awayGoalsDetail
        homeScore.text = data[0].homeScore
        awayScore.text = data[0].awayScore
        shootHome.text = data[0].homeShots
        shootAway.text = data[0].awayShots
        homeGoalKeeper.text = data[0].homeLineupGoalKeeper
        homeDefence.text = data[0].homeLineupDefense
        homeMildfield.text = data[0].homeLineupMidfield
        awayGoalKeeper.text = data[0].awayLineupGoalKeeper
        awayDefence.text = data[0].awayLineupDefense
        awayMildfield.text = data[0].awayLineupMidfield

    }
    private lateinit var presenterTeamHome: TeamHomePresenter
    private lateinit var presenterTeamAway: TeamAwayPresenter
    private lateinit var teams: Teams
    private lateinit var id: String
    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String
    //    private lateinit var idHomeTeam: String
//    private lateinit var idAwayTeam: String
    private lateinit var match: ModelMatch
    private lateinit var presenter: DetailMatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamBadgeHome: ImageView
    private lateinit var teamBadgeAway: ImageView

    private lateinit var dateEvent: TextView
    private lateinit var timeEvent: TextView
    private lateinit var homeName: TextView
    private lateinit var awayName: TextView
    private lateinit var homeScore: TextView
    private lateinit var awayScore: TextView
    private lateinit var goalsHome: TextView
    private lateinit var goalsAway: TextView
    private lateinit var shootHome: TextView
    private lateinit var shootAway: TextView
    private lateinit var homeGoalKeeper: TextView
    private lateinit var awayGoalKeeper: TextView
    private lateinit var homeDefence: TextView
    private lateinit var awayDefence: TextView
    private lateinit var homeMildfield: TextView
    private lateinit var awayMildfield: TextView
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            progressBar = progressBar {
            }.lparams {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
            }

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    android.R.color.darker_gray,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scrollView {
                    isVerticalScrollBarEnabled = false

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL
                        padding = dip(16)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER

                            dateEvent = textView {
                                id = R.id.date_event
                                text = "date/date/date"
                                textSize = 16f
                                gravity = Gravity.CENTER
                                bottomPadding = dip(4)
                            }

                            timeEvent = textView {
                                id = R.id.time_event
                                textSize = 16f
                                gravity = Gravity.CENTER
                                bottomPadding = dip(4)
                            }
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)

                            teamBadgeHome = imageView {
                                id = R.id.badge_home
                                imageResource = R.drawable.ic_launcher_background
                            }.lparams(height = dip(75)) {
                                alignParentLeft()
                            }

                            homeName = textView {
                                id = R.id.detail_home_name
                                rightPadding = dip(18)
                                textSize = 22f
                                maxWidth = dip(140)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentLeft()
                                below(R.id.badge_home)
                            }

                            homeScore = textView {
                                id = R.id.detail_home_score
                                text = "1"
                                textSize = 22f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                leftOf(R.id.vs)
                                centerInParent()
                            }

                            textView {
                                id = R.id.vs
                                text = "VS"
                                rightPadding = dip(12)
                                leftPadding = dip(12)
                                textSize = 22f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                centerInParent()
                            }

                            awayScore = textView {
                                id = R.id.detail_away_score
                                text = "2"
                                textSize = 22f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                rightOf(R.id.vs)
                                centerInParent()
                            }

                            teamBadgeAway = imageView {
                                id = R.id.badge_away
                                imageResource = R.drawable.ic_launcher_background
                            }.lparams(height = dip(75)) {
                                alignParentRight()
                            }

                            awayName = textView {
                                id = R.id.detail_away_name
                                leftPadding = dip(18)
                                textSize = 22f
                                maxWidth = dip(140)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentRight()
                                below(R.id.badge_away)
                            }
                        }


                        textView {
                            text = context.getString(R.string.goals)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                weightSum = 2f
                            }
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            goalsHome = textView {
                                id = R.id.detail_goals_home
                                gravity = Gravity.START
                                textSize = 18f
                            }.lparams(width = wrapContent, height = matchParent) { weight = 1f }
                            goalsAway = textView {
                                id = R.id.detail_goals_away
                                gravity = Gravity.END
                                textSize = 18f
                            }.lparams(width = wrapContent, height = matchParent) { weight = 1f }
                        }

                        textView {
                            text = context.getString(R.string.shots)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)
                            shootHome = textView {
                                id = R.id.detail_home_shoots
                                textSize = 18f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentLeft()
                            }

                            shootAway = textView {
                                id = R.id.detail_away_shoots
                                textSize = 18f
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentRight()
                            }
                        }

                        textView {
                            //lineup
                            text = context.getString(R.string.lineups)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        textView {
                            //lineup
                            text = context.getString(R.string.goal_keeper)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)
                            homeGoalKeeper = textView {
                                id = R.id.home_goal_keeper
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentLeft()
                            }

                            awayGoalKeeper = textView {
                                id = R.id.away_goal_keeper
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentRight()
                            }
                        }

                        textView {
                            //defence
                            text = context.getString(R.string.deff)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)
                            homeDefence = textView {
                                id = R.id.home_defence
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentLeft()
                            }

                            awayDefence = textView {
                                id = R.id.away_defence
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentRight()
                            }
                        }

                        textView {
                            //defence
                            text = context.getString(R.string.mildfield)
                            gravity = Gravity.CENTER_HORIZONTAL
                            rightPadding = dip(18)
                            textSize = 18f
                        }

                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)
                            homeMildfield = textView {
                                id = R.id.home_mildfield
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentLeft()
                            }

                            awayMildfield = textView {
                                id = R.id.away_mildfield
                                textSize = 18f
                                maxWidth = dip(100)
                            }.lparams(width = wrapContent, height = wrapContent) {
                                alignParentRight()
                            }
                        }
                    }
                }
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        idHomeTeam = intent.getStringExtra("id_home_team")
        idAwayTeam = intent.getStringExtra("id_away_team")
        id = intent.getStringExtra("idEvent")
        Log.i("idEvent", id)
        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getMatchDetaile(id)

        presenterTeamAway = TeamAwayPresenter(this, request, gson)
        presenterTeamAway.getTeamAway(idAwayTeam)

        presenterTeamHome = TeamHomePresenter(this, request, gson)
        presenterTeamHome.getHomeAway(idHomeTeam)
        swipeRefresh.onRefresh {
            presenter.getMatchDetaile(id)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.EVENT_DATE to match.eventDate,
                    Favorite.EVENT_TIME to match.eventTime,
                    Favorite.EVENT_ID to match.idEvent,
                    Favorite.EVENT_NAME to match.eventName,
                    Favorite.HOME_SCORE to match.homeScore,
                    Favorite.AWAY_SCORE to match.awayScore,
                    Favorite.NAME_HOME to match.homeTeamName,
                    Favorite.NAME_AWAY to match.awayTeamName,
                    Favorite.HOME_TEAM_ID to match.idHomeTeam,
                    Favorite.AWAY_TEAM_ID to match.idAwayTeam,
                    Favorite.HOME_GOALS_DETAIL to match.homeGoalsDetail,
                    Favorite.AWAY_GOALS_DETAIL to match.awayGoalsDetail,
                    Favorite.HOME_SHOTS to match.homeShots,
                    Favorite.AWAY_SHOTS to match.awayShots,
                    Favorite.BADGE_HOME to teams.teamBadge,
                    Favorite.BADGE_AWAY to teams.teamBadge,
                    Favorite.HOME_GOAL_KEEPER to match.homeLineupGoalKeeper,
                    Favorite.HOME_DEFENCE to match.homeLineupDefense,
                    Favorite.HOME_MILDFIELD to match.homeLineupMidfield,
                    Favorite.AWAY_GOAL_KEEPER to match.awayLineupGoalKeeper,
                    Favorite.AWAY_DEFENCE to match.awayLineupDefense,
                    Favorite.AWAY_MILDFIELD to match.awayLineupMidfield
                )
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            Toast.makeText(applicationContext, "Error" + e, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                    "id" to id
                )
            }
            swipeRefresh.snackbar("Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}