package ca.udes

import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    var serv = MatchTracker.Server
    //serv.main(emptyArray())
    Match("1234",
        "eq1",
        "eq2",
        0,
        1,
        intArrayOf(0,0),
        intArrayOf(0,0),
        intArrayOf(0,0),
        intArrayOf(0,0),
        arrayOf(""))

}

