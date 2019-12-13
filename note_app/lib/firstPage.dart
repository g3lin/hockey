import 'package:flutter/material.dart';
import 'package:note_app/name.dart';
import 'notesPage.dart';
String Name ;
class FirstPage extends StatelessWidget{
  final TextEditingController texteditcontrol = new TextEditingController();
  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text('My super Notessss'),
        centerTitle: true,
        backgroundColor: Colors.greenAccent,
      ),
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.stretch,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Text(
              'Welcome to Notes',
              style: TextStyle(fontSize: 30),

            ),

            TextField(
              decoration: new InputDecoration(
                labelText: "Enter your Name",
                fillColor: Colors.grey,
                border: new OutlineInputBorder(
                  borderRadius: new BorderRadius.circular(35.0),
                  borderSide: new BorderSide(
                  ),
                ),
                //fillColor: Colors.green
              ),

              keyboardType: TextInputType.text,
              style: new TextStyle(
                fontFamily: "Poppins",
              ),
              controller: texteditcontrol,
              onSubmitted: _submission,
            ),
            RaisedButton(
              child: Text('Get my notes'),
              onPressed: () {
                // Pushing a route directly, WITHOUT using a named route
                Navigator.of(context).push(
                  // With MaterialPageRoute, you can pass data between pages,
                  // but if you have a more complex app, you will quickly get lost.
                  MaterialPageRoute(
                    builder: (context) =>
                        NoteList(name:texteditcontrol.text) ,
                  ),
                );
              },
            )
          ],
        ),
      ),
    );
  }

  void _submission(String value) {
    Name = texteditcontrol.text;
    name = Name;
    print(name);
  }
}