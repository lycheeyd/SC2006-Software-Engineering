import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/Pages/sign_up/signup_page.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class Loginpage extends StatefulWidget {
  const Loginpage({super.key});

  @override
  State<Loginpage> createState() => _LoginpageState();
}

class _LoginpageState extends State<Loginpage> {
  bool _wrongPW = false;
  bool _invalidEmail = false;
  bool _showFP = false;
  final TextEditingController _inputPassword = TextEditingController();
  final TextEditingController _inputEmail = TextEditingController();
  final String _password =
      "12345"; //should be retrived from database according to email
  void _handleLogin() {
    if (_password != _inputPassword.text ||
        _inputEmail.text != "test@gmail.com") {
      setState(() {
        {
          _wrongPW = true;
          _invalidEmail = false;
          _inputPassword.clear();
        }
      });
    } else {
      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => const PageNavigator(
                    startPage: 0,
                  )));
      _inputPassword.clear();
      setState(() {
        _wrongPW = false;
        _invalidEmail = false;
        _inputPassword.clear();
        _inputEmail.clear();
      });
    }
  }

  void _handleForgetPW() {
    setState(() {
      _showFP = false;
    });
  }

//hello
  void _handleSignUp() {
    Navigator.push(
        context, MaterialPageRoute(builder: (context) => const SignupPage()));
  }

  @override
  Widget build(BuildContext context) {
    final inputBorder = OutlineInputBorder(
        borderSide: BorderSide(color: Colors.grey.shade400),
        borderRadius: BorderRadius.circular(8));
    return Scaffold(
      appBar: AppBar(
        backgroundColor: !_showFP
            ? PrimaryColors.dullGreen
            : const Color.fromARGB(255, 63, 96, 63),
        foregroundColor: PrimaryColors.dullGreen,
      ),
      backgroundColor: PrimaryColors.dullGreen,
      body: SingleChildScrollView(
        child: Stack(
          children: [
            Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                // const SizedBox(
                //   height: 100,
                // ),
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
                                  controller: _inputEmail,
                                  textAlign: TextAlign.left,
                                  decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.symmetric(
                                        vertical: 5, horizontal: 15),
                                    hintText: "Enter Your Email",
                                    hintStyle: GoogleFonts.roboto(
                                        fontSize: 16,
                                        color: PrimaryColors.grey),
                                    enabledBorder: inputBorder,
                                    border: inputBorder,
                                    focusedBorder: inputBorder,
                                  )),
                            ),
                            if (_invalidEmail)
                              Align(
                                alignment: Alignment.topLeft,
                                child: Text(
                                  "Invalid Email!",
                                  style: GoogleFonts.roboto(
                                      fontSize: 14, color: Colors.red),
                                ),
                              )
                          ],
                        ),
                        const SizedBox(
                          height: 15,
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
                                  controller: _inputPassword,
                                  obscureText: true,
                                  textAlign: TextAlign.left,
                                  decoration: InputDecoration(
                                    contentPadding: const EdgeInsets.symmetric(
                                        vertical: 5, horizontal: 15),
                                    hintText: "Enter Your Password",
                                    hintStyle: GoogleFonts.roboto(
                                        fontSize: 16,
                                        color: PrimaryColors.grey),
                                    enabledBorder: inputBorder,
                                    border: inputBorder,
                                    focusedBorder: inputBorder,
                                  )),
                            ),
                            if (_wrongPW)
                              Align(
                                alignment: Alignment.topLeft,
                                child: Text(
                                  "Wrong password!",
                                  style: GoogleFonts.roboto(
                                      fontSize: 14, color: Colors.red),
                                ),
                              )
                          ],
                        ),
                        const SizedBox(height: 15),
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
                                    fontSize: 16, color: Colors.white),
                              )),
                        ),
                        // const SizedBox(
                        //   height: 20,
                        // ),
                        Align(
                          alignment: Alignment.centerLeft,
                          child: TextButton(
                              onPressed: () {
                                setState(() {
                                  _showFP = true;
                                  _wrongPW = false;
                                });
                              },
                              child: Text(
                                "Forgot password?",
                                style: GoogleFonts.roboto(
                                    decoration: TextDecoration.underline,
                                    fontSize: 14,
                                    color: Colors.black),
                              )),
                        ),
                        Divider(
                          height: 15,
                          thickness: 1,
                          color: PrimaryColors.grey,
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
            //Forgot password pop up screen
            _showFP
                ? SizedBox(
                    width: MediaQuery.of(context).size.width,
                    height: MediaQuery.of(context).size.height,
                    child: Container(
                      color: const Color.fromARGB(120, 0, 0, 0),
                      child: Column(
                        children: [
                          const SizedBox(
                            height: 170,
                          ),
                          Container(
                              decoration: BoxDecoration(
                                  color:
                                      const Color.fromARGB(255, 241, 241, 241),
                                  borderRadius: BorderRadius.circular(8)),
                              width: 348,
                              height: 220,
                              child: Padding(
                                padding: const EdgeInsets.symmetric(
                                    vertical: 16, horizontal: 25),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      "Please key in your email",
                                      style: GoogleFonts.roboto(
                                          color: PrimaryColors.black,
                                          fontWeight: FontWeight.bold,
                                          fontSize: 16),
                                    ),
                                    const SizedBox(
                                      height: 10,
                                    ),
                                    Text(
                                      "A temporary password would be sent to your email",
                                      style: GoogleFonts.roboto(
                                          color: Colors.grey.shade800,
                                          fontSize: 14),
                                    ),
                                    const SizedBox(
                                      height: 10,
                                    ),
                                    SizedBox(
                                        height: 40,
                                        child: TextField(
                                          decoration: InputDecoration(
                                            filled: true,
                                            fillColor: Colors.white,
                                            contentPadding:
                                                const EdgeInsets.symmetric(
                                                    vertical: 5,
                                                    horizontal: 15),
                                            hintText: "Enter Your Email",
                                            hintStyle: GoogleFonts.roboto(
                                                fontSize: 16,
                                                color: PrimaryColors.grey),
                                            enabledBorder: inputBorder,
                                            border: inputBorder,
                                            focusedBorder: inputBorder,
                                          ),
                                        )),
                                    const SizedBox(
                                      height: 15,
                                    ),
                                    Row(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.center,
                                      mainAxisAlignment:
                                          MainAxisAlignment.spaceBetween,
                                      children: [
                                        SizedBox(
                                          width: 140,
                                          height: 40,
                                          child: ElevatedButton(
                                              onPressed: () => setState(() {
                                                    _showFP = false;
                                                  }),
                                              style: ElevatedButton.styleFrom(
                                                elevation: 0,
                                                backgroundColor: Colors.white,
                                                padding:
                                                    const EdgeInsets.symmetric(
                                                        horizontal: 20,
                                                        vertical: 5),
                                                shape: RoundedRectangleBorder(
                                                  borderRadius:
                                                      BorderRadius.circular(
                                                          10), // Rounded corners
                                                ),
                                              ),
                                              child: Text(
                                                "Cancel",
                                                style: GoogleFonts.roboto(
                                                    fontSize: 16,
                                                    color: Colors.black),
                                              )),
                                        ),
                                        SizedBox(
                                          width: 140,
                                          height: 40,
                                          child: ElevatedButton(
                                              onPressed: _handleForgetPW,
                                              style: ElevatedButton.styleFrom(
                                                elevation: 0,
                                                backgroundColor: Colors.black,
                                                padding:
                                                    const EdgeInsets.symmetric(
                                                        horizontal: 20,
                                                        vertical: 5),
                                                shape: RoundedRectangleBorder(
                                                  borderRadius:
                                                      BorderRadius.circular(
                                                          10), // Rounded corners
                                                ),
                                              ),
                                              child: Text(
                                                "Confirm",
                                                style: GoogleFonts.roboto(
                                                    fontSize: 16,
                                                    color: Colors.white),
                                              )),
                                        )
                                      ],
                                    )
                                  ],
                                ),
                              )),
                        ],
                      ),
                    ),
                  )
                : Container(),
          ],
        ),
      ),
    );
  }
}
