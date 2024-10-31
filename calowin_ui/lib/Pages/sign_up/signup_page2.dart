import 'package:calowin/common/custom_scaffold.dart';
import 'package:calowin/common/input_field.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/control/page_navigator.dart';
import 'package:calowin/control/AES_Encryptor.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class SignupPage2 extends StatefulWidget {
  final String email;
  final String password;
  final String confirmPassword;

  const SignupPage2({
    super.key,
    required this.email,
    required this.password,
    required this.confirmPassword,
  });

  @override
  State<SignupPage2> createState() => _SignupPage2State();
}

class _SignupPage2State extends State<SignupPage2> {
  final TextEditingController _inputWeight = TextEditingController();
  final TextEditingController _inputName = TextEditingController();

  String? _nameError;
  String? _weightError;

  final InputBorder inputBorder = UnderlineInputBorder(
      borderRadius: BorderRadius.circular(8), borderSide: BorderSide.none);

  void _checkName() {
    setState(() {
      if (_inputName.text.isEmpty) {
        _nameError = "Name is required";
      } else if (_inputName.text.length > 16) {
        _nameError = "Name cannot exceed 16 characters";
      } else {
        _nameError = null;
      }
    });
  }

  void _checkWeight() {
    setState(() {
      if (_inputWeight.text.isEmpty) {
        _weightError = "Weight is required";
      } else if (!RegExp(weightPattern).hasMatch(_inputWeight.text)) {
        _weightError = "Enter weight in kg (e.g., 70 or 70.5)";
      } else {
        _weightError = null;
      }
    });
  }

  Future<void> _handleSignup() async {
    _checkName();
    _checkWeight();

    if (_nameError == null && _weightError == null) {
      final String encryptedPassword = AES_Encryptor.encrypt(widget.password);
      final String encryptedConfirmPassword = AES_Encryptor.encrypt(widget.confirmPassword);

      final String url = "http://172.21.146.188:8080/central/account/signup";

      try {
        final response = await http.post(
          Uri.parse(url),
          headers: {"Content-Type": "application/json"},
          body: json.encode({
            "email": widget.email,
            "password": encryptedPassword,
            "confirm_password": encryptedConfirmPassword,
            "name": _inputName.text,
            "weight": double.parse(_inputWeight.text),
          }),
        );

        if (response.statusCode == 201) {
          // Signup successful
          ScaffoldMessenger.of(context).showSnackBar(
            const SnackBar(content: Text("Signup successful! Welcome to CaloWin!")),
          );

          // Navigate to the next page
          Navigator.of(context).push(
            PageRouteBuilder(
              pageBuilder: (context, animation, secondaryAnimation) =>
                  const PageNavigator(),
              transitionsBuilder: (context, animation, secondaryAnimation, child) {
                return child; // No custom transition
              },
              settings: const RouteSettings(arguments: 'disableSwipe'),
            ),
          );
        } else if (response.statusCode == 400) {
          // Bad request - likely due to invalid input
          final errorMessage = response.body;
          if (errorMessage.contains("Password")) {
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(content: Text("Signup failed: Password is invalid")),
            );
          } else if (errorMessage.contains("Email")) {
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(content: Text("Signup failed: Email is already registered")),
            );
          }
        } else {
          // Other errors
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Error: ${response.body}")),
          );
        }
      } catch (e) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(content: Text("Network error: ${e.toString()}")),
        );
      }
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please correct errors before proceeding.")),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return CustomScaffold(
      body: Scaffold(
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
                  obscureText: false,
                  inputController: _inputName,
                  title: "Name",
                  inputHint: "This name is what others will see!",
                  errorText: _nameError,
                  hasError: _nameError != null,
              ),
              const SizedBox(height: 30),

              InputField(
                obscureText: false,
                keyboardType: const TextInputType.numberWithOptions(decimal: true),
                inputFormatter: <TextInputFormatter>[
                  FilteringTextInputFormatter.allow(RegExp(r'^\d+\.?\d{0,1}'))
                ],
                inputController: _inputWeight,
                title: "Weight",
                inputHint: "Enter your weight in KG",
                bottomHint: "You can give up to the first decimal place!",
                errorText: _weightError,
                hasError: _weightError != null,
              ),
              const SizedBox(height: 30),

              Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
                child: Divider(
                  thickness: 1,
                  color: Colors.grey.shade400,
                ),
              ),
              Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
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
