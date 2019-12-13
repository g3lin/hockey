

import 'package:flutter/material.dart';
import 'note.dart';

import 'dart:async';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'name.dart';
import 'popup.dart';
import 'pooup_content.dart';

var test;

class NoteList extends StatefulWidget {
  final String name;
  const NoteList({Key key, this.name}) : super(key: key);
  @override
  _NoteListState createState() => _NoteListState();

}


class _NoteListState extends State<NoteList> {
  final TextEditingController body = new TextEditingController();
  DateTime _dateTime = null;
  Future<List<Note>> _getNotes()async{

    var data = await http.get("http://10.0.2.2:8080/api/getNotes?author="+name);
    //var jsonData = json.decode("[{id: 5deac88c67c323554b714cdf, author: antoine, body: ceci est un test, date: 2019-12-06, priority: 1, uniOnly: true},{id: 5deac8adda5d267ba0a9e5de, author: antoine, body: ceci est un test, date: 2019-12-06, priority: 1, uniOnly: true}]");
    var jsonData = json.decode(data.body);
    print(jsonData);
    List<Note> notes = [];
    for(var u in jsonData){
      print("hello");
      Note note = Note( u["author"], u["body"], u["date"],u["priority"],u["uniOnly"]);
      notes.add(note);
    }
    print(notes.length);

    return notes;
  }
  Future _postNote()async{
    print("hello " + content);
    priority = 0;
    unionly = false;
    date = _dateTime;
    var link = await http.post("http://10.0.2.2:8080/api/addNote?author="+name+"&body="+content+"&uniOnly="+unionly.toString()+"&priority="+ priority.toString()+"&date="+date.toString());
    print("status"+link.statusCode.toString());
    return link;
  }
  Widget _popupBody() {
    return  Scaffold(
        body: Container(
            padding:
            const EdgeInsets.symmetric(vertical: 16.0, horizontal: 16.0),
            child: Builder(
                builder: (context) => Form(
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.stretch,
                        children: [
                          TextFormField(
                            decoration:
                            InputDecoration(labelText: 'Note info'),
                            controller: body,
                            onChanged: _submissionbody,
                          ),
                          RaisedButton(
                            child: Text('Pick your due date'),
                            onPressed: (){
                              showDatePicker(context: context, initialDate: DateTime.now(), firstDate: DateTime(2010), lastDate: DateTime(2050)).then((date){
                                setState(() {
                                  _dateTime = date;
                                });
                              });
                            },
                          ),
                            RaisedButton(
                              child: Text('submit'),
                              onPressed:(){
                                _postNote();
                                try {
                                  Navigator.pop(context); //close the popup
                                } catch (e) {}
                              } ,

                            ),
                        ])
                )
            )
        )
    );
  }
  showPopup(BuildContext context, Widget widget, String title,
      {BuildContext popupContext}) {
    Navigator.push(
      context,
      PopupLayout(
        top: 30,
        left: 30,
        right: 30,
        bottom: 50,
        child: PopupContent(
          content: Scaffold(
            appBar: AppBar(
              title: Text(title),
              leading: new Builder(builder: (context) {
                return IconButton(
                  icon: Icon(Icons.arrow_back),
                  onPressed: () {
                    try {
                      Navigator.pop(context); //close the popup
                    } catch (e) {}
                  },
                );
              }),
              brightness: Brightness.light,
            ),
            resizeToAvoidBottomPadding: false,
            body: widget,
          ),
        ),
      ),
    );
  }


  @override
  Widget build(BuildContext context) {
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
              itemCount: snapshot.data.length,
              itemBuilder:(BuildContext context, int id){
                return Card(
                  margin: EdgeInsets.fromLTRB(16.0, 0, 16.0, 0),
                 child:Padding(
                   padding: const EdgeInsets.all(12.0),
                   child: Column(
                    children: <Widget>[
                      Text(
                        snapshot.data[id].body,
                        style: TextStyle(
                          fontSize: 18.0,
                          color: Colors.grey[600],
                        ),
                      ),
                      SizedBox(height: 6.0),
                      Text(
                        snapshot.data[id].date,
                        style: TextStyle(
                          fontSize: 14.0,
                          color: Colors.grey,
                        ),
                      )
                    ],
                  ),
                )
                );
              },
            );
          },
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          showPopup(context, _popupBody(), 'Add note');
        },
        tooltip: 'Open Popup',
        child: Icon(Icons.add),
      ),
    );
  }
  void _submissionbody(String value) {
    content = body.text;
    print(content);
  }
}
