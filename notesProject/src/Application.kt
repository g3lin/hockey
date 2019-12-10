package com.alzhanotes

import com.beust.klaxon.Klaxon
import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
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

    print("\n\n\n\n CONNECTED \n\n\n\n")


    routing {

        route("/api") {
            route("getNotes") {
                /*
                THIS ROUTE IS FOR DISPATCHING THE NOTES FOR A SPECIFIC USER
                 */
                get() {
                    val query = call.request.queryParameters["author"]
                    var rep:String? = ""
                    //    MatchTracker.handleReq("{\"objectType\":\"request\",\"objectRequested\":\"ListeDesMatchs\",\"idObjectRequested\":\"\"}")
                    if (query != null) {
                        val repAPI = APIHandler.retrieveDBbyAuthor(query)
                        if (repAPI != null) rep = Klaxon().toJsonString(repAPI)
                        else rep = "{}"
                    }
                    else rep = "Error, no parameters"

                    call.respondText(rep, ContentType.Text.Plain)
                }
            }

            route("addNote"){
                post(){
                    var rep = ""
                    val author = call.request.queryParameters["author"]
                    val body = call.request.queryParameters["body"]
                    val uniOnly = call.request.queryParameters["uniOnly"]
                    val priority = call.request.queryParameters["priority"]
                    val date = call.request.queryParameters["date"]
                    if (author != null && body != null && uniOnly != null && priority != null && date != null) {
                        val uniOnlyB = uniOnly.toBoolean()
                        val priorityI = priority.toInt()
                        if (uniOnlyB != null && priorityI != null) {
                            APIHandler.addNote(author, body, uniOnlyB, priorityI, date)
                            rep = "Success"
                        }
                        else rep = "Error type mismatch in parameters"
                    }
                    else rep = "Error missing parameters"

                    call.respondText(rep, ContentType.Text.Plain)

                }
            }


        }




        static{
            resource("/", "static/index.html") // single star will only resolve the first part
            resources("static")
        }
    }
}

