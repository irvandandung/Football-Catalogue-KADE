package com.example.myfootballmatchschedule.SearchTeam

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfootballmatchschedule.ModelData.Teams
import com.example.myfootballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchTeamAdapter(private val teams: List<Teams>, val listener: (Teams) -> Unit) :
    RecyclerView.Adapter<SearchTeamAdapter.SearchTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchTeamViewHolder(
            SearchTeamUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )


    override fun getItemCount() = teams.size

    override fun onBindViewHolder(holder: SearchTeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)

    }

    class SearchTeamUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.HORIZONTAL

                    imageView {
                        id = R.id.team_badge
                    }.lparams(width = dip(50), height = dip(50))

                    textView {
                        id = R.id.team_name
                        textSize = 16f
                    }.lparams {
                        margin = dip(15)
                    }
                }
            }
        }

    }

    class SearchTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.findViewById(R.id.team_badge)
        private val teamName: TextView = view.findViewById(R.id.team_name)

        fun bindItem(
            teams: Teams,
            listener: (Teams) -> Unit
        ) {
//            Picasso.get().load(teams.teamBadge).into(teamBadge)
            Glide.with(itemView.context).load(teams.teamBadge).into(teamBadge)
            teamName.text = teams.teamName

            itemView.onClick {
                listener(teams)
            }
        }
    }
}
