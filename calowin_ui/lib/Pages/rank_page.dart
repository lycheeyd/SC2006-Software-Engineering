import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/medals.dart';

class RankPage extends StatefulWidget {
  const RankPage({super.key});

  @override
  State<RankPage> createState() => _RankPageState();
}

class _RankPageState extends State<RankPage> {
  bool _isCalorie = true;

  //to be retrieved from backend
  List<Map<String, dynamic>> caloriesleaderBoard = [
    {"name": "Alice", "medal": Medals.caloriePlatinum, "points": 2000},
    {"name": "Bob", "medal": Medals.caloriePlatinum, "points": 1900},
    {"name": "Charlie", "medal": Medals.caloriePlatinum, "points": 1500},
    {"name": "Diana", "medal": Medals.calorieGold, "points": 1300},
    {"name": "Ethan", "medal": Medals.calorieGold, "points": 500},
    {"name": "Fiona", "medal": Medals.calorieGold, "points": 93},
    {"name": "George", "medal": Medals.calorieSilver, "points": 80},
    {"name": "Hannah", "medal": Medals.calorieSilver, "points": 75},
    {"name": "Ivan", "medal": Medals.calorieBronze, "points": 60},
    {"name": "Jack", "medal": Medals.calorieBronze, "points": 50},
  ];

  List<Map<String, dynamic>> carbonleaderBoard = [
    {"name": "Liam", "medal": Medals.ecoPlatinum, "points": 2102},
    {"name": "Mia", "medal": Medals.ecoGold, "points": 1221},
    {"name": "Noah", "medal": Medals.ecoGold, "points": 646},
    {"name": "Olivia", "medal": Medals.ecoSilver, "points": 131},
    {"name": "Paul", "medal": Medals.ecoSilver, "points": 121},
    {"name": "Quincy", "medal": Medals.ecoBronze, "points": 93},
    {"name": "Rachel", "medal": Medals.ecoBronze, "points": 79},
    {"name": "Sophia", "medal": Medals.ecoBronze, "points": 60},
    {"name": "Thomas", "medal": Medals.ecoBronze, "points": 51},
    {"name": "Uma", "medal": null, "points": 42},
  ];

  void _toggleLeaderBoard() {
    setState(() {
      _isCalorie = !_isCalorie;
    });
  }

  void _onListItemTap(String name) {
    //first retrieve the user id then pass to the page navigator (logic to be considered)
    const String userID = "#12345";
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();

    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(3,
          params: {'userID': userID}); // Navigate to Profile tab
    }
  }

  Widget _buildListItem(int index, String name, int points, Image? medal) {
    Color tileColor;
    double fontsize = 18;
    double medalsize = 45;
    TextStyle fontStyle =
        GoogleFonts.rammettoOne(fontSize: fontsize, color: Colors.black);
    switch (index) {
      case 0:
        tileColor = const Color.fromARGB(255, 220, 203, 12);
        break;
      case 1:
        tileColor = const Color.fromARGB(255, 225, 225, 225);
        break;
      case 2:
        tileColor = const Color.fromARGB(255, 220, 131, 15);
        break;
      default:
        tileColor = const Color.fromARGB(255, 214, 241, 214);
        fontsize = 18;
        fontStyle = GoogleFonts.aBeeZee(
            fontSize: fontsize, fontWeight: FontWeight.bold);
        medalsize = 30;
        break;
    }

    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 5),
      child: Container(
        decoration: BoxDecoration(
          color: tileColor, // Rounded corners
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.2), // Shadow color with opacity
              blurRadius: 6, // Softness of the shadow
              offset: const Offset(0, 4), // Offset in x and y direction
            ),
          ],
        ),
        child: ListTile(
          onTap: () => _onListItemTap(name),
          leading: Text("${index + 1}",
              style: fontStyle.copyWith(fontWeight: FontWeight.bold)),
          title: Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(
                name,
                style: fontStyle,
              ),
              const SizedBox(
                width: 10,
              ),
              SizedBox(height: medalsize, width: medalsize, child: medal)
            ],
          ),
          trailing: Text(
            points.toString(),
            style: fontStyle,
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      appBar: AppBar(
        leadingWidth: 300,
        backgroundColor: PrimaryColors.logoGreen,
        leading: Align(
          alignment: Alignment.centerLeft,
          child: Padding(
            padding: const EdgeInsets.only(left: 12),
            child: Text(
              "${_isCalorie ? "Calorie Points" : "Carbon Saved"} LeaderBoard",
              style: GoogleFonts.aBeeZee(
                  fontSize: 18,
                  color: Colors.black,
                  fontStyle: FontStyle.italic),
            ),
          ),
        ),
        actions: [
          IconButton(
              onPressed: _toggleLeaderBoard,
              icon: const Icon(
                Icons.swap_horiz_outlined,
                size: 35,
              ))
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 5),
        child: ListView.builder(
          scrollDirection: Axis.vertical,
          shrinkWrap: true,
          itemCount: _isCalorie
              ? caloriesleaderBoard.length
              : carbonleaderBoard.length,
          itemBuilder: (context, index) {
            Map currentItem = _isCalorie
                ? caloriesleaderBoard[index]
                : carbonleaderBoard[index];
            return _buildListItem(index, currentItem["name"],
                currentItem["points"], currentItem["medal"]);
          },
        ),
      ),
    );
  }
}
