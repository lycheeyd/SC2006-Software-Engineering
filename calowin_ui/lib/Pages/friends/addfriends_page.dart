import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class AddfriendsPage extends StatefulWidget {
  const AddfriendsPage({super.key});

  @override
  State<AddfriendsPage> createState() => _AddfriendsPageState();
}

class _AddfriendsPageState extends State<AddfriendsPage> {
  List<String> _searchList = [];
  List<String> _friendRequests = ['Mia', 'Khalifa', 'Cody'];

  void _handleBack() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(3); // Navigate to AddFriendsPage
    }
  }

  void _handleSearch() {
    setState(() {
      _searchList = ['Alex', 'Derick', 'Bob'];
    });
  }

  void _onSearchItemTap() {}

  void _onRequestTap() {}

  void _handleAccept() {}

  void _handleReject() {}

  Widget _buildSearchListItem(int index, String name) {
    Color tileColor = const Color.fromARGB(255, 214, 241, 214);
    TextStyle fontStyle =
        GoogleFonts.aBeeZee(fontSize: 16, fontWeight: FontWeight.bold);
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 0),
      child: Container(
        decoration: BoxDecoration(
          color: tileColor,
          boxShadow: [
            BoxShadow(
              color: Colors.black.withOpacity(0.2),
              blurRadius: 6,
              offset: const Offset(0, 4),
            ),
          ],
        ),
        child: ListTile(
          onTap: () => _onSearchItemTap(),
          leading: const Icon(
            Icons.person,
            size: 25,
          ),
          title: Text(
            name,
            style: fontStyle,
          ),
        ),
      ),
    );
  }

  Widget _buildFriendRequests(int index, String name) {
    Color tileColor = const Color.fromARGB(255, 214, 241, 214);
    TextStyle fontStyle =
        GoogleFonts.aBeeZee(fontSize: 16, fontWeight: FontWeight.bold);
    return GestureDetector(
      onTap: _onRequestTap,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Container(
          height: 90,
          width: 400,
          decoration: BoxDecoration(
            color: tileColor,
            boxShadow: [
              BoxShadow(
                color: Colors.black.withOpacity(0.2),
                blurRadius: 6,
                offset: const Offset(0, 4),
              ),
            ],
          ),
          child: Padding(
            padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 15),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const Icon(
                      Icons.person_add,
                      size: 25,
                    ),
                    const SizedBox(
                      width: 10,
                    ),
                    Text(
                      name,
                      style: fontStyle,
                    ),
                  ],
                ),
                const SizedBox(height: 15),
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Align(
                      alignment: Alignment.bottomRight,
                      child: SizedBox(
                        width: 140,
                        height: 27,
                        child: ElevatedButton(
                          onPressed: _handleAccept,
                          style: ElevatedButton.styleFrom(
                            elevation: 0,
                            backgroundColor: PrimaryColors.brightGreen,
                            padding: const EdgeInsets.symmetric(horizontal: 10),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                          ),
                          child: Text(
                            "Accept",
                            style: GoogleFonts.roboto(
                                fontSize: 13, color: Colors.white),
                          ),
                        ),
                      ),
                    ),
                    Align(
                      alignment: Alignment.bottomLeft,
                      child: SizedBox(
                        width: 140,
                        height: 27,
                        child: ElevatedButton(
                          onPressed: _handleReject,
                          style: ElevatedButton.styleFrom(
                            elevation: 0,
                            backgroundColor: Colors.red,
                            padding: const EdgeInsets.symmetric(horizontal: 10),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(10),
                            ),
                          ),
                          child: Text(
                            "Reject",
                            style: GoogleFonts.roboto(
                                fontSize: 13, color: Colors.white),
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  final TextEditingController _searchController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: PrimaryColors.dullGreen,
      appBar: AppBar(
        backgroundColor: PrimaryColors.dullGreen,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: _handleBack,
        ),
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
        child: Column(
          children: [
            Align(
              alignment: Alignment.topLeft,
              child: Text(
                "Add Friends!",
                style: GoogleFonts.poppins(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 22,
                ),
              ),
            ),
            Container(
              height: 45,
              padding:
                  const EdgeInsets.symmetric(horizontal: 10.0, vertical: 0),
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(15.0),
              ),
              child: TextField(
                controller: _searchController,
                decoration: InputDecoration(
                  hintText: "Search for your friends!",
                  hintStyle: const TextStyle(fontSize: 13, color: Colors.grey),
                  border: InputBorder.none,
                  suffixIcon: IconButton(
                    icon: const Icon(Icons.search),
                    onPressed: () {
                      _handleSearch();
                    },
                  ),
                ),
              ),
            ),
            const SizedBox(height: 30),

            // Search List
            Column(
              children: _searchList.isEmpty
                  ? [
                      const SizedBox()
                    ] // Return an empty widget if no search results
                  : List.generate(
                      _searchList.length,
                      (index) =>
                          _buildSearchListItem(index, _searchList[index]),
                    ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(vertical: 10),
              child: Divider(
                color: Colors.grey.shade400,
                thickness: 2,
                height: 10,
              ),
            ),

            Align(
              alignment: Alignment.topLeft,
              child: Text(
                "Friend Requests",
                style: GoogleFonts.poppins(
                  color: Colors.white,
                  fontWeight: FontWeight.bold,
                  fontSize: 22,
                ),
              ),
            ),

            // Friend Requests List
            Column(
              children: _friendRequests.isEmpty
                  ? [
                      const SizedBox()
                    ] // Return an empty widget if no friend requests
                  : List.generate(
                      _friendRequests.length,
                      (index) =>
                          _buildFriendRequests(index, _friendRequests[index]),
                    ),
            ),
          ],
        ),
      ),
    );
  }
}
