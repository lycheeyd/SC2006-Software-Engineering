import 'package:calowin/Pages/success_page.dart';
import 'package:calowin/common/dualbutton_dialog.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/colors_and_fonts.dart';

class WellnessPage extends StatefulWidget {
  const WellnessPage({super.key});

  @override
  State<WellnessPage> createState() => _WellnessPageState();
}

class _WellnessPageState extends State<WellnessPage> {
// Google Map Controller
  // late GoogleMapController mapController;
  // static const LatLng _center = LatLng(1.3483, 103.6831); // Nanyang Technological University location

  // Search Radius slider value
  double _currentRadius = 10;
  int selectedItemIndex = -1;

  void _handleItemPressed(index) {
    setState(() {
      selectedItemIndex = index;
    });
    // selectedItemIndex = index;
    // print("Item selected");
  }

  // List of wellness zones
  final List<Map<String, dynamic>> wellnessZones = [
    {"name": "West Coast Park", 
     "description": "A chill place", 
     "distance": 2,
     "forecast": "heavy Rain"
     },
    {"name": "Student Recreational Center", 
     "description": "For exercise", 
     "distance": 10,
     "forecast": "Sunny"
     },
  ];

  // void _onMapCreated(GoogleMapController controller) {
  //   mapController = controller;
  // }

  @override
  Widget build(BuildContext context) {
    final filteredZones = wellnessZones.where((zone) {
      return zone['distance'] <= _currentRadius; // Show only zones within the radius
    }).toList();

    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: PrimaryColors.dullGreen,
      
      body: Column(
        children: [
          // Weather widget
           // Weather Forecast
          Positioned(
            top: 20,
            left: 10,
            right: 10,
            child: selectedItemIndex != -1 ?// Only show if West Coast Park is selected
                  Container(
                    padding: EdgeInsets.all(10),
                    decoration: BoxDecoration(
                      color: Colors.black.withOpacity(0.5),
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Row(
                      children: [
                        // const Icon(Icons.cloud, color: Colors.white),
                        const SizedBox(width: 10),
                        Text(
                          'Next 2 hr forecast: ${wellnessZones[selectedItemIndex]['forecast']}',
                          style: const TextStyle(color: Colors.white, fontSize: 16),
                        ),
                        
                      ],
                    ),
                  )
                : Container(),
          ),

          // Map widget
          Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.width + 30,
            color: Colors.white,
            child: const Center(
              child: Text("Insert Map Here"),
            ),
          ),

          // Search Radius Slider
          Positioned(
            bottom: 10,
            left: 10,
            right: 10,
            child: Container(
              color: Colors.grey[200]?.withOpacity(0.8),
              padding: EdgeInsets.symmetric(horizontal: 10, vertical: 5),
              child: Row(
                children: [
                  Text('Search Radius'),
                  Expanded(
                    child: Slider(
                      value: _currentRadius,
                      min: 1,
                      max: 20,
                      divisions: 19,
                      label: '${_currentRadius.toInt()} KM',
                      onChanged: (double value) {
                        setState(() {
                          _currentRadius = value;
                        });
                      },
                    ),
                  ),
                  Text('${_currentRadius.toInt()}KM'),
                ],
              ),
            ),
          ),
              

          // Wellness Zones List
          Expanded(
            flex: 2,
            child: Container(
              color: Colors.white,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Padding(
                    padding: const EdgeInsets.all(8.0),
                    // backgroundColor: PrimaryColors.dullGreen,
                    child: Text(
                      'Wellness Zones',
                      style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                    ),
                  ),
                  Expanded(
                    child: ListView.builder(
                      itemCount: filteredZones.length,
                      itemBuilder: (context, index) {
                        return ListTile(
                          title: Text(filteredZones[index]['name']),
                          subtitle: Text(filteredZones[index]['description']),
                          trailing: Column(
                            children: [
                              Text("${filteredZones[index]['distance']} km"),
                              ElevatedButton(
                                onPressed: () => {
                                  _handleItemPressed(index)
                                },
                                style: ElevatedButton.styleFrom(
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(20),
                                  ),
                                  // primary: Colors.purple, // Adjust the button color
                                ),
                                child: Text('Go'),
                              ),
                            ],
                          ),
                        );
                      },
                    ),
                  ),
                ],
              ),
            ),
          ),

        ],
      ),
    );
  }
}