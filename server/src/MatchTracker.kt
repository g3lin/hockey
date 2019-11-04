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
        suspend fun main() {
            runBlocking {
                val serverSocket = aSocket(selectorManager).tcp().bind(port = DefaultPort)
                println("MatchTracker server listening at ${serverSocket.localAddress}")

                while (true) {
                    val socket = serverSocket.accept()
                    println("Accepted $socket")
                    launch {
                        val read = socket.openReadChannel()
                        val write = socket.openWriteChannel(autoFlush = true)
                        try {
                            while (true) {

                                val line = read.readUTF8Line()
                                val requestArgs= unserializeReq(line)
                                val rep = craftResponse(requestArgs[0],requestArgs[1],requestArgs[2])
                                if (line != null) {
                                    write.writeStringUtf8(rep+"\n")
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


        private fun unserializeReq(JSONReqLine: String?):Array<String?> {

            var req_objectType:String? = null
            var req_objectRequested:String? = null
            var req_idObjectRequested:String? = null

            //Parse le message recu
            try {

                val parser: Parser = Parser.default()
                val stringBuilder: StringBuilder = StringBuilder(JSONReqLine)
                val json: JsonObject = parser.parse(stringBuilder) as JsonObject

                req_objectType = json.string("objectType")
                req_objectRequested = json.string("objectRequested")
                req_idObjectRequested = json.string("idObjectRequested")

                if (req_objectType.isNullOrBlank() or req_objectType.isNullOrBlank()) {
                    trackerError("error: Malformed request")
                }
            }
            catch (e:Exception ){
                trackerError(e.toString())
            }

            println(req_objectType)
            println(req_objectRequested)
            println(req_idObjectRequested)
            return arrayOf(req_objectType,req_objectRequested,req_idObjectRequested)
        }


        private fun craftResponse(req_objectType:String?,req_objectRequested:String?,req_idObjectRequested:String?):String{
            var rep = ""
            if(req_objectType == "request"){
                // C'est une requete (c'est ce qu'on veut et ce que traite ce serveur)
                // on va donc comparer l'objet requis pour savoir quoi lui renvoyer
                when (req_objectRequested) {
                    "ListeDesMatchs" ->  rep = handleListeDesMatchs()


                    "Match" -> {
                        if(req_idObjectRequested.isNullOrBlank()){
                            trackerError("erreur, pas d'objet de matchs spécifié")
                        }
                        else{
                            rep = handleMatch(req_idObjectRequested)
                        }

                    }

                    "Bet" -> trackerError("erreur, ce serveur ne prend pas en charge les paris")
                    else -> trackerError("erreur: message objet malformé")
                }
            }
            else trackerError("Message malformé \n Doit etre une requete sur ce serveur")

            return rep
        }







        private fun handleListeDesMatchs():String{
            var rep = "{\"objectType\":\"response\",\"matchIDs\":["
            for(match in objetMatchs.ListeDesMatch){
                rep += "\"${match.matchID}\","
            }
            //Enleve la derniere virgule
            rep = rep.dropLast(1)
            rep += "]}"
            println(rep)
            return rep
        }


        private fun handleMatch(idMatchReq:String):String{
            var rep = ""
            var matchTrouve : Match? = null
            for(match in objetMatchs.ListeDesMatch){
                // On a trouvé le match avec l'ID correspondant
                if(match.matchID == idMatchReq) matchTrouve = match
            }
            if (matchTrouve != null){
                rep = "{\"objectType\":\"response\",\"match\":{"
                rep += "\"matchID\":\"${matchTrouve.matchID}\","
                rep += "\"nomEquipe1\":\"${matchTrouve.nomEquipe1}\","
                rep += "\"nomEquipe2\":\"${matchTrouve.nomEquipe2}\","
                rep += "\"chronometreSec\":\"${matchTrouve.chronometreSec}\","
                rep += "\"PeriodeEnCours\":\"${matchTrouve.PeriodeEnCours}\","
                rep += "\"scoreP1\":\"${matchTrouve.scoreP1.joinToString()}\","
                rep += "\"scoreP2\":\"${matchTrouve.scoreP2.joinToString()}\","
                rep += "\"scoreP3\":\"${matchTrouve.scoreP3.joinToString()}\","
                rep += "\"penalites\":\"${matchTrouve.penalites.joinToString()}\""
                rep += "}}"
            }

            else trackerError("Match non trouvé")
            //print(rep)
            return rep
        }

        private fun trackerError(msg:String){
            print(msg)
        }

    }


}
