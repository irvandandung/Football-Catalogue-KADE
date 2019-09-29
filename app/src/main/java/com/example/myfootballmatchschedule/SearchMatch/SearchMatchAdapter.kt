package com.example.myfootballmatchschedule.SearchMatch

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.R
import org.jetbrains.anko.*

class SearchMatchAdapter(private val matches: List<ModelMatch>, val listener: (ModelMatch) -> Unit) :
    RecyclerView.Adapter<SearchMatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchMatchViewHolder(
        SearchMatchUI().createView(
            AnkoContext.Companion.create(parent.context, parent)
        )
    )

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: SearchMatchViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

}

class SearchMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(8)
                }
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(16)
                        orientation = LinearLayout.VERTICAL
                        backgroundColor = R.color.colorAccent

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            textView {
                                id = R.id.home_score
                                rightPadding = dip(10)
                                textSize = 16f
                            }

                            textView {
                                id = R.id.away_score
                                leftPadding = dip(10)
                                textSize = 16f
                            }
                        }

                        textView {
                            id = R.id.event_name
                            text = context.getString(R.string.vs)
                            textSize = 18f
                            this.gravity = Gravity.CENTER_HORIZONTAL
                        }
                    }
                }
            }
        }
    }

}

class SearchMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventName: TextView = view.find(R.id.event_name)
    private val homeScore: TextView = view.find(R.id.home_score)
    private val awayScore: TextView = view.find(R.id.away_score)

    fun bindItem(
        match: ModelMatch,
        listener: (ModelMatch) -> Unit
    ) {
        eventName.text = match.eventName
        homeScore.text = match.homeScore
        awayScore.text = match.awayScore

        itemView.setOnClickListener { listener(match) }
    }
}
