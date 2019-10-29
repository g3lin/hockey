package com.brosseau.julien.tp1.ui.match_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brosseau.julien.tp1.R
import org.w3c.dom.Text

class Match2Fragment : Fragment() {

    private lateinit var match2ViewModel: Match2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        match2ViewModel =
            ViewModelProviders.of(this).get(Match2ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_match_2, container, false)

        val matchID: TextView = root.findViewById(R.id.text_match_2)
        val textTeam1: TextView = root.findViewById(R.id.text_team_1)
        val team2: TextView = root.findViewById(R.id.text_team_2)

        match2ViewModel.matchID.observe(this, Observer { matchID.text = it })
        match2ViewModel.testText2.observe(this, Observer { textTeam1.text = it })
        match2ViewModel.nomEquipe2.observe(this, Observer { team2.text = it })

        return root
    }
}