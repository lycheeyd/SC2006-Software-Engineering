import 'package:calowin/common/colors_and_fonts.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class Loginpage extends StatefulWidget {
  const Loginpage({super.key});

  @override
  State<Loginpage> createState() => _LoginpageState();
}

class _LoginpageState extends State<Loginpage> {
  bool _showFP = false;
  void _handleLogin() {}
  void _handleForgetPW() {
    setState(() {
      _showFP = true;
    });
  }

  void _handleSignUp() {}

  @override
  Widget build(BuildContext context) {
    final inputBorder = OutlineInputBorder(
        borderSide: BorderSide(color: Colors.grey.shade400),
        borderRadius: BorderRadius.circular(8));
    return Scaffold(
      backgroundColor: PrimaryColors.dullGreen,
      body: SingleChildScrollView(
        child: Stack(
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const SizedBox(
                  height: 100,
                ),
                Center(
                  child: Image.asset(
                    'assets/images/CalowinNoBackground.png',
                    width: 150,
                    height: 150,
                  ),
                ),
                Text(
                  "CaloWin",
                  style: PrimaryFonts.logoFont,
                ),
                Text(
                  "Crush Your Calories, Conquer Your Goals!",
                  style: GoogleFonts.poppins(
                      color: Colors.white,
                      fontSize: 12,
                      fontWeight: FontWeight.bold),
                ),
                const SizedBox(
                  height: 30,
                ),
                Container(
                  width: 320,
                  height: 400,
                  decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(8),
                      border:
                          Border.all(color: Colors.grey.shade200, width: 1)),
                  child: Padding(
                    padding: const EdgeInsets.all(25),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        Column(
                          children: [
                            Align(
                              alignment: Alignment.topLeft,
                              child: Text(
                                "Email",
                                style: GoogleFonts.roboto(fontSize: 16),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            const SizedBox(height: 5),
                            SizedBox(
                              height: 40,
                              child: TextField(
                                  textAlign: TextAlign.left,
                                  decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.symmetric(
                                        vertical: 5, horizontal: 15),
                                    hintText: "Enter Your Email",
                                    hintStyle: TextStyle(
                                        color: Colors.grey.shade400,
                                        fontSize: 16),
                                    enabledBorder: inputBorder,
                                    border: inputBorder,
                                    focusedBorder: inputBorder,
                                  )),
                            ),
                          ],
                        ),
                        const SizedBox(
                          height: 20,
                        ),
                        Column(
                          children: [
                            Align(
                              alignment: Alignment.topLeft,
                              child: Text(
                                "Password",
                                style: GoogleFonts.roboto(fontSize: 16),
                                textAlign: TextAlign.left,
                              ),
                            ),
                            const SizedBox(height: 5),
                            SizedBox(
                              height: 40,
                              child: TextField(
                                  textAlign: TextAlign.left,
                                  decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.symmetric(
                                        vertical: 5, horizontal: 15),
                                    hintText: "Enter Your Password",
                                    hintStyle: TextStyle(
                                        color: Colors.grey.shade400,
                                        fontSize: 16),
                                    enabledBorder: inputBorder,
                                    border: inputBorder,
                                    focusedBorder: inputBorder,
                                  )),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20),
                        SizedBox(
                          width: 272,
                          height: 45,
                          child: ElevatedButton(
                              onPressed: _handleLogin,
                              style: ElevatedButton.styleFrom(
                                backgroundColor:
                                    const Color.fromARGB(255, 48, 93, 48),
                                padding: const EdgeInsets.symmetric(
                                    horizontal: 40, vertical: 12),
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(
                                      10), // Rounded corners
                                ),
                              ),
                              child: Text(
                                "Sign In",
                                style: GoogleFonts.roboto(
                                    fontSize: 14, color: Colors.white),
                              )),
                        ),
                        // const SizedBox(
                        //   height: 20,
                        // ),
                        Align(
                          alignment: Alignment.centerLeft,
                          child: TextButton(
                              onPressed: _handleForgetPW,
                              child: const Text(
                                "Forgot password?",
                                style: TextStyle(
                                    decoration: TextDecoration.underline,
                                    color: Colors.black),
                              )),
                        ),
                        Divider(
                          height: 15,
                          thickness: 1,
                          color: Colors.grey.shade400,
                        ),
                        Row(
                          children: [
                            const Align(
                                alignment: Alignment.centerLeft,
                                child: Text("Not a member yet?")),
                            TextButton(
                                onPressed: _handleSignUp,
                                child: const Text(
                                  "Sign Up",
                                  style: TextStyle(color: Colors.orange),
                                ))
                          ],
                        )
                      ],
                    ),
                  ),
                )
              ],
            ),
            //create a grey overlay which shows when forgot password is pressed
            _showFP
                ? SizedBox(
                    width: MediaQuery.of(context).size.width,
                    height: MediaQuery.of(context).size.height,
                    child: Container(
                      color: const Color.fromARGB(120, 0, 0, 0),
                    ),
                  )
                : Container(),
          ],
        ),
      ),
    );
  }
}
