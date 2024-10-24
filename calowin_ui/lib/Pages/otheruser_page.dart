import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/common/dualbutton_dialog.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/user_profile.dart';
import 'package:calowin/control/words2widget_converter.dart';
import 'package:flutter/material.dart';

//for setting this user's status relative to the current account
enum UserStatus { friend, requested, stranger, friendrequest }

class OtheruserPage extends StatefulWidget {
  final String? userID;

  const OtheruserPage({super.key, this.userID});

  @override
  State<OtheruserPage> createState() => _OtheruserPageState();
}

class _OtheruserPageState extends State<OtheruserPage> {
  //define retrieve logic here
  late String? _userID;
  late UserProfile? _profile;
  late List<Image?> _badges = [];
  late UserStatus _userStatus;
  late bool _userFound;

  //Need to set the state of this user, such as requested or friend or pending for approve etc
  //currently only taking in the userid for testing
  @override
  void initState() {
    super.initState();
    _userStatus = UserStatus.friend;
    _userID = widget.userID;
    getUserProfile(_userID);
  }

  //to check for any change in the userid passed into this page
  @override
  void didUpdateWidget(OtheruserPage oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.userID != oldWidget.userID) {
      _userStatus = UserStatus.friend;
      _userID = widget.userID; // Update the userID
      getUserProfile(_userID);
      // Fetch the new profile
    }
  }

  //the setting user's state can be handled here
  void getUserProfile(String? id) {
    setState(() {
      if (id == null) {
        print("User not found");
        _userFound = false;
        // A placeholder profile to prevent errors
        _profile = UserProfile(
            name: "Friend 1",
            email: "Friend@gmail.com",
            userID: id ?? "#000000",
            bio: "I\nAm\nYourFriend",
            weight: 80,
            carbonSaved: 4000,
            calorieBurn: 2300,
            badges: ["CalorieGold", "EcoBronze"]);
      } else {
        print("User found");
        _userFound = true;
        _userStatus = UserStatus.friend;
        _profile = UserProfile(
            name: "Friend 1",
            email: "Friend@gmail.com",
            userID: id,
            bio: "I\nAm\nYourFriend",
            weight: 80,
            carbonSaved: 4000,
            calorieBurn: 2300,
            badges: ["CalorieGold", "EcoBronze"]);
        _badges.clear(); // Clear previous badges before adding new ones
        for (int i = 0; i < _profile!.getBadges().length; i++) {
          if (Words2widgetConverter.convert(_profile!.getBadges()[i]) != null) {
            _badges
                .add(Words2widgetConverter.convert(_profile!.getBadges()[i]));
          }
        }
      }
    });
  }

  void _handleBack() {
    final pageNavigatorState =
        context.findAncestorStateOfType<PageNavigatorState>();
    //change here
    if (pageNavigatorState != null) {
      pageNavigatorState.navigateToPage(3); // Navigate to AddFriendsPage
    }
  }

  void _handleRequestFriend() {
    setState(() {
      _userStatus = UserStatus.requested;
    });
  }

  void _handleUnrequestFriend() {
    setState(() {
      _userStatus = UserStatus.stranger;
    });
  }

  void _handleRemoveFriend() {
    setState(() {
      _userStatus = UserStatus.stranger;
    });
  }

  void _handleAccept() {
    setState(() {
      _userStatus = UserStatus.friend;
    });
  }

  void _handleReject() {
    setState(() {
      _userStatus = UserStatus.stranger;
    });
  }

  Widget fieldBuilder(String title, String content) {
    return SizedBox(
      height: 47,
      width: 400,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
        child: Column(
          children: [
            Row(
              children: [
                SizedBox(
                  width: 100,
                  child: Text(
                    title,
                    style: const TextStyle(
                        fontStyle: FontStyle.italic, color: Colors.black),
                  ),
                ),
                const SizedBox(
                  width: 30,
                ),
                SizedBox(
                    width: 200,
                    child: Text(
                      content,
                      style: const TextStyle(color: Colors.black),
                    )),
              ],
            ),
            Divider(
              thickness: 0.3,
              color: Colors.grey.shade600,
            )
          ],
        ),
      ),
    );
  }

  Widget fieldInContainerBuilder(String title, String content, String unit) {
    return SizedBox(
      height: 35,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Align(
              alignment: Alignment.centerLeft,
              child: SizedBox(
                child: Text(
                  textAlign: TextAlign.start,
                  title,
                  style: const TextStyle(
                      fontStyle: FontStyle.italic, color: Colors.black),
                ),
              ),
            ),
            Align(
              alignment: Alignment.centerRight,
              child: SizedBox(
                  child: Text(
                textAlign: TextAlign.right,
                "$content $unit",
                style: const TextStyle(color: Colors.black),
              )),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    Widget getPage() {
      switch (_userStatus) {
        case UserStatus.friend:
          return Align(
            alignment: Alignment.bottomCenter,
            child: SizedBox(
              width: 150,
              height: 40,
              child: ElevatedButton(
                  onPressed: () => showDialog(
                      context: context,
                      builder: (BuildContext context) => DualbuttonDialog(
                          title: "Remove Friend?",
                          content:
                              "You will not see him on your leaderboard anymore",
                          onConfirm: () {
                            Navigator.of(context).pop();
                            _handleRemoveFriend();
                          },
                          onCancel: Navigator.of(context).pop)),
                  style: ElevatedButton.styleFrom(
                    elevation: 0,
                    backgroundColor: Colors.red,
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                    shape: RoundedRectangleBorder(
                      borderRadius:
                          BorderRadius.circular(10), // Rounded corners
                    ),
                  ),
                  child: Text(
                    "Remove Friend",
                    style:
                        GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                  )),
            ),
          );

        case UserStatus.stranger:
          return Align(
            alignment: Alignment.bottomCenter,
            child: SizedBox(
              height: 40,
              width: 150,
              child: ElevatedButton(
                  onPressed: _handleRequestFriend,
                  style: ElevatedButton.styleFrom(
                    elevation: 0,
                    backgroundColor: PrimaryColors.darkGreen,
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                    shape: RoundedRectangleBorder(
                      borderRadius:
                          BorderRadius.circular(10), // Rounded corners
                    ),
                  ),
                  child: Text(
                    "Request",
                    style:
                        GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                  )),
            ),
          );

        case UserStatus.friendrequest:
          return Row(
            crossAxisAlignment: CrossAxisAlignment.center,
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Align(
                alignment: Alignment.bottomLeft,
                child: SizedBox(
                  width: 150,
                  height: 40,
                  child: ElevatedButton(
                      onPressed: _handleReject,
                      style: ElevatedButton.styleFrom(
                        elevation: 0,
                        backgroundColor: Colors.red,
                        padding: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 5),
                        shape: RoundedRectangleBorder(
                          borderRadius:
                              BorderRadius.circular(10), // Rounded corners
                        ),
                      ),
                      child: Text(
                        "Reject",
                        style: GoogleFonts.roboto(
                            fontSize: 16, color: Colors.white),
                      )),
                ),
              ),
              Align(
                alignment: Alignment.bottomRight,
                child: SizedBox(
                  width: 150,
                  height: 40,
                  child: ElevatedButton(
                      onPressed: _handleAccept,
                      style: ElevatedButton.styleFrom(
                        elevation: 0,
                        backgroundColor: PrimaryColors.brightGreen,
                        padding: const EdgeInsets.symmetric(
                            horizontal: 10, vertical: 5),
                        shape: RoundedRectangleBorder(
                          borderRadius:
                              BorderRadius.circular(10), // Rounded corners
                        ),
                      ),
                      child: Text(
                        "Accept",
                        style: GoogleFonts.roboto(
                            fontSize: 16, color: Colors.white),
                      )),
                ),
              )
            ],
          );
        case UserStatus.requested:
          return Align(
            alignment: Alignment.bottomCenter,
            child: SizedBox(
              height: 40,
              width: 150,
              child: ElevatedButton(
                  onPressed: _handleUnrequestFriend,
                  style: ElevatedButton.styleFrom(
                    elevation: 0,
                    backgroundColor: PrimaryColors.darkGreen,
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                    shape: RoundedRectangleBorder(
                      borderRadius:
                          BorderRadius.circular(10), // Rounded corners
                    ),
                  ),
                  child: Text(
                    "Requested",
                    style:
                        GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                  )),
            ),
          ); // Replace with actual user ID logic
        default:
          return const Center(child: Text('Something went wrong'));
      }
    }

    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      appBar: AppBar(
        backgroundColor: PrimaryColors.dullGreen,
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: _handleBack,
        ),
      ),
      body: _userFound
          ? Padding(
              padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
              child: Column(
                children: [
                  fieldBuilder("Name", _profile!.getName()),
                  fieldBuilder("User ID", _profile!.getUserID()),
                  Padding(
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
                    child: SizedBox(
                      height: 120,
                      width: 400,
                      child: Column(
                        children: [
                          const Align(
                            alignment: Alignment.topLeft,
                            child: Text(
                              textAlign: TextAlign.left,
                              "Bio",
                              style:
                                  TextStyle(color: Colors.black, fontSize: 18),
                            ),
                          ),
                          Container(
                            height: 80,
                            width: 400,
                            decoration: BoxDecoration(
                                color: const Color.fromARGB(255, 233, 243, 233),
                                borderRadius: BorderRadius.circular(10)),
                            child: Padding(
                              padding: const EdgeInsets.symmetric(
                                  vertical: 10, horizontal: 20),
                              child: Text(_profile!.getBio(),
                                  style: PrimaryFonts.systemFont.copyWith(
                                      color: Colors.black, fontSize: 14)),
                            ),
                          )
                        ],
                      ),
                    ),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
                    child: SizedBox(
                      height: 130,
                      width: 400,
                      child: Column(
                        children: [
                          const Align(
                            alignment: Alignment.topLeft,
                            child: Text(
                              textAlign: TextAlign.left,
                              "Stats",
                              style:
                                  TextStyle(color: Colors.black, fontSize: 18),
                            ),
                          ),
                          Container(
                            height: 90,
                            decoration: BoxDecoration(
                                color: const Color.fromARGB(255, 153, 240, 152),
                                borderRadius: BorderRadius.circular(10)),
                            child: Padding(
                                padding: const EdgeInsets.symmetric(
                                    vertical: 10, horizontal: 10),
                                child: Column(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    fieldInContainerBuilder(
                                        "Total Carbon Saved",
                                        _profile!.getCarbonSaved().toString(),
                                        "g"),
                                    fieldInContainerBuilder(
                                        "Total Calorie Burned",
                                        _profile!.getCalorieBurn().toString(),
                                        "kcal")
                                  ],
                                )),
                          )
                        ],
                      ),
                    ),
                  ),
                  Padding(
                    padding:
                        const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
                    child: SizedBox(
                      height: 100,
                      width: 400,
                      child: Column(
                        children: [
                          const Align(
                            alignment: Alignment.topLeft,
                            child: Text(
                              textAlign: TextAlign.left,
                              "Badges",
                              style:
                                  TextStyle(color: Colors.black, fontSize: 18),
                            ),
                          ),
                          Container(
                            height: 60,
                            width: 400,
                            decoration: BoxDecoration(
                                color: const Color.fromARGB(255, 153, 240, 152),
                                borderRadius: BorderRadius.circular(10)),
                            child: Padding(
                                padding: const EdgeInsets.symmetric(
                                    vertical: 2, horizontal: 17),
                                child: ListView.builder(
                                  scrollDirection: Axis.horizontal,
                                  shrinkWrap: true,
                                  itemCount: _badges.length,
                                  itemBuilder: (context, index) {
                                    return Padding(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 4, horizontal: 5),
                                        child: SizedBox(
                                          height: 30,
                                          width: 30,
                                          child: _badges[index],
                                        ));
                                  },
                                )),
                          )
                        ],
                      ),
                    ),
                  ),
                  Expanded(
                    child: SizedBox(
                      child: Padding(
                          padding: const EdgeInsets.symmetric(
                              horizontal: 10, vertical: 2),
                          child: getPage()),
                    ),
                  )
                ],
              ),
            )
          : const Center(child: Text("User not found")),
    );
  }
}
