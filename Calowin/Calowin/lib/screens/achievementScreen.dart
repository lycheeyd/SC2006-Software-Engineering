import 'package:flutter/material.dart';
import '../services/apiService.dart';

class AchievementScreen extends StatefulWidget {
  final int caloriesBurnt;
  final int carbonSaved;
  final String tripMethod;
  final String currentLocation;
  final String destination;
  final double distance;

  AchievementScreen({
    required this.caloriesBurnt,
    required this.carbonSaved,
    required this.tripMethod,
    required this.currentLocation,
    required this.destination,
    required this.distance,
  });

  @override
  _AchievementScreenState createState() => _AchievementScreenState();
}

class _AchievementScreenState extends State<AchievementScreen> {
  int totalCarbonSavedExp = 0;
  int totalCalorieBurntExp = 0;
  String carbonSavedMedal = "No Medal";
  String calorieBurntMedal = "No Medal";

  // Define thresholds for medals
  int pointsToNextCarbonBronze = 1000;
  int pointsToNextCarbonSilver = 5000;
  int pointsToNextCarbonGold = 10000;

  int pointsToNextCalorieBronze = 1000;
  int pointsToNextCalorieSilver = 5000;
  int pointsToNextCalorieGold = 10000;

  @override
  void initState() {
    super.initState();
    fetchAchievements();
  }

  Future<void> fetchAchievements() async {
    ApiService apiService = ApiService();
    var achievements = await apiService.getAchievementProgress();
    setState(() {
      totalCarbonSavedExp = achievements['totalCarbonSavedExp'];
      totalCalorieBurntExp = achievements['totalCalorieBurntExp'];
      carbonSavedMedal = achievements['carbonSavedMedal'];
      calorieBurntMedal = achievements['calorieBurntMedal'];
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Achievements"),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: Card(
            elevation: 4,
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    "Congratulations! 🎉",
                    style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.bold,
                      color: Colors.green[800],
                    ),
                    textAlign: TextAlign.center,
                  ),
                  SizedBox(height: 8),
                  Text(
                    "You have traveled ${widget.distance} km by ${widget.tripMethod} from ${widget.currentLocation} to ${widget.destination}.",
                    style: TextStyle(fontSize: 16, color: Colors.black54),
                    textAlign: TextAlign.center,
                  ),
                  SizedBox(height: 20),
                  _buildProgressSection(
                    title: "Carbon Saved EXP: $totalCarbonSavedExp / ${_getCarbonThreshold()}",
                    value: totalCarbonSavedExp,
                    medal: carbonSavedMedal,
                    pointsToNextBronze: pointsToNextCarbonBronze,
                    pointsToNextSilver: pointsToNextCarbonSilver,
                    pointsToNextGold: pointsToNextCarbonGold,
                  ),
                  SizedBox(height: 10),
                  _buildProgressSection(
                    title: "Calories Burnt EXP: $totalCalorieBurntExp / ${_getCalorieThreshold()}",
                    value: totalCalorieBurntExp,
                    medal: calorieBurntMedal,
                    pointsToNextBronze: pointsToNextCalorieBronze,
                    pointsToNextSilver: pointsToNextCalorieSilver,
                    pointsToNextGold: pointsToNextCalorieGold,
                  ),
                  SizedBox(height: 20),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      padding:
                          EdgeInsets.symmetric(horizontal: 30, vertical: 12),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: Text(
                      "Back to Home",
                      style: TextStyle(fontSize: 18),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  String _getCalorieThreshold() {
    // Determine the current threshold based on the user's EXP
    
    if (totalCalorieBurntExp >= pointsToNextCalorieSilver) {
      return pointsToNextCalorieGold.toString(); // Silver threshold
    } else if (totalCalorieBurntExp >= pointsToNextCalorieBronze) {
      return pointsToNextCalorieSilver.toString(); // Silver threshold
    } else {
      return pointsToNextCalorieBronze.toString(); // Default to bronze threshold
    }
  }

  String _getCarbonThreshold() {
    // Determine the current threshold based on the user's EXP
    
    if (totalCarbonSavedExp >= pointsToNextCarbonSilver) {
      return pointsToNextCarbonGold.toString(); // Silver threshold
    } else if (totalCarbonSavedExp >= pointsToNextCarbonBronze) {
      return pointsToNextCalorieSilver.toString(); // Silver threshold
    } else {
      return pointsToNextCalorieBronze.toString(); // Default to bronze threshold
    }
  }

  Widget _buildProgressSection({
    required String title,
    required int value,
    required String medal,
    required int pointsToNextBronze,
    required int pointsToNextSilver,
    required int pointsToNextGold,
  }) {
    double progress = 0.0;

    // Check if user has reached bronze threshold
    if (value >= pointsToNextBronze) {
      // Calculate the amount of experience earned after reaching bronze
      int experienceAfterBronze = value - pointsToNextBronze;

      // Check if user reached silver threshold
      if (experienceAfterBronze >= pointsToNextSilver - pointsToNextBronze) {
        progress = 1.0; // Set to complete for silver

        // Calculate experience after reaching silver
        int experienceAfterSilver = experienceAfterBronze - (pointsToNextSilver - pointsToNextBronze);

        // Check if user reached gold threshold
        if (experienceAfterSilver >= pointsToNextGold - pointsToNextSilver) {
          progress = 1.0; // Set to complete for gold
        } else {
          progress = experienceAfterSilver / (pointsToNextGold - pointsToNextSilver);
        }
      } else {
        progress = experienceAfterBronze / (pointsToNextSilver - pointsToNextBronze);
      }
    } else {
      // Normal progress calculation based on the current value
      progress = value / pointsToNextBronze;
    }

    // Clamp progress to avoid exceeding bounds
    progress = progress.clamp(0.0, 1.0);

    return Card(
      elevation: 4,
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Text(
              title,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 10),
            LinearProgressIndicator(
              value: progress,
              backgroundColor: Colors.grey[300],
              color: Colors.blue,
            ),
            SizedBox(height: 10),
            Text(
              "Current Badge: $medal",
              style: TextStyle(fontSize: 18),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 10),
            if (medal == "No Medal") ...[
              Text(
                "Points needed for Bronze Badge: ${pointsToNextBronze - value}",
                style: TextStyle(fontSize: 18, color: Colors.red),
                textAlign: TextAlign.center,
              ),
            ],
            if (medal == "Bronze") ...[
              Text(
                "Points needed for Silver Badge: ${pointsToNextSilver - value}",
                style: TextStyle(fontSize: 18, color: Colors.red),
                textAlign: TextAlign.center,
              ),
            ],
            if (medal == "Silver") ...[
              Text(
                "Points needed for Gold Badge: ${pointsToNextGold - value}",
                style: TextStyle(fontSize: 18, color: Colors.red),
                textAlign: TextAlign.center,
              ),
            ],
          ],
        ),
      ),
    );
  }
}
