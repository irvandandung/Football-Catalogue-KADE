package com.example.myfootballmatchschedule.TabMatch


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myfootballmatchschedule.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_container_team.*

class ContainerTeamFragment : Fragment() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabs: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_container_team, container, false)
        val view: View = inflater.inflate(R.layout.fragment_container_team, container, false)
        viewPager = view.findViewById(R.id.viewpager_maintwo)
        tabs = view.findViewById(R.id.tabs_maintwo)

        val fragmentAdapter = AdapterDetailLeagueTwo(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)

        return view
    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewpager_maintwo.adapter = AdapterDetailLeagueTwo(activity!!.supportFragmentManager)
//        tabs_maintwo.setupWithViewPager(viewpager_maintwo)
//    }
}
