package com.brosseau.julien.tp1.ui


import android.util.Log
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.util.decodeString
import io.ktor.util.moveToByteArray
import kotlinx.android.synthetic.main.fragment_match_2.*
import kotlinx.coroutines.Dispatchers
import java.net.InetSocketAddress
import kotlinx.coroutines.*
import kotlinx.coroutines.io.*
import kotlinx.io.charsets.decodeUTF8Line
import java.nio.ByteBuffer
import java.nio.charset.Charset

class Client {
    companion object {
        private fun runRequest(req:String):String {
            var rep = ""
            runBlocking {
                val socket =
                    aSocket(ActorSelectorManager(Dispatchers.IO)).tcp()
                        .connect(InetSocketAddress("192.168.43.174", 9002))
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
            )



            val stringBuilder: StringBuilder = StringBuilder(rep)
            val jsonObject: JsonObject = parser.parse(stringBuilder) as JsonObject

            // Recuperer un objet JSON du tableau
            obj = jsonObject.obj("match")


            return obj!!.string(id_info)
        }





    }
}
