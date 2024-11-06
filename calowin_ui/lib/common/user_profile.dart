class UserProfile {
  late String _name;
  late String? _email;
  late String _userID;
  late String _bio;
  late int? _weight;
  late int? _carbonSaved;
  late int? _calorieBurn;
  late List<String>? _badges;

  // Constructor
  UserProfile({
    required String name,
    String? email,
    required String userID,
    required String bio,
    int? weight,
    int? carbonSaved,
    int? calorieBurn,
    List<String>? badges,
  }) {
    _name = name ?? '';
    _email = email;
    _userID = userID ?? '';
    _bio = bio ?? '';
    _weight = weight;
    _carbonSaved = carbonSaved;
    _calorieBurn = calorieBurn;
    _badges = badges;
  }

  // Factory constructor for deserialization from LoginResponseDTO
  factory UserProfile.fromJson(Map<String, dynamic> json) {
    return UserProfile(
      name: json['name'] as String,
      email: json['email'] as String?,
      userID: json['userID'] as String,
      bio: json['bio'] as String,
      weight: (json['weight'] as num?)?.toInt(),  // Convert to int if available
      // Other fields like _carbonSaved, _calorieBurn, and _badges can remain null
      // or be set later as they are not part of LoginResponseDTO
    );
  }

  // Getters
  String getName() => _name;
  String? getEmail() => _email;
  String getUserID() => _userID;
  String getBio() => _bio;
  int getWeight() => _weight ?? 0;
  int getCarbonSaved() => _carbonSaved ?? 0;
  int getCalorieBurn() => _calorieBurn ?? 0;
  List<String> getBadges() => _badges ?? [];

  // Setters
  void setBio(String bio) {
    _bio = bio;
  }

  void setWeight(int weight) {
    _weight = weight;
  }

  void setCarbonSaved(int carbonSaved) {
    _carbonSaved = carbonSaved;
  }

  void setCalorieBurn(int calorieBurn) {
    _calorieBurn = calorieBurn;
  }

  void setBadges(List<String> badges) {
    _badges = badges;
  }
}
