import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class FriendsPage extends StatefulWidget {
  const FriendsPage({super.key});

  @override
  State<FriendsPage> createState() => _FriendsPageState();
}

class _FriendsPageState extends State<FriendsPage> {
  final List<String> _friendlist = [
    'Alex',
    'Derick',
    'Caleb',
    'Gray',
    'Bob',
    'Tom',
    'Alan',
    'Joyce'
  ];

  //handle the loading of friend list
  void _getFriends() {}

  //handle the redirection from notification and list
  void _redirectToFriend() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    //change here
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(5,
          params: {'userID': "334455"}); // Navigate to AddFriendsPage
    }
  }

  void _redirectToUser() {}

  //handle redirection to add friends page
  void _redirectToAddFriendsPage() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    //change here
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(6); // Navigate to AddFriendsPage
    }
  }

  //handle when friend is tapped
  void _onListItemTap() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    //change here
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(5,
          params: {'userID': "334455"}); // Navigate to AddFriendsPage
    }
  }

  Widget _buildListItem(int index, String name) {
    Color tileColor = const Color.fromARGB(255, 214, 241, 214);
    TextStyle fontStyle =
        GoogleFonts.aBeeZee(fontSize: 16, fontWeight: FontWeight.bold);
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 0),
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
          onTap: () => _onListItemTap(),
          leading: const Icon(
            Icons.person,
            size: 25,
          ),
          title: Text(
            name,
            style: fontStyle,
          ),
          trailing: SizedBox(
            width: 80,
            height: 35,
            child: ElevatedButton(
                onPressed: _onListItemTap,
                style: ElevatedButton.styleFrom(
                  elevation: 0,
                  backgroundColor: PrimaryColors.darkGreen,
                  padding:
                      const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10), // Rounded corners
                  ),
                ),
                child: Text(
                  "Profile",
                  style: GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                )),
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
        toolbarHeight: 20,
        automaticallyImplyLeading: false,
        backgroundColor: PrimaryColors.dullGreen,
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 10),
          child: Column(
            children: [
              Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "Friends",
                  style: GoogleFonts.poppins(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 22),
                ),
              ),
              SizedBox(
                height: 500,
                child: ListView.builder(
                  scrollDirection: Axis.vertical,
                  shrinkWrap: true,
                  itemCount: _friendlist.length,
                  itemBuilder: (context, index) {
                    return _buildListItem(index, _friendlist[index]);
                  },
                ),
              ),
              Divider(
                indent: 20,
                endIndent: 20,
                color: Colors.grey.shade400,
                thickness: 2,
              ),
              const SizedBox(
                height: 30,
              ),
              Center(
                child: SizedBox(
                  width: 150,
                  height: 40,
                  child: ElevatedButton(
                      onPressed: _redirectToAddFriendsPage,
                      style: ElevatedButton.styleFrom(
                        elevation: 0,
                        backgroundColor: PrimaryColors.darkGreen,
                        padding: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 5),
                        shape: RoundedRectangleBorder(
                          borderRadius:
                              BorderRadius.circular(10), // Rounded corners
                        ),
                      ),
                      child: Text(
                        "Add Friends",
                        style: GoogleFonts.roboto(
                            fontSize: 16, color: Colors.white),
                      )),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
