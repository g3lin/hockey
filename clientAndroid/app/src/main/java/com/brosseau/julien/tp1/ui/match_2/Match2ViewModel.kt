package com.brosseau.julien.tp1.ui.match_2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.network.sockets.openReadChannel
import io.ktor.network.sockets.openWriteChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.io.readUTF8Line
import kotlinx.coroutines.io.writeStringUtf8
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress
import org.json.JSONObject

class Match2ViewModel : ViewModel() {

    private fun onConnect(requete: String) {
        var response: String?
        response = "Not Found"

        runBlocking {
            val socket = aSocket(ActorSelectorManager(Dispatchers.IO)).tcp().connect(
                InetSocketAddress("127.0.0.1", 2323)
            )
            val input = socket.openReadChannel()
            val output = socket.openWriteChannel(autoFlush = true)

            output.writeStringUtf8(requete)
            response = input.readUTF8Line()

            println("Server said: '$response'")
        }
        return response as Unit
    }

    private fun getInformation(id_info: String) {
        // Pour toutes infomrations concernant cette section :
        // https://tutorielandroid.francoiscolin.fr/recupjson.php

        // Recuperer le JSON complet de la requete liee
        val jsonObject = JSONObject(onConnect(
            "{ \"objectType\": \"request\", \"objectRequested\" : \"Match\", \"idObjectRequested\" : \"1\" }"
        ).toString())

        // Recuperer un objet JSON du tableau
        val obj = JSONObject(jsonObject.getString("match"))

        return obj.getString(id_info) as Unit
    }

    // Recuperer "matchID"
    private val _matchID = MutableLiveData<String>().apply { value = getInformation("matchID").toString() }
    val matchID: LiveData<String> = _matchID
    // Recuperer "nomEquipe1" // Pour l'instant c'est pas le cas
    private val _testText = MutableLiveData<String>().apply { value = "Ceci est l'équipe 1" }
    val testText2: LiveData<String> = _testText
    // Recuperer "nomEquipe2"
    private val _nomEquipe2 = MutableLiveData<String>().apply { value = getInformation("nomEquipe2").toString() }
    val nomEquipe2: LiveData<String> = _nomEquipe2

}