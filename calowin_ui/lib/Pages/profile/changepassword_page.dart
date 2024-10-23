import 'package:calowin/common/colors_and_fonts.dart';
import 'package:calowin/common/custom_scaffold.dart';
import 'package:calowin/common/input_field.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class ChangepasswordPage extends StatefulWidget {
  const ChangepasswordPage({super.key});

  @override
  State<ChangepasswordPage> createState() => _ChangepasswordPageState();
}

class _ChangepasswordPageState extends State<ChangepasswordPage> {
  bool _wrongPW = false;
  bool _wrongNewPW = false;
  bool _newPWNotSame = false;

  TextEditingController _currentPWController = TextEditingController();
  TextEditingController _newPWController = TextEditingController();
  TextEditingController _confirmPWController = TextEditingController();

  void _handleChangePassword() {
    // Handle change password logic
    Navigator.pop(context);
    Navigator.pop(context);
  }

  @override
  Widget build(BuildContext context) {
    return CustomScaffold(
      body: Scaffold(
        backgroundColor: PrimaryColors.dullGreen,
        appBar: AppBar(
          backgroundColor: PrimaryColors.dullGreen,
        ),
        resizeToAvoidBottomInset:
            false, // Prevent resizing when keyboard pops up
        body: Padding(
          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 20),
          child: Column(
            children: [
              Expanded(
                child: SingleChildScrollView(
                  child: Column(
                    children: [
                      Align(
                        alignment: Alignment.topLeft,
                        child: Text(
                          "Change Password",
                          style: GoogleFonts.poppins(
                              color: Colors.white,
                              fontWeight: FontWeight.bold,
                              fontSize: 30),
                        ),
                      ),
                      const SizedBox(height: 10),
                      Align(
                        alignment: Alignment.topLeft,
                        child: Text(
                          "Your password must:\n  * Be at least 8 characters long\n  * Contain at least 1 special character\n  * Contain at least 1 upper case letter",
                          style: GoogleFonts.poppins(
                              fontSize: 15, color: Colors.white),
                        ),
                      ),
                      const SizedBox(height: 20),
                      InputField(
                        obscureText: true,
                        inputController: _currentPWController,
                        title: "Current Password",
                        inputHint: "Enter Your Current Password",
                        errorText: "Wrong Password!",
                        hasError: _wrongPW,
                      ),
                      const SizedBox(height: 10),
                      InputField(
                        obscureText: true,
                        inputController: _newPWController,
                        title: "New Password",
                        inputHint: "Enter Your New Password",
                        errorText: "Invalid Password!",
                        hasError: _wrongNewPW,
                      ),
                      const SizedBox(height: 10),
                      InputField(
                        obscureText: true,
                        inputController: _confirmPWController,
                        title: "Confirm Password",
                        inputHint: "Re-enter New Password",
                        errorText:
                            "Please make sure you entered the same as above!",
                        hasError: _newPWNotSame,
                      ),
                    ],
                  ),
                ),
              ),
              SizedBox(
                width: double.infinity,
                height: 45,
                child: ElevatedButton(
                  onPressed: _handleChangePassword,
                  style: ElevatedButton.styleFrom(
                    elevation: 0,
                    backgroundColor: PrimaryColors.darkGreen,
                    padding: const EdgeInsets.symmetric(
                        horizontal: 40, vertical: 12),
                    shape: RoundedRectangleBorder(
                      borderRadius:
                          BorderRadius.circular(10), // Rounded corners
                    ),
                  ),
                  child: Text(
                    "Change Password",
                    style:
                        GoogleFonts.roboto(fontSize: 16, color: Colors.white),
                  ),
                ),
              ),
              const SizedBox(
                height: 40,
              )
            ],
          ),
        ),
      ),
    );
  }
}
