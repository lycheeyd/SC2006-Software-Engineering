import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/common/dualbutton_dialog.dart';
import 'package:calowin/common/singlebutton_dialog.dart';
import 'package:calowin/control/friends_controller.dart';
import 'package:calowin/control/user_retriever.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/user_profile.dart';
import 'package:calowin/control/words2widget_converter.dart';
import 'package:flutter/material.dart';

class OtheruserPage extends StatefulWidget {
  final String? otherUserID;
  final String? userID;

  const OtheruserPage({super.key,this.userID,this.otherUserID});

  @override
  State<OtheruserPage> createState() => _OtheruserPageState();
}

class _OtheruserPageState extends State<OtheruserPage> {
  //define retrieve logic here
  late String? _userID;
  late String? _otherUserID;
  late UserProfile _profile = UserProfile(name: "NA", userID: "NA");
  late List<Image?> _badges = [];
  late UserStatus _userStatus;
  bool _userFound = false;
  final UserRetriever _userRetriever = UserRetriever();
  final FriendsController _friendsController = FriendsController();

  //Need to set the state of this user, such as requested or friend or pending for approve etc
  //currently only taking in the userid for testing
  @override
  void initState() {
    super.initState(); //change this to retrieve from database
    _userID = widget.userID;
    _otherUserID = widget.otherUserID;
    getUserProfile(_userID,_otherUserID);
    _userStatus = _profile.getStatus() ?? UserStatus.STRANGER;
    //print(_userStatus);
  }

  // to check for any change in the userid passed into this page
  @override
  void didUpdateWidget(OtheruserPage oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.otherUserID != oldWidget.otherUserID) {
      _otherUserID = widget.otherUserID;
      _userID = widget.userID; // Update the userID
      getUserProfile(_userID,_otherUserID);
      _userStatus = _profile.getStatus() ?? UserStatus.STRANGER;
      //print(_userStatus);
      //print("User ID passed: $_userID, OtherUserID passed: $_otherUserID");
      // Fetch the new profile
    }
  }

  //the setting user's state can be handled here
  Future<void> getUserProfile(String? user, String? otherUser) async {
    if(user == null  || otherUser == null) {
      //print("user id not passed");
      setState(() {
        _userFound = false;
    });
    return;
    }
    _profile = await _userRetriever.retrieveFriend(user, otherUser);
    //print("Profile of : ${_profile.getEmail()}");
    setState(() {
      _profile = _profile;
      _userStatus = _profile.getStatus() ?? UserStatus.STRANGER;
      if(_profile.getBadges().isNotEmpty)
      {
        _badges = [];
        for (int i = 0; i < _profile.getBadges().length; i++) {
          if (Words2widgetConverter.convert(_profile.getBadges()[i]) != null) {
            _badges
                .add(Words2widgetConverter.convert(_profile.getBadges()[i]));
          }
        }
        }
      _userFound = true;
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

  Future<void> _handleRequestFriend() async {
    bool success = await _friendsController.requestFriend(_userID!,_otherUserID!);
    setState(() {
      if(success){
        getUserProfile(_userID, _otherUserID);
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Success", content: "Request sent successfully", onConfirm: ()=>Navigator.pop(context));
            }); 
        _userStatus = UserStatus.REQUESTSENT;
      }
      else{
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Failed", content: "Failed to send request, please try again later", onConfirm: ()=>Navigator.pop(context));
            });
      }
    });
  }

  Future<void> _handleUnrequestFriend() async {
    bool success = await _friendsController.cancelRequest(_userID!,_otherUserID!);
    setState(() {
      if(success){
        getUserProfile(_userID, _otherUserID);
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Success", content: "Request cancelled successfully", onConfirm: ()=>Navigator.pop(context));
            }); 
        _userStatus = UserStatus.REQUESTSENT;
      }
      else{
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Failed", content: "Failed to cancel request, please try again later", onConfirm: ()=>Navigator.pop(context));
            });
      }
    });
  }

  Future<void> _handleRemoveFriend() async {
    bool success = await _friendsController.removeFriend(_userID!,_otherUserID!);
    setState(() {
      if(success){
        getUserProfile(_userID, _otherUserID);
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Success", content: "Friend removed successfully", onConfirm: ()=>Navigator.pop(context));
            }); 
        _userStatus = UserStatus.STRANGER;
      }
      else{
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Failed", content: "Failed to remove friend, please try again later", onConfirm: ()=>Navigator.pop(context));
            });
      }
    });
  }

  Future<void> _handleAccept() async {
    bool success = await _friendsController.acceptFriend(_userID!,_otherUserID!);
    setState(() {
      if(success){
        getUserProfile(_userID, _otherUserID);
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Success", content: "Friend added successfully", onConfirm: ()=>Navigator.pop(context));
            }); 
        _userStatus = UserStatus.FRIEND;
      }
      else{
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Failed", content: "Failed to add friend, please try again later", onConfirm: ()=>Navigator.pop(context));
            });
      }
    });
  }

  Future<void> _handleReject() async {
    bool success = await _friendsController.rejectFriend(_userID!,_otherUserID!);
    setState(() {
      if(success){
        getUserProfile(_userID, _otherUserID);
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Success", content: "Request rejected successfully", onConfirm: ()=>Navigator.pop(context));
            }); 
        _userStatus = UserStatus.STRANGER;
      }
      else{
        showDialog(
          context: context, 
          builder: (BuildContext context) {
            return SinglebuttonDialog(title: "Failed", content: "Failed to reject request, please try again later", onConfirm: ()=>Navigator.pop(context));
            });
      }
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
        case UserStatus.FRIEND:
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

        case UserStatus.STRANGER:
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

        case UserStatus.REQUESTRECIEVED:
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
        case UserStatus.REQUESTSENT:
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
          icon: const Icon(Icons.arrow_back),
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
