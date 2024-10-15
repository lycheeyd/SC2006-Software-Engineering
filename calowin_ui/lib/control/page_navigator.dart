import 'package:flutter/material.dart';
import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';

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
    const FriendPage(),
    const ProfilePage(),
    const NotificationPage(),
    const SettingsPage(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _currentIndex = index;
    });
  }

  @override
  void initState() {
    super.initState();
    _currentIndex = widget.startPage;
  }

  @override
  Widget build(BuildContext context) {
    Color lightgreen = const Color.fromARGB(255, 197, 251, 196);

    return Scaffold(
      //extendBodyBehindAppBar: true,
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
              )
            ],
          ),
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.notifications),
            onPressed: () {
              showDialog(
                context: context,
                builder: (context) => const AlertDialog(
                  content: SizedBox(
                    height: 200,
                    width: 300,
                    child: Column(
                      children: [
                        Text("Notification 1"),
                        Text("Notification 2"),
                      ],
                    ),
                  ),
                ),
              );
            },
          ),
        ],
      ),
      body: IndexedStack(
        index: _currentIndex,
        children: _pages,
      ),
      bottomNavigationBar: Theme(
        data: Theme.of(context).copyWith(canvasColor: lightgreen),
        child: BottomNavigationBar(
          backgroundColor: Colors.red,
          selectedItemColor: Colors.black,
          unselectedItemColor: Colors.white,
          currentIndex: _currentIndex,
          onTap: _onItemTapped,
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.map),
              label: 'Map',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.people),
              label: 'Friend',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.person),
              label: 'Profile',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.notifications),
              label: 'Notifications',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.settings),
              label: 'Settings',
            ),
          ],
        ),
      ),
    );
  }
}

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

class NotificationPage extends StatelessWidget {
  const NotificationPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Notification Page'));
  }
}

class SettingsPage extends StatelessWidget {
  const SettingsPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text('Settings Page'));
  }
}
