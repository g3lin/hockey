package com.alzhanotes

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.util.*

data class Note (@BsonId val _id: Id<Note> = newId(),
                 val author:String,
                 val body:String,
                 val uniOnly:Boolean,
                 val priority: Int,
                 val date: String) {
}