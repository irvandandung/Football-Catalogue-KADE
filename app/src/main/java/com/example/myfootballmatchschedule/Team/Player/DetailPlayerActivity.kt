package com.example.myfootballmatchschedule.Team.Player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.example.myfootballmatchschedule.ModelData.Players
import com.example.myfootballmatchschedule.R
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailPlayerActivity : AppCompatActivity() {

    private lateinit var player: Players
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        player = intent.getParcelableExtra("PLAYER_PARCEL")
        passData()

    }

    private fun passData() {
        Glide.with(this).load(player.playerImage).into(img_detail_player)
        tv_detail_player_name.text = player.playerName
        tv_detail_player_team.text = player.playerTeam
        tv_detail_player_position.text = player.playerPosition
        tv_detail_player_desc.text = player.playerDescription
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
