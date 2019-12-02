package com.hockeynite

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import kotlinx.coroutines.launch

lateinit var  objetMatchs : ListeDesMatch
lateinit var objetParis :ListeDesParis

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    //INITIALISATION DU SERVEUR
    initMatchs()
    print("Matchs initialisés\n\n")

    for (match in objetMatchs.ListeDesMatch) {

        launch { match.updateChrono() }
        launch { match.updateScores() }
    }

    //LOGIQUE DU ROUTAGE
    routing {

        //Si on est dans un chemin d'api, on doit pouvoir y répondre
        route("/api"){
            route("match"){
                get() {
                    val query = call.request.queryParameters["query"]
                    //val rep =
                    //    MatchTracker.handleReq("{\"objectType\":\"request\",\"objectRequested\":\"ListeDesMatchs\",\"idObjectRequested\":\"\"}")
                    if (query != null) {
                        val rep = MatchTracker.handleReq(query)
                        call.respondText(rep, contentType = ContentType.Text.Plain)
                    }
                }
            }
            route("bet"){
                post() {
                    val query = call.request.queryParameters["query"]
                    if (query != null) {
                        val rep = BetHandler.handleReq(query)
                        call.respondText(rep, contentType = ContentType.Text.Plain)
                    }
                }
            }

        }

        route("/put"){
                put(){
                    call.respondText("Appel PUT recu");
                }
        }

        route("/delete"){
            delete(){
                call.respondText("Appel DELETE recu");
            }
        }

        //Sinon on sert les pages statiques du site
        route("/") {

            // Traitement particulier pour la page d'accueil qui doit retourner le index.html
             // Sinon on va directement chercher dans le dossier resources
            static{
                resource("/", "static/index.html") // single star will only resolve the first part
                resources("static")
            }
        }



    }
}



fun initMatchs(){
    //Initialiser l'objet principal des matchs
    objetMatchs = ListeDesMatch(arrayOf<Match>())

    objetParis = ListeDesParis(arrayOf<Paris>())
    //Initialisons maintenant les matchs en cours
    val m1 = Match(
        "1",
        "Toronto Maple Leafs",
        "Canadiens de Montreal",
        0,
        1,
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        intArrayOf(0, 0),
        arrayOf("")
    )

    val m2 = Match(
        "2",
        "New York Rangers",
        "Jets de Winnipeg",
        2000,
        2,
        intArrayOf(1, 3),
        intArrayOf(2, 0),
        intArrayOf(0, 0),
        arrayOf("Carton Rouge","Carton Jaune")
    )

    val m3 = Match(
        "3",
        "Sherbrooke Phoenix",
        "Red Wings de Detroit",
        3300,
        3,
        intArrayOf(1, 3),
        intArrayOf(5, 0),
        intArrayOf(1, 3),
        arrayOf("Carton Rouge")
    )

    objetMatchs.ListeDesMatch = arrayOf<Match>(m1,m2,m3)


}


