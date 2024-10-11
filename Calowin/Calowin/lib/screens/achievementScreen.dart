import 'package:flutter/material.dart';

class AchievementScreen extends StatelessWidget {
  final double distance;
  final String destination;
  final String currentLocation;
  final String travelMethod;
  final int caloriesBurnt;
  final int carbonSaved;

  // Constructor to accept trip details and metrics
  AchievementScreen({
    required this.distance,
    required this.destination,
    required this.currentLocation,
    required this.travelMethod,
    required this.caloriesBurnt,
    required this.carbonSaved,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Achievement'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              Text(
                'Congratulations!',
                style: TextStyle(
                  fontSize: 32,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 20),
              Text(
                'You\'ve completed a ${distance.toStringAsFixed(2)} km trip from $currentLocation to $destination by $travelMethod.',
                style: TextStyle(fontSize: 18),
                textAlign: TextAlign.center,
              ),
              SizedBox(height: 30),
              Text(
                'Trip Statistics:',
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 20),
              Text(
                'Calories Burned: $caloriesBurnt kcal',
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(height: 10),
              Text(
                'Carbon Saved: $carbonSaved kg',
                style: TextStyle(fontSize: 18),
              ),
              SizedBox(height: 40),
              ElevatedButton(
                onPressed: () {
                  Navigator.popUntil(context, ModalRoute.withName('/')); // Go back to home screen
                },
                child: Text('Back to Home'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
