package com.brosseau.julien.tp1


import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.provider.Settings.Global.getString
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import java.net.InetSocketAddress
import kotlinx.coroutines.*
import kotlinx.coroutines.io.*
import java.nio.ByteBuffer

class Client {
    companion object {
        private fun runRequest(req:String,port:Int,activite:MainActivity):String {
            var rep = ""



            runBlocking {
                val socket =
                    aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
                        .connect(InetSocketAddress(activite.getIP(), port))
                val input = socket.openReadChannel()
                val output = socket.openWriteChannel(autoFlush = true)
                val charset = Charsets.UTF_8

                output.writeStringUtf8(req)

                var charac = ""
                while (charac != "\n") {
                    // Create a ByteBuffer using a byte array
                    val bytes = ByteArray(1)
                    val buffer = ByteBuffer.wrap(bytes)

                    input.readAvailable(bytes)
                    charac = bytes.toString(charset)
                    rep += charac


                }
                println("Server said: $rep")


            }
            return rep
        }





        fun getInformationMatch(id_match:String, id_info:String, activite: MainActivity):String? {

            var obj: JsonObject?

            // Recuperer le JSON complet de la requete liee
            val parser: Parser = Parser.default()
            println("av&ant co")

            var rep = runRequest(
                "{ \"objectType\": \"request\", \"objectRequested\" : \"Match\", \"idObjectRequested\" : \"$id_match\" }\n"
            ,activite.getpMatch(),activite)



            val stringBuilder: StringBuilder = StringBuilder(rep)
            val jsonObject: JsonObject = parser.parse(stringBuilder) as JsonObject

            // Recuperer un objet JSON du tableau
            obj = jsonObject.obj("match")


            return obj!!.string(id_info)
        }



        fun getMatchsEnCours(activite: MainActivity):Array<String>{

            var obj: JsonObject?

            // Recuperer le JSON complet de la requete liee
            val parser: Parser = Parser.default()
            println("av&ant co")

            var rep = runRequest(
                "{ \"objectType\": \"request\", \"objectRequested\" : \"ListeDesMatchs\", \"idObjectRequested\" : \"\" }\n"
            ,activite.getpMatch(),activite)

            val stringBuilder: StringBuilder = StringBuilder(rep)
            val jsonObject: JsonObject = parser.parse(stringBuilder) as JsonObject
            val ids = jsonObject.array<String>("matchIDs")
            var rep_f =""
            var i = 0
            for (id in ids!!){
                rep_f += "$id\n"
                i+=1
            }
            //activite.hidePasMatchs(i)


            return arrayOf(rep_f,i.toString())
        }

        fun createBet(montant:String, match:String, equipe:String, activite: MainActivity):String{
            var rep = ""
            if((montant.toInt() > 0) and (match.toInt() >=1) and (match.toInt() <=5) and (equipe.toInt() >=0) and (match.toInt() <=2)) {
                val req =
                    "{\"objectType\":\"betUpdate\",\"Bet\":{\"matchID\":\"$match\",\"miseSur\":$equipe,\"sommeMisee\":$montant}}\n"
                rep = runRequest(req,activite.getpBet(),activite)
            }


            return rep
        }



    }
}
