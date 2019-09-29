package com.example.myfootballmatchschedule.TabMatch.DetailLeagueFragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.myfootballmatchschedule.Connection.ApiRepository
import com.example.myfootballmatchschedule.ModelData.ModelLeague
import com.example.myfootballmatchschedule.R
import com.example.myfootballmatchschedule.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_league.*
import org.jetbrains.anko.support.v4.intentFor

class DetailLeagueFragment : Fragment(), DetailLeagueView {
    private var idLeague: String? = ""
    private lateinit var league: ModelLeague
    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var progressBar: ProgressBar
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showLeagueDetail(data: List<ModelLeague>) {
        league = ModelLeague(
            data[0].leagueId,
            data[0].leagueName,
            data[0].leagueImage,
            data[0].leagueDescription
        )

        tv_Judul?.text = league.leagueName
        tv_Deskripsi?.text = league.leagueDescription
        if (badge != null) {
            Picasso.get().load(league.leagueImage).into(badge)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_league, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()
        idLeague = activity!!.intent.getStringExtra("id")
        progressBar = pg
        presenter = DetailLeaguePresenter(this, request, gson)
        presenter.getDetailLeague(idLeague!!)
    }


}
