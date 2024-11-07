import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/words2widget_converter.dart';
import 'package:flutter/material.dart';
import '../control/apiService.dart';
import 'package:google_fonts/google_fonts.dart';
 // Import the converter

class SuccessPage extends StatefulWidget {
  final int caloriesBurnt;
  final int carbonSaved;
  final String tripMethod;
  final String currentLocation;
  final String destination;
  final double distance;
  final String userId;

  SuccessPage({
    required this.caloriesBurnt,
    required this.carbonSaved,
    required this.tripMethod,
    required this.currentLocation,
    required this.destination,
    required this.distance,
    required this.userId,
  });

  @override
  _SuccessPageState createState() => _SuccessPageState();
}

class _SuccessPageState extends State<SuccessPage> with SingleTickerProviderStateMixin {
  int totalCarbonSavedExp = 0;
  int totalCalorieBurntExp = 0;
  String carbonSavedMedal = "No Medal";
  String calorieBurntMedal = "No Medal";
  late String _userId;

  final int pointsToNextBronze = 1000;
  final int pointsToNextSilver = 5000;
  final int pointsToNextGold = 10000;
  final int pointsToNextPlatinum = 15000;

  int maxCarbon = 0;
  int maxCalorie = 0;

  late AnimationController _controller;
  late Animation<double> _animation;

  @override
  void initState() {
    super.initState();
    _userId = widget.userId;
    _controller = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    );
    _animation = Tween<double>(begin: 0.0, end: 1.0).animate(_controller);
    fetchAchievements();

  }

  @override 
  void didUpdateWidget(SuccessPage oldWidget){
    super.didUpdateWidget(oldWidget);
    fetchAchievements();
  }

  Future<void> fetchAchievements() async {
    ApiService apiService = ApiService();
    var achievements = await apiService.getAchievementProgress(_userId);
    //var achievements = await apiService.getAchievementProgress();

    setState(() {
      totalCarbonSavedExp = achievements['totalCarbonSavedExp'];
      totalCalorieBurntExp = achievements['totalCalorieBurntExp'];
      carbonSavedMedal = achievements['carbonSavedMedal'];
      calorieBurntMedal = achievements['calorieBurntMedal'];
      print(carbonSavedMedal);
      print(calorieBurntMedal);
    });
    _controller.forward();
    maxCarbon = _retrieveThreshold(totalCarbonSavedExp, pointsToNextPlatinum, pointsToNextGold, pointsToNextSilver, pointsToNextBronze);
    maxCalorie = _retrieveThreshold(totalCalorieBurntExp, pointsToNextPlatinum, pointsToNextGold, pointsToNextSilver, pointsToNextBronze);
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: Center(
        child: SingleChildScrollView(
          child: Card(
            color: PrimaryColors.dullGreen,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(16),
            ),
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                mainAxisSize: MainAxisSize.min,
                children: [
                  Text(
                    "Congratulations! 🎉",
                    style: GoogleFonts.rammettoOne(
                          fontSize: 30,
                          fontWeight: FontWeight.bold,
                          color: Colors.white),
                    textAlign: TextAlign.center,
                  ),
                  SizedBox(height: 10),
                  Text(
                    "You have traveled ${widget.distance.toStringAsFixed(2)} km to ${widget.destination}.",
                    style: GoogleFonts.openSans(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      color: Colors.black54,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  SizedBox(height: 20),
                  _buildProgressSection(
                    title: "Carbon Saved EXP: ${_formatExpDisplay(totalCarbonSavedExp, maxCarbon)}",
                    value: totalCarbonSavedExp,
                    medal: carbonSavedMedal,
                    gainedExp: widget.carbonSaved,
                  ),
                  SizedBox(height: 15),
                  _buildProgressSection(
                    title: "Calories Burnt EXP: ${_formatExpDisplay(totalCalorieBurntExp, maxCalorie)}",
                    value: totalCalorieBurntExp,
                    medal: calorieBurntMedal,
                    gainedExp: widget.caloriesBurnt,
                  ),
                  SizedBox(height: 20),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      padding: EdgeInsets.symmetric(horizontal: 30, vertical: 12),
                      backgroundColor: Colors.green[700],
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: Text(
                      "Back to Map",
                      style: GoogleFonts.openSans(
                        fontSize: 18,
                        fontWeight: FontWeight.w600,
                        color: Colors.white,
                      ),
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

  String _formatExpDisplay(int currentExp, int platinumThreshold) {
    return currentExp >= platinumThreshold ? "$platinumThreshold/$platinumThreshold" : "$currentExp/$platinumThreshold";
  }

  int _retrieveThreshold(int value, int pointsToNextPlatinum, int pointsToNextGold, int pointsToNextSilver, int pointsToNextBronze) {
    if (value >= pointsToNextGold) return pointsToNextPlatinum;
    if (value >= pointsToNextSilver) return pointsToNextGold;
    if (value >= pointsToNextBronze) return pointsToNextSilver;
    return pointsToNextBronze;
  }

  Widget _buildProgressSection({
    required String title,
    required int value,
    required String medal,
    required int gainedExp,
  }) {
    double progress = (value >= pointsToNextPlatinum) ? 1.0 : value / pointsToNextBronze;
    progress = progress.clamp(0.0, 1.0);

    return Container(
      decoration: _getCardBackgroundImage(medal),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Text(
              title,
              style: GoogleFonts.openSans(
                fontSize: 20,
                fontWeight: FontWeight.bold,
              ),
              textAlign: TextAlign.center,
            ),
            SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                SizedBox(height: 50, width: 50, child: Words2widgetConverter.convert(medal) ?? Container(),),
                SizedBox(width: 8),
                Expanded(
                  child: AnimatedBuilder(
                    animation: _animation,
                    builder: (context, child) {
                      return LinearProgressIndicator(
                        value: progress * _animation.value,
                        backgroundColor: Colors.grey[300],
                        color: Colors.red,
                        minHeight: 8,
                      );
                    },
                  ),
                ),
                SizedBox(width: 10),
                Text(
                  value >= pointsToNextPlatinum ? "MAX" : "+$gainedExp EXP",
                  style: GoogleFonts.openSans(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                  textAlign: TextAlign.center,
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  BoxDecoration _getCardBackgroundImage(String medal) {
    switch (medal) {
      case "CaloriePlatinum":
      case "EcoPlatinum":
        return BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/Platinum.jpg'),
            fit: BoxFit.cover,
          ),
        );
      case "CalorieGold":
      case "EcoGold":
        return BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/Gold.jpg'),
            fit: BoxFit.cover,
          ),
        );
      case "CalorieSilver":
      case "EcoSilver":
        return BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/Silver.jpg'),
            fit: BoxFit.cover,
            )
          );
      case "CalorieBronze":
      case "EcoBronze":
        return BoxDecoration(
          image: DecorationImage(
            image: AssetImage('assets/images/Bronze.jpg'),
            fit: BoxFit.cover,
          ),
        );
      default:
        return BoxDecoration(color: Colors.white);
    }
  }
}
