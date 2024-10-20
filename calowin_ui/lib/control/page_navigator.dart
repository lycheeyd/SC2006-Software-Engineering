import 'package:flutter/material.dart';
import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:flutter/rendering.dart';
import 'package:google_fonts/google_fonts.dart';

class PageNavigator extends StatefulWidget {
  final int startPage;

  const PageNavigator({super.key, required this.startPage});

  @override
  State<PageNavigator> createState() => _PageNavigatorState();
}

class _PageNavigatorState extends State<PageNavigator> {
  int _currentIndex = 0;
  final List<Widget> _pages = [
    const MapcalcPage(),
    const RankPage(),
    const ProfilePage(),
    const FriendPage(),
    const WellnessPage(),
  ];

  List<String> listOfNotifications = <String>[
    "John sent a Friend Request",
    "Notification 2",
    "Notification 3"
  ];

  bool _showNotifications =
      false; // State to track if notifications are visible

  void _toggleNotifications() {
    setState(() {
      _showNotifications = !_showNotifications;
    });
  }

  void _onItemTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _handleNotificationTap(int index) {
    setState(() {
      _currentIndex = 3;
      _toggleNotifications();
      //index should be passed to the profile page to be able to open the profile page of the person
      //alternately can just open the friend request page
    });
  }

  @override
  void initState() {
    super.initState();
    _currentIndex = widget.startPage;
  }

  //to build our custom navigation button decorations
  Widget _buildBottomNavItem(IconData icon, int index, String label) {
    Color lightgreen = const Color.fromARGB(255, 197, 251, 196);
    double bottomNavFontSize = 8;
    //label.length > 6 ? (label.length > 7 ? 6 : 7.5) : 8;
    return GestureDetector(
      onTap: () => _onItemTapped(index),
      child: Container(
        width: 60,
        padding: const EdgeInsets.symmetric(
            horizontal: 2, vertical: 5), // Add padding for better visuals
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              height: 35,
              width: 80,
              decoration: BoxDecoration(
                color: _currentIndex == index
                    ? PrimaryColors.darkGreen
                    : lightgreen,
                borderRadius: BorderRadius.circular(20), // Rounded rectangle
              ),
              child: Icon(
                icon,
                color: _currentIndex == index ? Colors.white : Colors.black,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              label,
              style:
                  TextStyle(color: Colors.black, fontSize: bottomNavFontSize),
            ),
            const SizedBox(height: 10), //elevate from the bottom
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    Color lightgreen = const Color.fromARGB(255, 197, 251, 196);

    return Scaffold(
      //extendBodyBehindAppBar: true,
      appBar: PreferredSize(
        preferredSize: const Size.fromHeight(60),
        child: Stack(
          children: [
            AppBar(
              leadingWidth: 300,
              toolbarHeight: 60,
              backgroundColor: lightgreen,
              leading: Padding(
                padding: const EdgeInsets.only(left: 8, bottom: 5),
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    SizedBox(
                      height: 50,
                      width: 50,
                      child: Image.asset(
                          'assets/images/CalowinNoBackground.png',
                          fit: BoxFit.contain),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(top: 17),
                      child: Text(
                        "CaloWin",
                        style: PrimaryFonts.logoFont.copyWith(fontSize: 27),
                      ),
                    ),
                  ],
                ),
              ),
              actions: [
                Padding(
                  padding: const EdgeInsets.only(
                    right: 5,
                  ),
                  child: Container(
                    height: 40,
                    width: 40,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: _showNotifications ? Colors.black : lightgreen,
                    ),
                    child: IconButton(
                      color: _showNotifications ? Colors.white : Colors.black,
                      icon: const Icon(Icons.notifications),
                      onPressed: _toggleNotifications,
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
      body: Stack(children: [
        IndexedStack(
          index: _currentIndex,
          children: _pages,
        ),
        // this is for the notification center
        if (_showNotifications)
          Positioned(
            top: 5, // Adjust position to appear just below the AppBar
            right: 10, // Position near the bell icon
            child: Material(
              elevation: 4,
              borderRadius: BorderRadius.circular(8),
              color: Colors.white,
              child: Container(
                width: 250,
                padding: const EdgeInsets.all(10),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text(
                          'Notifications',
                          style: GoogleFonts.poppins(
                              fontWeight: FontWeight.bold, fontSize: 20),
                        ),
                        IconButton(
                          icon: const Icon(Icons.close, size: 18),
                          onPressed: _toggleNotifications,
                        ),
                      ],
                    ),
                    const Divider(),
                    ListView.builder(
                      scrollDirection: Axis.vertical,
                      shrinkWrap: true,
                      itemCount: listOfNotifications.length,
                      itemBuilder: (context, index) {
                        return Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 4, horizontal: 5),
                          child: Container(
                            decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(10),
                                color: Colors.grey.shade300),
                            child: ListTile(
                              onTap: () => _handleNotificationTap(index),
                              title: Text(
                                listOfNotifications[index],
                                style: GoogleFonts.poppins(
                                    fontSize: 12, fontWeight: FontWeight.bold),
                              ),
                            ),
                          ),
                        );
                      },
                    )
                  ],
                ),
              ),
            ),
          ),
      ]),
      bottomNavigationBar: Theme(
        data: Theme.of(context).copyWith(canvasColor: lightgreen),
        child: Container(
          color:
              lightgreen, // Optional: If you want a background for the entire bottom nav bar
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              _buildBottomNavItem(Icons.map_outlined, 0, 'Map'),
              _buildBottomNavItem(Icons.star_outline_outlined, 1, 'Rank'),
              _buildBottomNavItem(Icons.person_outline, 2, 'Profile'),
              _buildBottomNavItem(Icons.people_outline, 3, 'Friends'),
              _buildBottomNavItem(Icons.eco_outlined, 4, 'Wellness'),
            ],
          ),
        ),
      ),
    );
  }
}

//temporary placeholders for the pages

class FriendPage extends StatelessWidget {
  const FriendPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Friend Page'));
  }
}

class ProfilePage extends StatelessWidget {
  const ProfilePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Profile Page'));
  }
}

class RankPage extends StatelessWidget {
  const RankPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Rank Page'));
  }
}

class WellnessPage extends StatelessWidget {
  const WellnessPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Wellness Page'));
  }
}
