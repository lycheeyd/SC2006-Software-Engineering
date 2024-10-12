import 'package:calowin/Pages/sign_up/input_field.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class SignupPage extends StatefulWidget {
  const SignupPage({super.key});

  @override
  State<SignupPage> createState() => _SignupPageState();
}

class _SignupPageState extends State<SignupPage> {
  final TextEditingController _inputPassword = TextEditingController();
  final TextEditingController _inputEmail = TextEditingController();
  final TextEditingController _inputOTP = TextEditingController();
  bool _invalidPassword = false;
  bool _invalidEmail = false;
  final InputBorder inputBorder = UnderlineInputBorder(
      borderRadius: BorderRadius.circular(8), borderSide: BorderSide.none);

  void _checkEmail() {
    setState(() {
      _invalidEmail = true; //define the conditions later
    });
  }

  void _checkPassword() {
    setState(() {
      _invalidPassword = true; //define the conditions later
    });
  }

  void _handleOTP() {}

  void _handleContinue() {}

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
          preferredSize: const Size.fromHeight(50),
          child: AppBar(
            backgroundColor: PrimaryColors.dullGreen,
          )),
      backgroundColor: PrimaryColors.dullGreen,
      body: SingleChildScrollView(
        child: Column(
          children: [
            Center(
              child: Image.asset(
                'assets/images/CalowinNoBackground.png',
                width: 100,
                height: 100,
              ),
            ),
            Text(
              "CaloWin",
              style: PrimaryFonts.logoFont.copyWith(fontSize: 45),
            ),
            Text(
              "Crush Your Calories, Conquer Your Goals!",
              style: GoogleFonts.poppins(
                  color: Colors.white,
                  fontSize: 10,
                  fontWeight: FontWeight.bold),
            ),
            const SizedBox(
              height: 30,
            ),
            InputField(
              inputController: _inputEmail,
              title: "Email Address",
              inputHint: "Enter Your Email",
              errorText: "Invalid Email!!",
              hasError: _invalidEmail,
            ),
            const SizedBox(
              height: 30,
            ),
            InputField(
              inputController: _inputPassword,
              title: "Password",
              inputHint: "Enter Your Password",
              bottomHint:
                  "Your Password must be at least 8 characters long, have at least 1 special character, and at least 1 upper-case character.",
              errorText: "Invalid Password!! Please make sure: ",
              hasError: _invalidPassword,
            ),
            const SizedBox(
              height: 50,
            ),
            Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Text(
                  "OTP: ",
                  style: GoogleFonts.poppins(
                      fontSize: 14,
                      color: Colors.white,
                      fontWeight: FontWeight.bold),
                ),
                SizedBox(
                  height: 50,
                  width: 200,
                  child: TextField(
                      controller: _inputOTP,
                      textAlign: TextAlign.left,
                      decoration: InputDecoration(
                        filled: true,
                        fillColor: Colors.white,
                        border: inputBorder,
                        enabledBorder: inputBorder,
                        focusedBorder: inputBorder,
                        contentPadding: const EdgeInsets.symmetric(
                            vertical: 2, horizontal: 15),
                        hintText: "Enter OTP sent to your Email",
                        hintStyle: GoogleFonts.roboto(
                            fontSize: 12, color: PrimaryColors.grey),
                      )),
                ),
                SizedBox(
                  width: 70,
                  height: 30,
                  child: ElevatedButton(
                      onPressed: _handleOTP,
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.blue,
                        padding: const EdgeInsets.symmetric(
                            horizontal: 2, vertical: 2),
                        shape: RoundedRectangleBorder(
                          borderRadius:
                              BorderRadius.circular(10), // Rounded corners
                        ),
                      ),
                      child: Text(
                        "Send OTP",
                        style: GoogleFonts.roboto(
                            fontSize: 12, color: Colors.white),
                      )),
                ),
              ],
            ),
            const SizedBox(
              height: 30,
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
              child: Divider(
                thickness: 1,
                color: Colors.grey.shade400,
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
              child: SizedBox(
                width: 400,
                height: 45,
                child: ElevatedButton(
                    onPressed: _handleContinue,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color.fromARGB(255, 48, 93, 48),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 40, vertical: 12),
                      shape: RoundedRectangleBorder(
                        borderRadius:
                            BorderRadius.circular(10), // Rounded corners
                      ),
                    ),
                    child: Text(
                      "Continue",
                      style:
                          GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                    )),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
