import 'dart:convert';
import 'package:http/http.dart' as http;

class NotificationService {
  final String baseUrl = "http://localhost:8080"; // Update with actual base URL

  Future<List<String>> fetchFriendRequests(String userId) async {
    final response = await http.get(Uri.parse('$baseUrl/api/user/$userId/friend-requests'));

    if (response.statusCode == 200) {
      // Parse the JSON response
      List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item.toString()).toList();
    } else {
      throw Exception("Failed to load notifications");
    }
  }
}
