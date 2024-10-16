import 'package:calowin/Pages/success_page.dart';
import 'package:calowin/common/dualbutton_dialog.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/colors_and_fonts.dart';

class MapcalcPage extends StatefulWidget {
  const MapcalcPage({super.key});

  @override
  State<MapcalcPage> createState() => _MapcalcPageState();
}

class _MapcalcPageState extends State<MapcalcPage> {
  late int _currentIndex = 99; //to unselect transport method
  late bool _tripStarted = false;

  void _handleSearch(String address) {}

  void _handleStart() {
    setState(() {
      _tripStarted = true;
    });
  }

  void _handleCancel() {
    setState(() {
      _tripStarted = false;
    });
  }

  void _handleEnd() {
    setState(() {
      _tripStarted = false;
    });
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => const SuccessPage(
                  congratsText:
                      "You've completed 5km by bicycle!!!", //needs to implement logic here
                )));
  }

//edit here for the logic of transport method change
  void _onItemTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  Widget _transportIconBuilder(IconData icon, String title, int index) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        IconButton(
          onPressed: () => _onItemTapped(index),
          icon: Icon(
            icon,
            color: _currentIndex == index ? Colors.black : Colors.grey.shade700,
          ),
        ),
        Text(
          title,
          style: TextStyle(
              fontSize: 10,
              color:
                  _currentIndex == index ? Colors.black : Colors.grey.shade700),
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
            child: SearchAnchor(
                builder: (BuildContext context, SearchController controller) {
              return SearchBar(
                elevation: const WidgetStatePropertyAll(0),
                controller: controller,
                onTap: () {
                  controller.openView();
                },
                onChanged: (_) {
                  controller.openView();
                },
                trailing: <Widget>[
                  Tooltip(
                    message: 'Search',
                    child: IconButton(
                      onPressed: () => _handleSearch(controller.text),
                      icon: const Icon(Icons.search),
                    ),
                  )
                ],
              );
            },
                //temporary suggestion features
                suggestionsBuilder:
                    (BuildContext context, SearchController controller) {
              return List<ListTile>.generate(5, (int index) {
                final String item = 'item $index';
                return ListTile(
                  title: Text(item),
                  onTap: () {
                    setState(() {
                      controller.closeView(item);
                    });
                  },
                );
              });
            }),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              _transportIconBuilder(Icons.directions_walk, "Walk", 0),
              _transportIconBuilder(Icons.pedal_bike, "Bicycle", 1),
              _transportIconBuilder(Icons.directions_bus, "Bus", 2),
              _transportIconBuilder(Icons.directions_car, "Car", 3)
            ],
          ),
          const SizedBox(
            height: 15,
          ),
          Container(
            width: MediaQuery.of(context).size.width,
            height: MediaQuery.of(context).size.width + 30,
            color: Colors.white,
            child: const Center(
              child: Text("Insert Map Here"),
            ),
          ),
          Expanded(
            child: SizedBox(
              child: !_tripStarted //check which buttons to display
                  ? Center(
                      child: SizedBox(
                        width: 150,
                        height: 40,
                        child: ElevatedButton(
                            onPressed: _handleStart,
                            style: ElevatedButton.styleFrom(
                              elevation: 0,
                              backgroundColor:
                                  const Color.fromARGB(255, 48, 93, 48),
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 40, vertical: 3),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(
                                    10), // Rounded corners
                              ),
                            ),
                            child: Text(
                              "Start",
                              style: GoogleFonts.roboto(
                                  fontSize: 16, color: Colors.white),
                            )),
                      ),
                    )
                  : Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        SizedBox(
                          width: 150,
                          height: 40,
                          child: ElevatedButton(
                              onPressed: _handleCancel,
                              style: ElevatedButton.styleFrom(
                                elevation: 0,
                                backgroundColor: Colors.red,
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 40, vertical: 3),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(
                                      10), // Rounded corners
                                ),
                              ),
                              child: Text(
                                "Cancel",
                                style: GoogleFonts.roboto(
                                    fontSize: 16, color: Colors.white),
                              )),
                        ),
                        SizedBox(
                          width: 150,
                          height: 40,
                          child: ElevatedButton(
                              onPressed: () => showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return DualbuttonDialog(
                                        title:
                                            "End the trip and earn your rewards?",
                                        content: "We don't encourage cheating!",
                                        onConfirm: () {
                                          Navigator.of(context).pop();
                                          _handleEnd();
                                        },
                                        onCancel: () {
                                          Navigator.of(context).pop();
                                        });
                                  }),
                              style: ElevatedButton.styleFrom(
                                elevation: 0,
                                backgroundColor:
                                    const Color.fromARGB(255, 0, 255, 34),
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 40, vertical: 3),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(
                                      10), // Rounded corners
                                ),
                              ),
                              child: Text(
                                "End Trip",
                                style: GoogleFonts.roboto(
                                    fontSize: 16, color: Colors.white),
                              )),
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
