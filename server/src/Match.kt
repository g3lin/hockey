package ca.udes

import kotlinx.coroutines.*
import java.util.Random
import kotlin.math.floor

// 3 tiers temps de 20 mn
// p1 s'arrete à 1200s
// p2 s'arrete à 2400s
// p3 et match s'arrete à 3600s

class Match(val matchID:String,
            val nomEquipe1:String,
            val nomEquipe2:String,
            var chronometreSec:Int,
            var PeriodeEnCours:Int,
            var scoreP1 : IntArray,
            var scoreP2 : IntArray,
            var scoreP3 : IntArray,
            var penalites : Array<String>) {



     suspend fun updateChrono() {
        while(chronometreSec < 3600){
            // MATCH EN COURS
            chronometreSec += 1

            if (chronometreSec == 1200){
                PeriodeEnCours = 2
            }
            if (chronometreSec == 2400){
                PeriodeEnCours = 3
            }

            //println(chronometreSec)
            delay(1000L)
        }

         println("fin du match $nomEquipe1 VS $nomEquipe2 ID=$matchID")
         gestionEndGame()
    }



    suspend fun updateScores(){
        val random = Random()
        val PROB_BUT = 95
        while (chronometreSec < 3600) {
            var proba = random.nextInt(100)
            if (proba > PROB_BUT) {
                when (PeriodeEnCours) {
                    1 -> scoreP1[0] += 1
                    2 -> scoreP2[0] += 1
                    3 -> scoreP3[0] += 1
                }
                println("But pour l'equipe $nomEquipe1 dans le match $matchID")
            }


            if (proba < 100 - PROB_BUT) {
                when (PeriodeEnCours) {
                    1 -> scoreP1[1] += 1
                    2 -> scoreP2[1] += 1
                       3 -> scoreP3[1] += 1
                }
                println("But pour l'equipe $nomEquipe2 dans le match $matchID")
            }

            if (proba == 99){
                penalites += "Carton Jaune"
                println("Pénalité dans le match $matchID")
            }

            if (proba == 100){
                penalites += "Carton rouge"
                println("Pénalité dans le match $matchID")
            }
            delay(10000L)
        }

    }

    fun gestionEndGame(){
        //LIST ALL BETS ON THIS MATCH
        objetParis.semaBets.acquire()
        var ParisMatch:Array<Paris> = arrayOf<Paris>()
        for (paris in objetParis.ListeDesParis){
            paris.Status = 1
            if(paris.Match == this){
                ParisMatch += paris
            }
        }


        //CALCULATE SCORES
        var scoreFinal = arrayOf<Int>(0,0)
        scoreFinal[0] = scoreP1[0]+scoreP2[0]+scoreP3[0]
        scoreFinal[1] = scoreP1[1]+scoreP2[1]+scoreP3[1]

        var winner:Int = 4
        if(scoreFinal[0] > scoreFinal[1]) winner = 0
        if(scoreFinal[0] < scoreFinal[1]) winner = 1
        if(scoreFinal[0] == scoreFinal[1]) winner = 2

        println("Le gagnant est l'équipe ${winner}")

        // GET ALL THE MONEY PUT ON THE TABLE
        var sommeTotale = 0
        var sommeARepartir:Double
        for (paris in ParisMatch){
            sommeTotale += paris.sommeMisee
        }
        sommeARepartir = 0.75*sommeTotale

        // GET ALL WINNING BETS
        var sommeParieeParGagnants:Int = 0
        var ParisMatchGagnants:Array<Paris> = arrayOf<Paris>()
        for (paris in ParisMatch){
            if(paris.miseSur == winner){
                sommeParieeParGagnants += paris.sommeMisee
                ParisMatchGagnants += paris
            }
        }

        // AWARD WINNERS
        for(paris in ParisMatchGagnants){
            val sommeGagnee = sommeARepartir*(paris.sommeMisee/sommeParieeParGagnants)
            paris.sommeGagnee = floor(sommeGagnee*100) /100 //Arrondit au centime inférieur

        }

        objetParis.semaBets.release()

        //

    }


}

