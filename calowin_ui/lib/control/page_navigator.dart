import 'package:calowin/Pages/friends/addfriends_page.dart';
import 'package:calowin/Pages/friends/friends_page.dart';
import 'package:calowin/Pages/otheruser_page.dart';
import 'package:calowin/Pages/profile/profile_page.dart';
import 'package:calowin/Pages/wellnesszone_page.dart';
import 'package:flutter/material.dart';
import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/Pages/wellness_page.dart';
import 'package:calowin/Pages/rank_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:google_fonts/google_fonts.dart';
<<<<<<< Updated upstream
import 'package:calowin/control/notification_service.dart';
import 'package:logger/logger.dart';

final logger = Logger();

=======
import 'package:calowin/control/notification_service.dart'; // Import NotificationService
>>>>>>> Stashed changes

class PageNavigator extends StatefulWidget {
  const PageNavigator({super.key});

  @override
  State<PageNavigator> createState() => PageNavigatorState();
}

class PageNavigatorState extends State<PageNavigator> {
  // Set the page index
  int _currentIndex = 0;

  // Set parameters to pass to the pages
  Map<String, dynamic>? _currentParams;
  bool _showNotifications = false;
  List<String> listOfNotifications = [];
  //final NotificationService notificationService = NotificationService();
  final List<Widget Function(Map<String, dynamic>?)> _pages = [
    (params) => const MapcalcPage(),
    (params) => const RankPage(),
    (params) => const ProfilePage(),
    (params) => const FriendsPage(),
    (params) => const WellnessZonePage(),
    // Below are all not available in the navigation bar
    (params) => OtheruserPage(
          userID: params?['userID'], // Passing the user's id to redirect
        ),
    (params) => const AddfriendsPage(),
  ];

<<<<<<< Updated upstream
  /*List<String> listOfNotifications = <String>[
    "John sent a Friend Request",
    "Notification 2",
    "Notification 3",
    "Notification 4",
    "Notification 4",
    "Notification 4",
    "Notification 4",
    "Notification 4",
    "Notification 4",
    "Notification 4",
    "Notification 4",
  ];*/
  Future<void> _loadNotifications() async {
  try {
    String userId = "00000001";
    NotificationService notificationService = NotificationService(); // Create an instance
    List<String> notifications = await notificationService.fetchFriendRequests(userId);
    setState(() {
      listOfNotifications = notifications;
    });
  } catch (e, stackTrace) {
    logger.e("Error loading notifications", e, stackTrace);
  }
}


  
=======
  List<String> listOfNotifications = [];
  bool _showNotifications = false; // Track if notifications are visible

  @override
  void initState() {
    super.initState();
    _loadNotifications(); // Load notifications when the widget is initialized
  }

  Future<void> _loadNotifications() async {
    try {
      String userId = "0000001"; // Example user ID; replace with actual user ID if needed
      NotificationService notificationService = NotificationService();
      List<String> notifications = await notificationService.fetchFriendRequests(userId);
      setState(() {
        listOfNotifications = notifications;
      });
    } catch (e) {
      print("Error loading notifications: $e");
    }
  }
>>>>>>> Stashed changes

  void navigateToPage(int index, {Map<String, dynamic>? params}) {
    setState(() {
      _currentIndex = index;
      _currentParams = params;
    });
<<<<<<< Updated upstream
    print("Navigating to page $_currentIndex");
=======
>>>>>>> Stashed changes
  }

  void _toggleNotifications() {
    setState(() {
      _showNotifications = !_showNotifications;
    });
    if (_showNotifications) {
      _loadNotifications(); // Load notifications when opened
    }
  }

  void _onItemTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  void _handleNotificationTap(int index) {
<<<<<<< Updated upstream
    setState(() {
      navigateToPage(5, params: {"userID": "00000001"});
      _toggleNotifications();
      //index should be passed to the profile page to be able to open the profile page of the person
      //alternately can just open the friend request page
    });
=======
    navigateToPage(5, params: {"userID": "888888"}); // Example user ID; modify as needed
    _toggleNotifications();
>>>>>>> Stashed changes
  }

  // Build custom navigation button decorations
  Widget _buildBottomNavItem(IconData icon, int index, String label) {
    Color lightgreen = const Color.fromARGB(255, 197, 251, 196);
    double bottomNavFontSize = 8;
    return GestureDetector(
      onTap: () => _onItemTapped(index),
      child: Container(
        width: 60,
        padding: const EdgeInsets.symmetric(horizontal: 2, vertical: 5),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Container(
              height: 35,
              width: 80,
              decoration: BoxDecoration(
                color: (_currentIndex < 5 ? _currentIndex : 3) == index
                    ? PrimaryColors.darkGreen
                    : lightgreen,
                borderRadius: BorderRadius.circular(20),
              ),
              child: Icon(
                icon,
                color: (_currentIndex < 5 ? _currentIndex : 3) == index
                    ? Colors.white
                    : Colors.black,
              ),
            ),
            const SizedBox(height: 4),
            Text(
              label,
              style: TextStyle(color: Colors.black, fontSize: bottomNavFontSize),
            ),
            const SizedBox(height: 10),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    Color lightgreen = const Color.fromARGB(255, 197, 251, 196);

    return GestureDetector(
      onTap: () => setState(() {
        _showNotifications = false;
      }),
      child: Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
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
                  child: Image.asset('assets/images/CalowinNoBackground.png',
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
              padding: const EdgeInsets.only(right: 5),
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
        body: Stack(children: [
          IndexedStack(
            index: _currentIndex,
            children: _pages.map((builder) => builder(_currentParams)).toList(),
          ),
          if (_showNotifications)
            Positioned(
              top: 5,
              right: 10,
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
                      SizedBox(
                        height: 230,
                        child: ListView.builder(
                          itemCount: listOfNotifications.length,
                          itemBuilder: (context, index) {
                            return Padding(
                              padding: const EdgeInsets.symmetric(vertical: 4, horizontal: 5),
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
                        ),
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
            color: lightgreen,
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
      ),
    );
  }
}