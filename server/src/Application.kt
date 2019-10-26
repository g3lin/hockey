package ca.udes

import io.ktor.application.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    runBlocking {
        var serv = MatchTracker.Server


        var m = Match(
            "1234",
            "eq1",
            "eq2",
            0,
            1,
            intArrayOf(0, 0),
            intArrayOf(0, 0),
            intArrayOf(0, 0),
            intArrayOf(0, 0),
            arrayOf("")
        )
        launch { m.updateMatchInfos() }
        launch{ serv.main(emptyArray()) }
    }
}

