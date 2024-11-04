import 'dart:convert';
import 'package:http/http.dart' as http;

class NotificationService {
<<<<<<< Updated upstream
  final String baseUrl = "http://localhost:8081"; // Update with actual base URL
=======
  final String baseUrl = "http://localhost:8084"; // Update with actual base URL
>>>>>>> Stashed changes

  Future<List<String>> fetchFriendRequests(String userId) async {
    final response = await http.get(Uri.parse('$baseUrl/notifications/friend-requests/$userId'));

    if (response.statusCode == 200) {
      // Parse the JSON response
      List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item.toString()).toList();
    } else {
      throw Exception("Failed to load notifications");
    }
  }
}
