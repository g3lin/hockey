package com.brosseau.julien.tp1.ui.match_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brosseau.julien.tp1.R

class Match1Fragment : Fragment() {

    private lateinit var match1ViewModel: Match1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        match1ViewModel =
            ViewModelProviders.of(this).get(Match1ViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_match_1, container, false)
        val textMatch: TextView = root.findViewById(R.id.text_match_1)
        val textTeam1: TextView = root.findViewById(R.id.text_team_1)

        match1ViewModel.text.observe(this, Observer {
            textMatch.text = it
        })

        return root
    }
}