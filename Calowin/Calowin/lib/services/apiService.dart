// apiservice.dart
import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/location.dart';
import '../models/travelmethod.dart';
import '../models/currentlocation.dart';

class ApiService {
  final String baseUrl = "http://yourlocal-ip:8080"; // use your local ip

  ApiService();

  Future<List<Location>> fetchAvailableLocations() async {
    final response = await http.get(Uri.parse('$baseUrl/api/trips/locations'));
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return data.map((loc) => Location(loc['name'], loc['latitude'], loc['longitude'])).toList();
    } else {
      throw Exception('Failed to load locations');
    }
  }

  Future<CurrentLocation> fetchCurrentLocation() async {
    final response = await http.get(Uri.parse('$baseUrl/api/trips/current-location'));
    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      return CurrentLocation(
        name: data['name'],
        latitude: data['latitude'],
        longitude: data['longitude'],
      );
    } else {
      throw Exception('Failed to load current location');
    }
  }

  Future<List<String>> fetchTravelMethods() async {
    final response = await http.get(Uri.parse('$baseUrl/api/trips/methods'));
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      return List<String>.from(data);
    } else {
      throw Exception('Failed to load travel methods');
    }
  }

  Future<Map<String, dynamic>> startTrip(Location destination, String method) async {
    final response = await http.post(
      Uri.parse('$baseUrl/api/trips/start'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode({
        'destination': {
          'name': destination.name,
          'latitude': destination.latitude,
          'longitude': destination.longitude,
        },
        'travelMethod': method,
      }),
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to start trip');
    }
  }
}
