package com.alzhanotes

import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import org.litote.kmongo.* //NEEDED! import KMongo extensions

object APIHandler {
    fun retrieveDBbyAuthor(author:String):List<Note>?{
        val database = client.getDatabase("IFT604") //normal java driver usage
        val col = database.getCollection<Note>("notes")

        val note : List<Note>? = col.find(Note::author eq author).into(ArrayList<Note>())
        return note
    }

    fun addNote(author:String, body:String, uniOnly:Boolean, priority:Int, date:String){
        val database = client.getDatabase("IFT604") //normal java driver usage
        val col = database.getCollection<Note>("notes")
        col.insertOne(Note(newId(),author,body,uniOnly,priority, date))

    }
}