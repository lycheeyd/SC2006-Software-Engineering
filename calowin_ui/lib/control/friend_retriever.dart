import 'dart:convert';
import 'package:calowin/common/user_profile.dart';
import 'package:http/http.dart' as http;

class FriendRetriever {
  final String _baseUrl =
      'http://172.21.146.188:8080/central/account'; // Replace with your backend URL

  Future<UserProfile> retrieveFriend(String userId, String otherId) async {
    // /selfid/otherid
    final url = Uri.parse('$_baseUrl/$userId/$otherId');
    print(url);

    try {
      final response = await http.get(url);

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);

        // Convert the JSON response to a list of FriendRetrieverItem objects
        UserProfile user = data.fromJson(data);
        
        return user;
      } else {
        // Handle error responses
        print('Failed to retrieve user: ${response.statusCode}');
        return UserProfile(name: "User not found", userID: "User not found");
      }
    } catch (e) {
      // Handle network or parsing errors
      print('Error occurred while retrieving user: $e');
      return UserProfile(name: "User not found", userID: "User not found");
    }
  }

  
}
