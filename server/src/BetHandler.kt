package ca.udes

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import kotlinx.coroutines.io.*


/**
 * Two mains are provided, you must first start EchoApp.Server, and then EchoApp.Client.
 * You can also start EchoApp.Server and then use a telnet client to connect to the echo server.
 */
object BetHandler {
    val selectorManager = ActorSelectorManager(Dispatchers.IO)
    val DefaultPort = 9001

    object Server {

        @JvmStatic
        suspend fun main() {
            runBlocking {
                val serverSocket = aSocket(selectorManager).tcp().bind(port = DefaultPort)
                println("BetHandler server listening at ${serverSocket.localAddress}")

                while (true) {
                    val socket = serverSocket.accept()
                    println("Accepted $socket")
                    launch {
                        val read = socket.openReadChannel()
                        val write = socket.openWriteChannel(autoFlush = true)
                        try {
                            while (true) {
                                val line = read.readUTF8Line()
                                val requestArgs = unserializeReq(line)
                                val rep = craftResponse(requestArgs[0] as String?,
                                                            requestArgs[1] as String?,
                                                            requestArgs[2] as String?,
                                                            requestArgs[3] as String?,
                                                            requestArgs[4] as Int?,
                                                            requestArgs[5] as Int?)
                                if (line != null) {
                                    write.writeStringUtf8(rep)
                                }
                            }
                        } catch (e: Throwable) {
                            print(e)
                            socket.close()
                        }
                    }
                }
            }
        }


        private fun unserializeReq(JSONReqLine: String?): Array<Any?> {

            var req_objectType: String? = null
            var req_objectRequested: String? = null
            var req_idObjectRequested: String? = null
            var req_bet_matchID: String? = null
            var req_bet_miseSur: Int? = null
            var req_bet_sommeMisee: Int? = null


            //Parse le message recu
            try {

                val parser: Parser = Parser.default()
                val stringBuilder: StringBuilder = StringBuilder(JSONReqLine)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                req_objectType = json.string("objectType")
                req_objectRequested = json.string("objectRequested")
                req_idObjectRequested = json.string("idObjectRequested")

                var betU = json.obj("Bet")
                print(betU.toString())
                if (betU != null){
                    req_bet_matchID = betU.string("matchID")
                    req_bet_miseSur = betU.int("miseSur")
                    req_bet_sommeMisee = betU.int("sommeMisee")
                }

                if (req_objectType.isNullOrBlank() or req_objectType.isNullOrBlank()) {
                    betError("error: Malformed request")
                }
            } catch (e: Exception) {
                betError(e.toString())
            }

            println(req_objectType)
            println(req_objectRequested)
            println(req_idObjectRequested)
            println(req_bet_matchID)
            println(req_bet_miseSur)
            println(req_bet_sommeMisee)
            return arrayOf(req_objectType, req_objectRequested, req_idObjectRequested,req_bet_matchID,req_bet_miseSur,req_bet_sommeMisee)
        }


        private fun craftResponse(
            req_objectType: String?,
            req_objectRequested: String?,
            req_idObjectRequested: String?,
            req_bet_matchID: String? ,
            req_bet_miseSur: Int? ,
            req_bet_sommeMisee: Int? 
        ): String {
            var rep = ""
            if ((req_objectType == "request") and (req_objectRequested == "betStatus")) {
                // C'est une requete betUpdate(c'est ce qu'on veut et ce que traite ce serveur)
                rep = handleBetRequest(req_idObjectRequested)

            }

            else if (req_objectType == "betUpdate"){
                rep = handleBetCreation(req_bet_matchID, req_bet_miseSur, req_bet_sommeMisee)


            }

            else betError("Message malformé \n Doit etre un pari ou une requete sur un pari sur ce serveur")

            return rep
        }

        private fun handleBetCreation(reqBetMatchid: String?, reqBetMisesur: Int?, reqBetSommemisee: Int?): String {
            var rep : String  = "{\"objectType\":\"response\"," +
                    "\"status\":-1," +
                    "}"

            if (reqBetMatchid.isNullOrBlank() or (reqBetMisesur==null) or (reqBetSommemisee == null)) betError("Mauvaise requete")
            else{
                //SÉMAPHORE SUR LES PARIS
                objetParis.semaBets.acquire()

                val nbreParis = objetParis.ListeDesParis.size


                var matchTrouve:Match? = null
                for(match in objetMatchs.ListeDesMatch){
                    // On a trouvé le match avec l'ID correspondant
                    if(match.matchID == reqBetMatchid) matchTrouve = match
                }
                if (matchTrouve != null ){
                    if (matchTrouve.PeriodeEnCours < 3) {
                        val betID = matchTrouve.matchID + "_" + nbreParis
                        val pariCree = Paris(matchTrouve, reqBetMisesur!!, betID, 0, reqBetSommemisee!!, 0.0)
                        objetParis.ListeDesParis += pariCree
                        rep = "{\"objectType\":\"response\"," +
                                "\"status\":0," +
                                "\"betID\":\"" + betID + "\"" +
                                "}"

                    }
                }
                // FIN SEMAPHORE PARIS ICI
                objetParis.semaBets.release()
            }

            return rep
        }




        private fun handleBetRequest(req_idObjectRequested: String?):String {
            objetParis.semaBets.acquire()
            var rep = ""

            var PariTrouve : Paris? = null
            for(bet in objetParis.ListeDesParis){
                // On a trouvé le match avec l'ID correspondant
                if(bet.betID == req_idObjectRequested) PariTrouve = bet
            }
            if (PariTrouve != null){
                rep = "{\"objectType\":\"response\",\"Bet\":{"
                rep += "\"matchID\":\"${PariTrouve.Match.matchID}\","
                rep += "\"miseSur\":\"${PariTrouve.miseSur}\","
                rep += "\"sommeMisee\":\"${PariTrouve.sommeMisee}\","
                rep += "\"Status\":\"${PariTrouve.Status}\","
                rep += "\"sommeGagnee\":\"${PariTrouve.sommeGagnee}\","
                rep += "\"betID\":\"${PariTrouve.betID}\""
                rep += "}}"
            }
            else betError("Pari non trouvé")

            objetParis.semaBets.release()
            return rep
        }


        private fun betError(msg:String){
            print(msg)
        }

    }


}
