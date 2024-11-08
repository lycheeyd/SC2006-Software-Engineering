import 'package:calowin/Pages/friends/addfriends_page.dart';
import 'package:calowin/Pages/friends/friends_page.dart';
import 'package:calowin/Pages/otheruser_page.dart';
import 'package:calowin/Pages/profile/profile_page.dart';
import 'package:calowin/Pages/wellnesszone_page.dart';
import 'package:calowin/common/user_profile.dart';
import 'package:calowin/control/friends_controller.dart';
import 'package:calowin/control/notification_service.dart';
import 'package:calowin/control/user_retriever.dart';
import 'package:flutter/material.dart';
import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/Pages/rank_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:google_fonts/google_fonts.dart';

class PageNavigator extends StatefulWidget {
  final UserProfile profile;

  const PageNavigator({
    super.key,
    required this.profile,
    });

  @override
  State<PageNavigator> createState() => PageNavigatorState();
}

class PageNavigatorState extends State<PageNavigator> {
  //this is to set the page index
  int _currentIndex = 0;
  UserProfile _profile = UserProfile(name: "Error loading user", userID: "Error loading user");
  final FriendsController _friendsController = FriendsController();
  final UserRetriever _userRetriever = UserRetriever();

  //this is to set parameters to pass to the pages
  Map<String, dynamic>? _currentParams;

  late List<Widget Function(Map<String, dynamic>?)> _pages;

  List<UserProfile> listOfNotifications = [];

  bool _showNotifications =
      false; // State to track if notifications are visible
  bool _hasNoti = false;

  @override
  void initState() {
    super.initState();
    _profile = widget.profile;
    _pages = [
    (params) => MapcalcPage(
      targetName: params?['targetName'], targetLat: params?['targetLat'], targetLong: params?['targetLong'], profile: _profile,
    ),
    (params) => RankPage(
      key: UniqueKey(), // Assign a unique key to force rebuild
      userID:  _profile.getUserID(),),
    (params) => ProfilePage(profile: _profile),
    (params) => FriendsPage(
      key: UniqueKey(), // Assign a unique key to force rebuild
      userID:  _profile.getUserID()),
    (params) => const WellnessZonePage(),
    //below are all not available in navigation bar
    (params) => OtheruserPage(
          key: UniqueKey(), // Assign a unique key to force rebuild
          otherUserID: params?['otherUserID'], userID: params?['userID'], //passing the user's id to redirect
        ),
    (params) => AddfriendsPage(
      key: UniqueKey(), // Assign a unique key to force rebuild
      userID:  _profile.getUserID()),
  ];

  _getNotifications();
  }


  Future<void> _getSelf() async {
    _profile = await _userRetriever.retrieveSelf(_profile.getUserID());
    setState(() {
      _profile = _profile;
    });
  }

  void navigateToPage(int index, {Map<String, dynamic>? params}) {
    setState(() {
      _currentIndex = index;
      _currentParams = params; // Save parameters if needed
    });
    //print("Navigating to page $_currentIndex");
  }

  Future<void> _getNotifications() async {
    listOfNotifications = await _friendsController.retrieveRequesterList(_profile.getUserID());
    
      setState(() {
        if(listOfNotifications.isNotEmpty){
        _hasNoti = true;}
        else {_hasNoti = false;}
      });
  }

  void _toggleNotifications() {
    _getNotifications();
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
      navigateToPage(5, params: {"otherUserID": listOfNotifications[index].getUserID(), "userID": _profile.getUserID()});
      _toggleNotifications();
    });
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
                color: (_currentIndex < 5 ? _currentIndex : 3) == index
                    ? PrimaryColors.darkGreen
                    : lightgreen,
                borderRadius: BorderRadius.circular(20), // Rounded rectangle
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

    return GestureDetector(
      onTap: () => setState(() {
        {
          _showNotifications = false;
        }
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
                child: Stack(
                  children: [
                    IconButton(
                      color: _showNotifications ? Colors.white : Colors.black,
                      icon: const Icon(Icons.notifications),
                      onPressed: _toggleNotifications,
                    ),
                    if(_hasNoti)Align(
                      alignment: Alignment.topLeft,
                      child: Icon(Icons.circle, color: Colors.red,size: 15,),
                    )
                  ],
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
                      SizedBox(
                        height: 230,
                        child: ListView.builder(
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
                                    "${listOfNotifications[index].getName()} sent you a friend request",
                                    style: GoogleFonts.poppins(
                                        fontSize: 12,
                                        fontWeight: FontWeight.bold),
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
      ),
    );
  }
}
