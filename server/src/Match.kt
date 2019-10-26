package ca.udes

import kotlinx.coroutines.*

class Match(val matchID:String,
            val nomEquipe1:String,
            val nomEquipe2:String,
            var chronometreSec:Int,
            var PeriodeEnCours:Int,
            var scoreP1 : IntArray,
            var scoreP2 : IntArray,
            var scoreP3 : IntArray,
            var scoreP4 : IntArray,
            var penalites : Array<String>) {

    fun launch() {
        // ceci m'a fait ragequit
        // Enjoy ce truc tout nul qui marche pas
        // Marre.

        launch{updateMatchInfos()}
        print("match lancé !")

    }

     suspend fun updateMatchInfos() {

        runBlocking {
            while(chronometreSec<10){
                chronometreSec += 1
                println(chronometreSec)
                delay(1000L)
            }
            println("fin du match")
        }
    }
}

