import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/colors_and_fonts.dart';

class MapcalcPage extends StatefulWidget {
  const MapcalcPage({super.key});

  @override
  State<MapcalcPage> createState() => _MapcalcPageState();
}

class _MapcalcPageState extends State<MapcalcPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Center(
            child: Container(
              height: 200,
              width: 200,
              color: Colors.green,
            ),
          )
        ],
      ),
    );
  }
}
