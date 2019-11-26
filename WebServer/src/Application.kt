package com.hockeynite

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {

        //Si on est dans un chemin d'api, on doit pouvoir y répondre
        route("/api"){
            get() {
                call.respondText("It's API time", contentType = ContentType.Text.Plain)
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

