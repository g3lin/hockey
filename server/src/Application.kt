package ca.udes

import io.ktor.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

lateinit var  ObjetMatchs : ListeDesMatch

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    runBlocking {
        initMatchs()

        var serv = MatchTracker.Server
        serv.ObjetMatchs = ObjetMatchs

        for (match in ObjetMatchs.ListeDesMatch) {

            launch { match.updateChrono() }
            launch { match.updateScores() }
        }



        launch{ serv.main() }
    }
}

fun initMatchs(){
    //Initialiser l'objet principal des matchs
    ObjetMatchs = ListeDesMatch(arrayOf<Match>())

    //Initialisons maintenant les matchs en cours
    val m1 = Match(
        "1",
        "Toronto Maple Leafs",
        "Canadiens de Montréal",
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
        "Red Wings de Détroit",
        3300,
        3,
        intArrayOf(1, 3),
        intArrayOf(5, 0),
        intArrayOf(1, 3),
        arrayOf("Carton Rouge")
    )

    ObjetMatchs.ListeDesMatch = arrayOf<Match>(m1,m2,m3)


}

