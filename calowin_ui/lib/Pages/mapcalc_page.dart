import 'package:flutter/material.dart';

class MapcalcPage extends StatefulWidget {
  const MapcalcPage({super.key});

  @override
  State<MapcalcPage> createState() => _MapcalcPageState();
}

class _MapcalcPageState extends State<MapcalcPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Map Page")),
    );
  }
}
