package com.brosseau.julien.tp1.ui.match_5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.brosseau.julien.tp1.MainActivity
import com.brosseau.julien.tp1.R


class Match5Fragment : Fragment() {

    private lateinit var match5ViewModel: Match5ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        match5ViewModel =
            ViewModelProviders.of(this).get(Match5ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_match_5, container, false)





        val matchID: TextView = root.findViewById(R.id.text_match_2)
        (activity as MainActivity).getInfoAndUpdateUI("5","idMatch",matchID)

        val textTeam1: TextView = root.findViewById(R.id.text_team_1)
        (activity as MainActivity).getInfoAndUpdateUI("5","nomEquipe1",textTeam1)

        val textTeam2: TextView = root.findViewById(R.id.text_team_2)
        (activity as MainActivity).getInfoAndUpdateUI("5","nomEquipe2",textTeam2)

        val textTemps: TextView = root.findViewById(R.id.text_temps)
        (activity as MainActivity).getInfoAndUpdateUI("5","chronometreSec",textTemps)

        val textPeriode: TextView = root.findViewById(R.id.text_period)
        (activity as MainActivity).getInfoAndUpdateUI("5","PeriodeEnCours",textPeriode)

        val textScore1: TextView = root.findViewById(R.id.text_score_1)
        (activity as MainActivity).getInfoAndUpdateUI("5","scoreP1",textScore1)

        val textScore2: TextView = root.findViewById(R.id.text_score_2)
        (activity as MainActivity).getInfoAndUpdateUI("5","scoreP2",textScore2)

        val textScore3: TextView = root.findViewById(R.id.text_score_3)
        (activity as MainActivity).getInfoAndUpdateUI("5","scoreP3",textScore3)


        val textPenalites: TextView = root.findViewById(R.id.text_event)
        (activity as MainActivity).getInfoAndUpdateUI("5","penalites",textPenalites)





        /*
        match3ViewModel.matchID.observe(this, Observer { matchID.text = it })
        match3ViewModel.testText2.observe(this, Observer { textTeam1.text = it })
        match3ViewModel.nomEquipe2.observe(this, Observer { team2.text = it })
        */

        return root
    }

    fun updateInfos(){


    }

}