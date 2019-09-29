package com.example.myfootballmatchschedule.TabMatch.Favorite

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.changeFormatDate
import com.example.myfootballmatchschedule.db.Favorite
import com.example.myfootballmatchschedule.strTodate
import com.example.myfootballmatchschedule.toGMTFormat
import org.jetbrains.anko.*
import java.text.SimpleDateFormat

class MatchFavoriteAdapter(private val matches: List<Favorite>, private val listener: (Favorite)->Unit) :
        RecyclerView.Adapter<MatchFavoriteViewHolder>(){

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MatchFavoriteViewHolder, position: Int) {
        holder.bindItem(matches[position], listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchFavoriteViewHolder {
        return MatchFavoriteViewHolder(
            MatchFavUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }
}

class MatchFavUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    bottomMargin = dip(8)
                }
                orientation = LinearLayout.VERTICAL

                linearLayout{
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(16)
                        orientation = LinearLayout.VERTICAL

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
}

class MatchFavoriteViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val eventName: TextView = view.find(R.id.last_event_name)
    private val eventDate: TextView = view.find(R.id.date_event)
    private val eventTime: TextView = view.find(R.id.time_event)

    @SuppressLint("SimpleDateFormat")
    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {
        val date = strTodate(favorite.dateEvent)
        val dateTime = toGMTFormat(favorite.dateEvent, favorite.dateTime)

        eventName.text = favorite.nameEvent
        eventDate.text = changeFormatDate(date)
        eventTime.text = SimpleDateFormat("HH:mm").format(dateTime)
        itemView.setOnClickListener { listener(favorite) }
    }
}
