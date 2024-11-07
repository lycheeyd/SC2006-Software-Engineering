import 'dart:convert';
import 'package:http/http.dart' as http;

class NotificationService {
  final String baseUrl = "http://172.21.146.188:8084"; // Update with actual base URL

  Future<List<String>> fetchFriendRequests(String userId) async {
    final response = await http.get(Uri.parse('$baseUrl/notifications/friend-requests/$userId'));

    if (response.statusCode == 200) {
      // Parse the JSON response
      List<dynamic> data = jsonDecode(response.body);
      final newdata = data.map((item) => item.toString()).toList();
      print(newdata);
      return newdata;
    } else {
      throw Exception("Failed to load notifications");
    }
  }
}
