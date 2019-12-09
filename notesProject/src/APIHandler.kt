package com.alzhanotes

import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.litote.kmongo.* //NEEDED! import KMongo extensions

object APIHandler {
    fun retrieveDB(author:String):Note{
        //val uri = MongoClientURI("mongodb://IFT604:18TJfffN3gk1awb2@ift604-9q5dz.mongodb.net")

        val database = client.getDatabase("IFT604") //normal java driver usage
        val col = database.getCollection<Note>("notes")

        col.insertOne(Note(newId(),"antoine",
             "ceci est un test",true,1,"2019-12-06"))

        val note : Note? = col.findOne(Note::author eq author)
        return note!!
    }
}