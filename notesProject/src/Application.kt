package com.alzhanotes

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.content.resource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.routing.routing
import org.litote.kmongo.KMongo


lateinit var  client: MongoClient

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    println("INITIALISATION DE LA BDD")

    val uri = MongoClientURI(
        "mongodb+srv://IFT604:18TJfffN3gk1awb2@ift604-9q5dz.mongodb.net/test?retryWrites=true&w=majority"
    )
    client = KMongo.createClient(uri)
    var database: MongoDatabase? = client.getDatabase("test")

    print("\n\n\n\n CONNECTED \n\n\n\n")


    routing {

        route("/api") {
            route("getNotes") {
                get() {
                    val query = call.request.queryParameters["author"]
                    //val rep =
                    //    MatchTracker.handleReq("{\"objectType\":\"request\",\"objectRequested\":\"ListeDesMatchs\",\"idObjectRequested\":\"\"}")
                    if (query != null) {
                        APIHandler.retrieveDB(query)
                    }
                }
            }

            route("addNote"){
                post(){


                }
            }


        }


        get("/testDB"){
            var note = APIHandler.retrieveDB("0")
            call.respondText(note!!.body, ContentType.Text.Plain)

        }

        static{
            resource("/", "static/index.html") // single star will only resolve the first part
            resources("static")
        }
    }
}

