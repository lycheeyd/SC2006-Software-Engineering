import 'package:calowin/Pages/mapcalc_page.dart';
import 'package:calowin/Pages/sign_up/input_field.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:flutter/services.dart';

class SignupPage2 extends StatefulWidget {
  const SignupPage2({super.key});

  @override
  State<SignupPage2> createState() => _SignupPage2State();
}

class _SignupPage2State extends State<SignupPage2> {
  final TextEditingController _inputPassword = TextEditingController();
  final TextEditingController _inputEmail = TextEditingController();
  bool _invalidName = false;
  bool _invalidWeight = false;
  final InputBorder inputBorder = UnderlineInputBorder(
      borderRadius: BorderRadius.circular(8), borderSide: BorderSide.none);

  void _checkName() {
    setState(() {
      _invalidName = true; //define the conditions laterf
    });
  }

  void _checkWeight() {
    setState(() {
      _invalidWeight = true; //define the conditions later
    });
  }

  void _handleSignup() {
    //check conditions and communicate with backend
    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) => const PageNavigator(
                  startPage: 0,
                )));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: PreferredSize(
          preferredSize: const Size.fromHeight(50),
          child: AppBar(
            backgroundColor: PrimaryColors.dullGreen,
            //automaticallyImplyLeading: false,
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
                title: "Name",
                inputHint: "This name is what others will see!",
                errorText: "Invalid Name!! Try another one",
                hasError: _invalidWeight),
            const SizedBox(
              height: 30,
            ),
            InputField(
              keyboardType:
                  const TextInputType.numberWithOptions(decimal: true),
              inputFormatter: <TextInputFormatter>[
                FilteringTextInputFormatter.allow(RegExp(r'^\d+\.?\d{0,1}'))
              ],
              inputController: _inputPassword,
              title: "Weight",
              inputHint: "Enter your weight in KG",
              bottomHint: "You can give up to the first decimal place!",
              errorText:
                  "Invalid Weight!! Please make sure you entered your weight in all numbers and to the nearest 1 decimal.",
              hasError: _invalidName,
            ),
            const SizedBox(
              height: 150,
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
                    onPressed: _handleSignup,
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
                      "Sign Up",
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
