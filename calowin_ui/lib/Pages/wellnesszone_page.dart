import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:calowin/control/park_retriever.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class WellnessZonePage extends StatefulWidget {
  const WellnessZonePage({super.key});

  @override
  State<WellnessZonePage> createState() => _WellnessZonePageState();
}

class _WellnessZonePageState extends State<WellnessZonePage> {
  int _currentIndex = -1;
  final double _sliderMin = 1;
  final double _sliderMax = 20;
  double _sliderValue = 5;
  ParkRetriever retriever = ParkRetriever();

  //to be retrieved
  double userLat = 1.385170;
  double userLon = 103.79615;

  // final List<Map<String, dynamic>> _wellnessZones = [
  //   {"name": "East Coast Park", "distance": 5},
  //   {"name": "West Coast Park", "distance": 3},
  //   {"name": "Lake Side Garden", "distance": 4},
  //   {"name": "Sentosa", "distance": 2},
  //   {"name": "Gardens By The Bay", "distance": 7}
  // ];

  List<Park> _wellnessZones = [];
  List<Park> _filteredZones = [];

  void _retrieveWellnessZones() async {
    //_wellnessZones = await retriever.retrievePark(userLat, userLon);
    //testing list for filtering
    setState(() {
      _wellnessZones = [
        Park(
          name: "Central Park",
          distance: 5.3,
          closestPoint: {"Lat": 1.3000, "Lon": 103.8000},
        ),
        Park(
          name: "Greenwood Park",
          distance: 12.7,
          closestPoint: {"Lat": 1.3050, "Lon": 103.8100},
        ),
        Park(
          name: "Sunshine Gardens",
          distance: 9.8,
          closestPoint: {"Lat": 1.3200, "Lon": 103.8200},
        ),
        Park(
          name: "Maple Leaf Park",
          distance: 3.1,
          closestPoint: {"Lat": 1.3350, "Lon": 103.8250},
        ),
        Park(
          name: "Riverside Park",
          distance: 15.4,
          closestPoint: {"Lat": 1.3450, "Lon": 103.8350},
        ),
        Park(
          name: "Hillside Park",
          distance: 7.2,
          closestPoint: {"Lat": 1.3550, "Lon": 103.8450},
        ),
        Park(
          name: "Forest Grove",
          distance: 19.0,
          closestPoint: {"Lat": 1.3600, "Lon": 103.8550},
        ),
        Park(
          name: "Lakeside Park",
          distance: 2.6,
          closestPoint: {"Lat": 1.3700, "Lon": 103.8650},
        ),
        Park(
          name: "Willow Creek Park",
          distance: 14.1,
          closestPoint: {"Lat": 1.3800, "Lon": 103.8750},
        ),
        Park(
          name: "Evergreen Park",
          distance: 8.3,
          closestPoint: {"Lat": 1.3900, "Lon": 103.8850},
        ),
        Park(
          name: "Pine Ridge Park",
          distance: 6.7,
          closestPoint: {"Lat": 1.4000, "Lon": 103.8950},
        ),
        Park(
          name: "Oakwood Park",
          distance: 17.5,
          closestPoint: {"Lat": 1.4100, "Lon": 103.9050},
        ),
      ];
    });

    _filterWellnessZones(_sliderValue);
  }

  void _filterWellnessZones(double radius) {
    setState(() {
      _filteredZones = _wellnessZones.where((zone) {
        return zone.distance <=
            _sliderValue; // Show only zones within the radius
      }).toList();
      _filteredZones.sort((a, b) => a.distance.compareTo(b.distance));
      _sliderValue = radius;
    });
  }

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

  Widget _buildListItem(int index, Park zone) {
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
              color: Colors.grey,
              width: 2,
            ),
          ),
        ),
        child: ListTile(
          title: Text(
            zone.name,
            style:
                GoogleFonts.poppins(fontWeight: FontWeight.bold, fontSize: 15),
          ),
          trailing: SizedBox(
            width: 130,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(
                  "${zone.distance.toString()} km",
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
  void initState() {
    super.initState();
    _retrieveWellnessZones();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: Column(
        children: [
          SizedBox(
            height: 400,
            width: 400,
            child: Stack(
              children: [
                Container(
                  color: const Color.fromARGB(255, 138, 218, 255),
                  height: 400,
                  width: 400,
                  child: const Center(child: Text("<Insert Map Here>")),
                ),
                Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 15, horizontal: 5),
                  child: Align(
                    alignment: Alignment.bottomCenter,
                    child: Container(
                      decoration: BoxDecoration(
                          color: const Color.fromARGB(50, 0, 0, 0),
                          borderRadius: BorderRadius.circular(10)),
                      height: 45,
                      width: 300,
                      child: Column(
                        children: [
                          const Padding(
                            padding:
                                EdgeInsets.only(left: 20, top: 3, bottom: 0),
                            child: Align(
                                alignment: Alignment.topLeft,
                                child: Text(
                                  "Search Radius",
                                  style: TextStyle(color: Colors.white),
                                )),
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              SizedBox(
                                height: 18,
                                width: 240,
                                child: SliderTheme(
                                  data: SliderTheme.of(context).copyWith(
                                    thumbShape: const RoundSliderThumbShape(
                                        pressedElevation: 0,
                                        enabledThumbRadius:
                                            7.0), // Change thumb size here
                                    overlayShape: const RoundSliderOverlayShape(
                                        overlayRadius:
                                            8.0), // Change overlay size
                                  ),
                                  child: Slider(
                                    activeColor: Colors.black,
                                    overlayColor: const WidgetStatePropertyAll(
                                        Colors.black),
                                    value: _sliderValue,
                                    min: _sliderMin,
                                    max: _sliderMax,
                                    divisions: 200,
                                    onChanged: (double value) {
                                      _filterWellnessZones(value);
                                    },
                                  ),
                                ),
                              ),
                              const SizedBox(
                                width: 7,
                              ),
                              Align(
                                  alignment: Alignment.bottomRight,
                                  child: Text(_sliderValue.toStringAsFixed(1),
                                      style: const TextStyle(
                                          color: Colors.white))),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            color: PrimaryColors.grey,
            height: 40,
            width: 400,
            child: Stack(
              children: [
                // Use Expanded to take all the available space for the text
                const Center(
                  child: Text(
                    "Wellness Zones",
                    style: TextStyle(
                        color: Colors.black, fontWeight: FontWeight.bold),
                  ),
                ),
                Align(
                  alignment: Alignment.centerRight,
                  child: IconButton(
                    iconSize: 20,
                    onPressed: _retrieveWellnessZones,
                    icon: const Icon(
                      Icons.refresh,
                      color: Colors.black,
                    ),
                  ),
                ),
              ],
            ),
          ),
          Expanded(
            child: Container(
              color: Colors.white,
              child: ListView.builder(
                scrollDirection: Axis.vertical,
                shrinkWrap: true,
                itemCount: _filteredZones.length,
                itemBuilder: (context, index) {
                  Park currentItem = _filteredZones[index];
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
