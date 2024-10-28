import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';

class WeatherRetriever {
  double latitude;
  double longitude;

  WeatherRetriever({required this.latitude, required this.longitude});
  String formattedDate =
      DateFormat("yyyy-MM-dd'T'HH:mm:ss").format(DateTime.now());
  // Retrieve weather forecast from API
  Future<String> retrieveWeather() async {
    final url = Uri.parse(
        'https://api-open.data.gov.sg/v2/real-time/api/two-hr-forecast?$formattedDate'); // Replace with actual API endpoint

    try {
      final response = await http.post(url, body: {
        'latitude': latitude.toString(),
        'longitude': longitude.toString(),
      });

      if (response.statusCode == 200) {
        final jsonResponse = jsonDecode(response.body);

        if (jsonResponse['code'] == 1) {
          // Extract forecast information from response
          final forecast =
              jsonResponse['data']['items'][0]['forecasts'][0]['forecast'];
          return forecast;
        } else {
          return 'Error: ${jsonResponse['errorMsg']}';
        }
      } else if (response.statusCode == 404) {
        return 'Forecast Not Available';
      } else {
        return 'Error: Unable to retrieve weather data';
      }
    } catch (e) {
      return 'Error: $e';
    }
  }

  void setLocation(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // Get const icon based on weather forecast
  Icon getWeatherIcon(String forecast) {
    switch (forecast) {
      case 'Fair':
      case 'Fair (Day)':
      case 'Fair (Night)':
      case 'Fair and Warm':
        return const Icon(Icons.wb_sunny, color: Colors.yellow);

      case 'Partly Cloudy':
      case 'Partly Cloudy (Day)':
      case 'Partly Cloudy (Night)':
        return const Icon(Icons.cloud, color: Colors.blueGrey);

      case 'Cloudy':
        return const Icon(Icons.cloud_queue, color: Colors.grey);

      case 'Hazy':
      case 'Slightly Hazy':
        return const Icon(Icons.filter_drama, color: Colors.orange);

      case 'Windy':
        return const Icon(Icons.air, color: Colors.blue);

      case 'Mist':
      case 'Fog':
        return const Icon(Icons.blur_on, color: Colors.grey);

      case 'Light Rain':
      case 'Moderate Rain':
      case 'Heavy Rain':
        return const Icon(Icons.grain, color: Colors.blueAccent);

      case 'Passing Showers':
      case 'Light Showers':
      case 'Showers':
      case 'Heavy Showers':
        return const Icon(Icons.grain, color: Colors.blue);

      case 'Thundery Showers':
      case 'Heavy Thundery Showers':
      case 'Heavy Thundery Showers with Gusty Winds':
        return const Icon(Icons.flash_on, color: Colors.purple);

      default:
        return const Icon(Icons.help_outline, color: Colors.grey);
    }
  }
}
