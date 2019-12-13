import 'dart:async';
import 'dart:collection';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;

class Note{
  final String author;
  final String body;
  final String date;
  final int priority;
  final bool uniOnly;
  //Note({ this.id, this.author, this.body, this.date, this.priority, this.uniOnly});
  Note( this.author, this.body, this.date, this.priority, this.uniOnly);

}

