package com.example.myfootballmatchschedule.TabMatch

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchMain
import com.example.myfootballmatchschedule.SearchTeam.SearchTeamMain
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_detail_league.*


class DetailLeague : AppCompatActivity() {

    private var menu: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.activity_detail_league)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        /**
         * set default
         */
        val fragment = ContainerMatchFragment()
        addFragment(fragment)
//        viewpager_main.adapter = AdapterDetailLeague(supportFragmentManager)
//        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search2, menu)

        menu?.findItem(R.id.search_match)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_match -> {
                var i = Intent(this, SearchMatchMain::class.java)
                when (menu) {
                    1 ->   i = Intent(this, SearchMatchMain::class.java)
                    2 ->  i = Intent(this, SearchTeamMain::class.java)}
//                val i = Intent(this, SearchTeamMain::class.java)
                this.startActivity(i)
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    val fragment = ContainerMatchFragment()
                    addFragment(fragment)
                    menu = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    val fragment = ContainerTeamFragment()
                    addFragment(fragment)
                    menu = 2
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
