package ca.udes

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import java.io.*
import java.util.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.network.util.*
import kotlin.coroutines.*
import kotlinx.coroutines.*
import kotlinx.coroutines.io.*


/**
 * Two mains are provided, you must first start EchoApp.Server, and then EchoApp.Client.
 * You can also start EchoApp.Server and then use a telnet client to connect to the echo server.
 */
object MatchTracker {
    val selectorManager = ActorSelectorManager(Dispatchers.IO)
    val DefaultPort = 9002

    object Server {
        @JvmStatic
        fun main(args: Array<String>) {
            runBlocking {
                val serverSocket = aSocket(selectorManager).tcp().bind(port = DefaultPort)
                println("Echo Server listening at ${serverSocket.localAddress}")
                while (true) {
                    val socket = serverSocket.accept()
                    println("Accepted $socket")
                    launch {
                        val read = socket.openReadChannel()
                        val write = socket.openWriteChannel(autoFlush = true)
                        try {
                            while (true) {
                                val line = read.readUTF8Line()

                                write.writeStringUtf8("$line\n")
                            }
                        } catch (e: Throwable) {
                            socket.close()
                        }
                    }
                }
            }
        }


        fun unserializeReq(JSONReqLine: String){

            val parser: Parser = Parser.default()
            val stringBuilder: StringBuilder = StringBuilder(JSONReqLine)
            val json: JsonObject = parser.parse(stringBuilder) as JsonObject

            val req_objectType = json.string("objectType")
            val req_objectRequested  = json.string("objectRequested")
            val req_idObjectRequested = json.string("idObjectRequested")

            if (req_objectType.isNullOrBlank() or req_objectType.isNullOrBlank()){
                print("error: Malformed request")
            }

            print(req_objectType)
            print(req_objectRequested)
            print(req_idObjectRequested)

            if(req_objectType == "request"){
                // C'est une requete (c'est ce qu'on veut et ce que traite ce serveur)
                // on va donc comparer l'objet requis pour savoir quoi lui renvoyer
                when (req_objectRequested) {
                    "ListeDesMatchs" -> {
                        print("request")


                    }
                    "Match" -> {
                        if(req_idObjectRequested.isNullOrBlank()){
                            print("erreur")
                        }

                    }

                    "Bet" -> {
                        if(req_idObjectRequested.isNullOrBlank()){
                            print("erreur")
                        }

                    }


                    else -> print("erreur")
                }
            }


        }


    }


}
