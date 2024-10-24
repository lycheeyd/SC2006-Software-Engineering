import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class WellnessPage extends StatefulWidget {
  const WellnessPage({super.key});

  @override
  State<WellnessPage> createState() => _WellnessPageState();
}

class _WellnessPageState extends State<WellnessPage> {
  int _currentIndex = -1;

  final List<Map<String, dynamic>> _wellnessZones = [
    {"name": "East Coast Park", "distance": 5},
    {"name": "West Coast Park", "distance": 3},
    {"name": "Lake Side Garden", "distance": 4},
    {"name": "Sentosa", "distance": 2},
    {"name": "Gardens By The Bay", "distance": 7}
  ];

  void _onListItemTap(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _handleGO() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    //change here
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(0); // Navigate to AddFriendsPage
    }
  }

  void _getWellnessZones() {}

  Widget _buildListItem(int index, Map<String, dynamic> zone) {
    Color tileColor = const Color.fromARGB(10, 0, 0, 0);
    Color selectedColor = const Color.fromARGB(255, 232, 231, 253);
    return Padding(
      padding: const EdgeInsets.only(top: 5, bottom: 5, left: 10, right: 3),
      child: Container(
        height: 60,
        width: 400,
        decoration: BoxDecoration(
          color: index == _currentIndex ? selectedColor : tileColor,
          borderRadius: BorderRadius.circular(10),
          border: const Border(
            bottom: BorderSide(
              color: Colors.grey, // Change this to your desired border color
              width: 2, // Set the desired width of the bottom border
            ),
          ),
        ),
        child: ListTile(
          title: Text(
            zone["name"],
            style:
                GoogleFonts.poppins(fontWeight: FontWeight.bold, fontSize: 15),
          ),
          trailing: SizedBox(
            width: 120,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(
                  "${zone["distance"].toString()} km",
                  style: GoogleFonts.poppins(
                      fontSize: 14, fontWeight: FontWeight.w600),
                ),
                const SizedBox(
                  width: 10,
                ),
                SizedBox(
                  width: 60,
                  height: 30,
                  child: ElevatedButton(
                      onPressed: _handleGO,
                      style: ElevatedButton.styleFrom(
                        elevation: 0,
                        backgroundColor: Colors.purple,
                        padding: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 5),
                        shape: RoundedRectangleBorder(
                          borderRadius:
                              BorderRadius.circular(50), // Rounded corners
                        ),
                      ),
                      child: const Text(
                        "Go",
                        style: TextStyle(fontSize: 12, color: Colors.white),
                      )),
                )
              ],
            ),
          ),
          onTap: () => _onListItemTap(index),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: Column(
        children: [
          Container(
            color: Colors.white,
            height: 400,
            width: 400,
            child: const Center(child: Text("<Insert Map Here>")),
          ),
          Container(
            color: PrimaryColors.grey,
            height: 30,
            width: 400,
            child: const Center(
                child: Text(
              "Wellness Zones",
              style:
                  TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
            )),
          ),
          Expanded(
            child: Container(
              color: Colors.white,
              child: ListView.builder(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                itemCount: _wellnessZones.length,
                itemBuilder: (context, index) {
                  Map<String, dynamic> currentItem = _wellnessZones[index];
                  return _buildListItem(index, currentItem);
                },
              ),
            ),
          )
        ],
      ),
    );
  }
}
