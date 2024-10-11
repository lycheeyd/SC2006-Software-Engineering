import 'package:flutter/material.dart';
import '../models/location.dart';
import '../models/currentlocation.dart'; // Import the CurrentLocation model
import '../services/apiService.dart';
import '../models/travelmethod.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final ApiService apiService = ApiService('http://your-backend-url');
  List<Location> locations = [];
  List<String> travelMethods = []; // Store fetched travel methods
  Location? selectedLocation;
  TravelMethod? selectedMethod;
  CurrentLocation? userCurrentLocation;
  String? resultMessage;

  @override
  void initState() {
    super.initState();
    _fetchLocations();
    _fetchCurrentLocation();
    _fetchTravelMethods(); // Fetch travel methods on init
  }

  Future<void> _fetchLocations() async {
    try {
      locations = await apiService.fetchAvailableLocations();
      setState(() {}); // Refresh UI after fetching locations
    } catch (e) {
      print('Error fetching locations: $e');
    }
  }

  Future<void> _fetchCurrentLocation() async {
    try {
      userCurrentLocation = await apiService.fetchCurrentLocation();
      setState(() {}); // Refresh UI after fetching current location
    } catch (e) {
      print('Error fetching current location: $e');
    }
  }

  Future<void> _fetchTravelMethods() async {
    try {
      travelMethods = await apiService.fetchTravelMethods();
      setState(() {}); // Refresh UI after fetching travel methods
    } catch (e) {
      print('Error fetching travel methods: $e');
    }
  }

 Future<void> _startTrip() async {
  if (selectedLocation != null && selectedMethod != null) {
    try {
      final metrics = await apiService.startTrip(selectedLocation!, selectedMethod!);
      print('Metrics received: $metrics');

      setState(() {
        resultMessage = 'Calories burned: ${metrics['caloriesBurnt']}, Carbon saved: ${metrics['carbonSaved']} kg';
      });
    } catch (e) {
      print('Error starting trip: $e');
    }
  } else {
    print('Please select a location and travel method.');
  }
}

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Travel Planner'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            if (userCurrentLocation != null) // Ensure userCurrentLocation is not null
              Text(
                'Your current location: ${userCurrentLocation!.name} (${userCurrentLocation!.latitude}, ${userCurrentLocation!.longitude})',
                style: TextStyle(fontSize: 16),
              ),
            SizedBox(height: 20),
            DropdownButton<Location>(
              hint: Text('Select a destination'),
              value: selectedLocation,
              onChanged: (Location? newValue) {
                setState(() {
                  selectedLocation = newValue;
                });
              },
              items: locations.map((Location loc) {
                return DropdownMenuItem<Location>(
                  value: loc,
                  child: Text(loc.name),
                );
              }).toList(),
            ),
            SizedBox(height: 20),
            DropdownButton<TravelMethod>(
              hint: Text('Select travel method'),
              value: selectedMethod,
              onChanged: (TravelMethod? newValue) {
                setState(() {
                  selectedMethod = newValue;
                });
              },
              items: TravelMethod.values.map((TravelMethod method) {
                return DropdownMenuItem<TravelMethod>(
                  value: method,
                  child: Text(method.toString().split('.').last),
                );
              }).toList(),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _startTrip,
              child: Text('Start Trip'),
            ),
            if (resultMessage != null)
              Padding(
                padding: const EdgeInsets.only(top: 20),
                child: Text(resultMessage!, style: TextStyle(fontSize: 18)),
              ),
          ],
        ),
      ),
    );
  }
}