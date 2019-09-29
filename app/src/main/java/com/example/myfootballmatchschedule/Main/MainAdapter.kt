package com.example.myfootballmatchschedule.Main

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*

class MainAdapter(private val league: List<ModelLeague>, val listener: (ModelLeague) -> Unit) :
    RecyclerView.Adapter<LeagueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LeagueViewHolder(LeagueUI().createView(AnkoContext.Companion.create(parent.context, parent)))

    override fun getItemCount(): Int = league.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(league[position], listener)
    }

}

class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val leagueBadge: ImageView = view.find(R.id.league_badge)
    private val leagueName: TextView = view.find(R.id.league_name)
    fun bindItem(
        league: ModelLeague,
        listener: (ModelLeague) -> Unit
    ) {
        Picasso.get().load(league.leagueImage).into(leagueBadge)
        leagueName.text = league.leagueName

        itemView.setOnClickListener { listener(league) }
    }
}

class LeagueUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.league_badge
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.league_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }
            }
        }
    }
}