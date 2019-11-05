package com.brosseau.julien.tp1


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
        private fun runRequest(req:String,port:Int):String {
            var rep = ""
            runBlocking {
                val socket =
                    aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
                        .connect(InetSocketAddress("192.168.43.174", port))
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





        fun getInformationMatch(id_match:String, id_info:String):String? {

            var obj: JsonObject?

            // Recuperer le JSON complet de la requete liee
            val parser: Parser = Parser.default()
            println("av&ant co")

            var rep = runRequest(
                "{ \"objectType\": \"request\", \"objectRequested\" : \"Match\", \"idObjectRequested\" : \"$id_match\" }\n"
            ,9002)



            val stringBuilder: StringBuilder = StringBuilder(rep)
            val jsonObject: JsonObject = parser.parse(stringBuilder) as JsonObject

            // Recuperer un objet JSON du tableau
            obj = jsonObject.obj("match")


            return obj!!.string(id_info)
        }



        fun getMatchsEnCourS():String{

            var obj: JsonObject?

            // Recuperer le JSON complet de la requete liee
            val parser: Parser = Parser.default()
            println("av&ant co")

            var rep = runRequest(
                "{ \"objectType\": \"request\", \"objectRequested\" : \"ListeDesMatchs\", \"idObjectRequested\" : \"\" }\n"
            ,9002)

            val stringBuilder: StringBuilder = StringBuilder(rep)
            val jsonObject: JsonObject = parser.parse(stringBuilder) as JsonObject
            val ids = jsonObject.array<String>("matchIDs")
            var rep_f =""
            for (id in ids!!){
                rep_f += "$id\n"
            }


            return rep_f
        }

        fun createBet(montant:String, match:String, equipe:String):String{
            var rep = ""
            if((montant.toInt() > 0) and (match.toInt() >=1) and (match.toInt() <=5) and (equipe.toInt() >=0) and (match.toInt() <=2)) {
                val req =
                    "{\"objectType\":\"betUpdate\",\"Bet\":{\"matchID\":\"$match\",\"miseSur\":$equipe,\"sommeMisee\":$montant}}\n"
                rep = runRequest(req,9001)
            }


            return rep
        }



    }
}
