package com.example.myfootballmatchschedule.TabMatch

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballmatchschedule.ModelData.ModelMatch
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.changeFormatDate
import com.example.myfootballmatchschedule.strTodate
import com.example.myfootballmatchschedule.toGMTFormat
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class MatchAdapter(private val matches: List<ModelMatch>, val listener: (ModelMatch) -> Unit) :
    RecyclerView.Adapter<LastMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LastMatchViewHolder(MatchUI().createView(AnkoContext.Companion.create(parent.context, parent)))

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: LastMatchViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

}

@SuppressLint("SimpleDateFormat")
class LastMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val eventName: TextView = view.find(R.id.last_event_name)
    private val eventDate: TextView = view.find(R.id.date_event)
    private val eventTime: TextView = view.find(R.id.time_event)
    fun bindItem(
        modelMatch: ModelMatch,
        listener: (ModelMatch) -> Unit
    ) {
        val date = strTodate(modelMatch.eventDate)
        val dateTime = toGMTFormat(modelMatch.eventDate, modelMatch.eventTime)

        eventName.text = modelMatch.eventName
        eventDate.text = changeFormatDate(date)
        eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)

        itemView.setOnClickListener { listener(modelMatch) }
    }


}

class MatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(8)
                }
                orientation = LinearLayout.VERTICAL


                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    orientation = LinearLayout.VERTICAL
                    backgroundColor = R.color.colorAccent


                    textView {
                        id = R.id.date_event
                        textSize = 16f
                        this.gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView {
                        id = R.id.time_event
                        textSize = 16f
                        this.gravity = Gravity.CENTER_HORIZONTAL
                    }

                    textView {
                        id = R.id.last_event_name
                        text = context.getString(R.string.vs)
                        textSize = 18f
                        this.gravity = Gravity.CENTER_HORIZONTAL
                    }
                }
            }
        }
    }
}
