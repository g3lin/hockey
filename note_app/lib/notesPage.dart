import 'package:flutter/material.dart';
import 'note.dart';

import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;

class NoteList extends StatefulWidget {
  @override
  _NoteListState createState() => _NoteListState();
}

class _NoteListState extends State<NoteList> {

  Future<List<Note>> _getNotes()async{

    var data = await http.get("http://10.0.2.2:8080/api/getNotes?author=antoine");
    //var jsonData = json.decode("[{id: 5deac88c67c323554b714cdf, author: antoine, body: ceci est un test, date: 2019-12-06, priority: 1, uniOnly: true},{id: 5deac8adda5d267ba0a9e5de, author: antoine, body: ceci est un test, date: 2019-12-06, priority: 1, uniOnly: true}]");
    var jsonData = json.decode(data.body);
    print(jsonData);
    List<Note> notes = [];
    for(var u in jsonData){
      print("hello");
      Note note = Note(u["_id"], u["author"], u["body"], u["date"],u["priority"],u["uniOnly"]);
      notes.add(note);
    }
    print(notes.length);

    return notes;
  }


  /*List<Note> notes=[
    Note(id:'5453UGUFYD65' ,body: 'hello Mr body1',author: 'soufiane',date: DateTime.now(), priority: 2,uniOnly:true ),
    Note(id:'5453UGUFYD65' ,body: 'hello Mr body2',author: 'TEST',date: DateTime.now(), priority: 1,uniOnly:false ),
    Note(id:'5453UGUFYD65' ,body: 'hello Mr body3',author: 'HELLO',date: DateTime.now(), priority:3,uniOnly:true ),
  ];*/


  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Scaffold(
      body: Container(
        child: FutureBuilder(
          future: _getNotes(),
          builder: (BuildContext context, AsyncSnapshot snapshot){
            if(snapshot.data == null ){
              return Container(
                child: Center(
                  child:Text("Loading.....") ,
                ),
              );
            }
            return ListView.builder(
              itemCount: snapshot.data.leght,
              itemBuilder:(BuildContext context, int id){
                return ListTile(
                  title: Text(snapshot.data[id].author),
                );
              } ,);
          },
        ),
      ),
    );
  }
}
