package com.brosseau.julien.tp1.ui.match_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beust.klaxon.Json
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.readUTF8Line
import kotlinx.coroutines.io.writeStringUtf8
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
//import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.TextView
import com.brosseau.julien.tp1.MainActivity
import com.brosseau.julien.tp1.R
import kotlinx.android.synthetic.main.fragment_match_2.*
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import java.nio.file.Files.find


//import android.R




class Match2ViewModel : ViewModel() {

    //init {

        /*
        var mID = Thread({getInformation("matchID")}).run()
        var nE1 = Thread(getInformation("nomEquipe1")).run()
        var nE2 = Thread(getInformation("nomEquipe2")).run()
        print(mID)
        print(nE1)
        print(nE2)

         */
        //getInfoAndUpdateUI("nomEquipe1")
    //




    //}

    /*
    // Recuperer "matchID"
    private val _matchID = MutableLiveData<String>().apply { value = getInformation("matchID")}
    val matchID: LiveData<String> = _matchID
    // Recuperer "nomEquipe1" // Pour l'instant c'est pas le cas
    private val _testText = MutableLiveData<String>().apply { value = "Ceci est l'équipe 1" }
    val testText2: LiveData<String> = _testText
    // Recuperer "nomEquipe2"
    private val _nomEquipe2 = MutableLiveData<String>().apply { value = getInformation("nomEquipe2") }
    val nomEquipe2: LiveData<String> = _nomEquipe2
    */

}