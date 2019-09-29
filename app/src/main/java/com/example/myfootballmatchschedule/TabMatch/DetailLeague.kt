package com.example.myfootballmatchschedule.TabMatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchMain
import kotlinx.android.synthetic.main.activity_detail_league.*
import android.view.MenuItem


class DetailLeague : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(com.example.myfootballmatchschedule.R.layout.activity_detail_league)
        viewpager_main.adapter = AdapterDetailLeague(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.myfootballmatchschedule.R.menu.menu_search2, menu)

        menu?.findItem(com.example.myfootballmatchschedule.R.id.search_match)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            com.example.myfootballmatchschedule.R.id.search_match -> {
                val i = Intent(this, SearchMatchMain::class.java)
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
}
