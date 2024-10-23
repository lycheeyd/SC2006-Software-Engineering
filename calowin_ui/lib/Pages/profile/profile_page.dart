import 'package:calowin/Pages/profile/editprofile_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/user_profile.dart';
import 'package:calowin/control/words2widget_converter.dart';
import 'package:flutter/material.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({super.key});

  @override
  State<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  //define retrieve logic here
  late final UserProfile _profile;
  late final List<Image?> _badges = [];

  @override
  void initState() {
    super.initState();
    getUserProfile();
  }

  void getUserProfile() {
    _profile = UserProfile(
        name: "Eric Koh",
        email: "Eric123@gmail.com",
        userID: "#044612",
        bio: "012345678901234567890123456789",
        weight: 80,
        carbonSaved: 4000,
        calorieBurn: 2300,
        badges: ["CalorieGold", "EcoBronze"]);
    for (int i = 0; i < _profile.getBadges().length; i++) {
      if (Words2widgetConverter.convert(_profile.getBadges()[i]) != null) {
        _badges.add(Words2widgetConverter.convert(_profile.getBadges()[i]));
      }
    }
  }

  void _handleEditProfile() {
    Navigator.push(context,
        MaterialPageRoute(builder: (context) => const EditprofilePage()));
  }

  void _handleLogOut() {}

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
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: Padding(
        padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 10),
        child: Column(
          children: [
            fieldBuilder("Name", _profile.getName()),
            fieldBuilder("User ID", _profile.getUserID()),
            fieldBuilder("Email", _profile.getEmail() ?? "Error Retrieving"),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
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
                        style: TextStyle(color: Colors.black, fontSize: 18),
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
                        child: Text(_profile.getBio(),
                            style: PrimaryFonts.systemFont
                                .copyWith(color: Colors.black, fontSize: 14)),
                      ),
                    )
                  ],
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
              child: SizedBox(
                height: 170,
                width: 400,
                child: Column(
                  children: [
                    const Align(
                      alignment: Alignment.topLeft,
                      child: Text(
                        textAlign: TextAlign.left,
                        "Stats",
                        style: TextStyle(color: Colors.black, fontSize: 18),
                      ),
                    ),
                    Container(
                      height: 130,
                      decoration: BoxDecoration(
                          color: const Color.fromARGB(255, 153, 240, 152),
                          borderRadius: BorderRadius.circular(10)),
                      child: Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 10, horizontal: 10),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              fieldInContainerBuilder("Weight",
                                  _profile.getWeight().toString(), "kg"),
                              fieldInContainerBuilder("Total Carbon Saved",
                                  _profile.getCarbonSaved().toString(), "g"),
                              fieldInContainerBuilder("Total Calorie Burned",
                                  _profile.getCalorieBurn().toString(), "kcal")
                            ],
                          )),
                    )
                  ],
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
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
                        style: TextStyle(color: Colors.black, fontSize: 18),
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
                              vertical: 2, horizontal: 2),
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
                  padding:
                      const EdgeInsets.symmetric(horizontal: 10, vertical: 2),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Align(
                        alignment: Alignment.bottomLeft,
                        child: SizedBox(
                          width: 150,
                          height: 40,
                          child: ElevatedButton(
                              onPressed: _handleEditProfile,
                              style: ElevatedButton.styleFrom(
                                elevation: 0,
                                backgroundColor: PrimaryColors.darkGreen,
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 10, vertical: 5),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(
                                      10), // Rounded corners
                                ),
                              ),
                              child: Text(
                                "Edit Profile",
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
                              onPressed: _handleLogOut,
                              style: ElevatedButton.styleFrom(
                                elevation: 0,
                                backgroundColor: Colors.red,
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 10, vertical: 5),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(
                                      10), // Rounded corners
                                ),
                              ),
                              child: Text(
                                "Log Out",
                                style: GoogleFonts.roboto(
                                    fontSize: 16, color: Colors.white),
                              )),
                        ),
                      )
                    ],
                  ),
                ),
              ),
            )
          ],
        ),
      ),
    );
  }
}
