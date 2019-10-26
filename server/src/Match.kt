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



     suspend fun updateMatchInfos() {
        while(chronometreSec<10){
            chronometreSec += 1
            println(chronometreSec)
            delay(1000L)
        }
        println("fin du match")
    }
}

