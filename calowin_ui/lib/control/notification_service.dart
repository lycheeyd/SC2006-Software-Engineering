import 'dart:convert';
import 'package:http/http.dart' as http;

class NotificationService {
  final String baseUrl = 'http://localhost:8080'; // Replace with your backend URL

  Future<List<String>> fetchFriendRequests(String userId) async {
    final url = Uri.parse('$baseUrl/notifications/friend-requests/$userId');
    final response = await http.get(url);

    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((request) => "${request['senderUsername']} sent a Friend Request").toList();
    } else {
      throw Exception('Failed to load friend requests');
    }
  }
}
