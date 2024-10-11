// lib/main.dart

import 'package:flutter/material.dart';
import 'screens/homescreen.dart';  // Import the HomeScreen

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Calowin',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: HomeScreen(),  // Set HomeScreen as the starting page
    );
  }
}
