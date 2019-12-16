import 'package:flutter/material.dart';
import 'package:note_app/name.dart';
import 'notesPage.dart';
import 'package:geolocator/geolocator.dart';

String Name ;
class FirstPage extends StatelessWidget{
  final TextEditingController texteditcontrol = new TextEditingController();
  @override
  Widget build(BuildContext context) {

    void _getCurrentLocation() async{

      final position = await Geolocator().getCurrentPosition(desiredAccuracy: LocationAccuracy.best);
      var uniLat = 45.379059;
      var uniLong = -71.927256;
      final distance = await Geolocator().distanceBetween(position.latitude, position.longitude, uniLat, uniLong);
      print("your current position : "+ position.toString() +" the distance between you and the uni : " );
      print(distance);
      if(distance < 1)
        {
          inUni = true;
        }
      else{
        inUni  = false;
      }
      print(inUni);
    }
    return Scaffold(
      appBar: AppBar(
        title: Text('Alzhei Notes'),
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
              onChanged: _submission,
            ),
            RaisedButton(
              child: Text('Get my notes'),
              onPressed: () {
                _getCurrentLocation();
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