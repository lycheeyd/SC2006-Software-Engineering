class UserProfile {
  late final String _name;
  late final String _email;
  late final String _userID;
  late String _bio;
  late int _weight;
  late int _carbonSaved;
  late int _calorieBurn;
  late List<String> _badges;

  // Constructor
  UserProfile({
    required String name,
    required String email,
    required String userID,
    required String bio,
    required int weight,
    required int carbonSaved,
    required int calorieBurn,
    required List<String> badges,
  }) {
    _name = name;
    _email = email;
    _userID = userID;
    _bio = bio;
    _weight = weight;
    _carbonSaved = carbonSaved;
    _calorieBurn = calorieBurn;
    _badges = badges;
  }

  // Getters
  String getName() {
    return _name;
  }

  String getEmail() {
    return _email;
  }

  String getUserID() {
    return _userID;
  }

  String getBio() {
    return _bio;
  }

  int getWeight() {
    return _weight;
  }

  int getCarbonSaved() {
    return _carbonSaved;
  }

  int getCalorieBurn() {
    return _calorieBurn;
  }

  List<String> getBadges() {
    return _badges;
  }

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
