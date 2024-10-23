import 'package:calowin/Pages/profile/changepassword_page.dart';
import 'package:calowin/common/custom_scaffold.dart';
import 'package:calowin/common/input_dialog.dart';
import 'package:calowin/control/maxline_inputformatter.dart';
import 'package:flutter/material.dart';
import 'package:calowin/common/colors_and_fonts.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:calowin/common/input_field.dart';
import 'package:flutter/services.dart';

class EditprofilePage extends StatefulWidget {
  const EditprofilePage({super.key});

  @override
  State<EditprofilePage> createState() => _EditprofilePageState();
}

class _EditprofilePageState extends State<EditprofilePage> {
  //logic to be implemented
  bool _invalidName = false;
  bool _invalidWeight = false;

  //preset the inputs here
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _weightController = TextEditingController();
  final TextEditingController _bioController = TextEditingController();

  void _handleSaveChanges() {
    Navigator.pop(context);
  }

  void _handleChangePassword() {
    Navigator.push(context,
        MaterialPageRoute(builder: (context) => const ChangepasswordPage()));
  }

  void _handleDeleteAccount(String otp) {}

  @override
  Widget build(BuildContext context) {
    return CustomScaffold(
      body: Scaffold(
        resizeToAvoidBottomInset: false,
        appBar: AppBar(
          backgroundColor: PrimaryColors.dullGreen,
        ),
        backgroundColor: PrimaryColors.dullGreen,
        body: Padding(
          padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 20),
          child: Column(
            children: [
              Align(
                alignment: Alignment.topLeft,
                child: Text(
                  "Edit Profile",
                  style: GoogleFonts.poppins(
                      color: Colors.white,
                      fontWeight: FontWeight.bold,
                      fontSize: 30),
                ),
              ),
              const SizedBox(
                height: 20,
              ),
              InputField(
                  obscureText: false,
                  hasError: _invalidName,
                  errorText: "Invalid Name",
                  inputController: _nameController,
                  title: "Name",
                  inputHint: "Enter your name here"),
              const SizedBox(
                height: 10,
              ),
              SizedBox(
                height: 140,
                width: 400,
                child: Column(
                  children: [
                    Align(
                      alignment: Alignment.topLeft,
                      child: Text(
                        textAlign: TextAlign.left,
                        "Bio",
                        style: GoogleFonts.poppins(
                            fontSize: 14,
                            fontWeight: FontWeight.bold,
                            color: Colors.white),
                      ),
                    ),
                    const SizedBox(
                      height: 5,
                    ),
                    Container(
                      height: 100,
                      width: 400,
                      decoration: BoxDecoration(
                          color: const Color.fromARGB(255, 233, 243, 233),
                          borderRadius: BorderRadius.circular(10)),
                      child: Padding(
                        padding: const EdgeInsets.symmetric(
                            vertical: 5, horizontal: 10),
                        child: TextField(
                          inputFormatters: [
                            MaxLinesInputFormatter(maxLines: 3),
                            LengthLimitingTextInputFormatter(100)
                          ],
                          style: const TextStyle(fontSize: 14),
                          controller: _bioController,
                          maxLines: 3, // Set the maximum number of lines
                          minLines: 1, // Set the minimum number of lines
                          decoration: const InputDecoration(
                            labelText: "Maximum 100 characters",
                            labelStyle: TextStyle(fontSize: 12),
                            border: InputBorder.none,
                          ),
                        ),
                      ),
                    )
                  ],
                ),
              ),
              InputField(
                  obscureText: false,
                  keyboardType:
                      const TextInputType.numberWithOptions(decimal: true),
                  inputFormatter: <TextInputFormatter>[
                    FilteringTextInputFormatter.allow(RegExp(r'^\d+\.?\d{0,1}'))
                  ],
                  hasError: _invalidWeight,
                  errorText: "Invalid Weight",
                  inputController: _weightController,
                  title: "Weight",
                  inputHint: "Enter your current weight"),
              const Spacer(),
              Padding(
                padding:
                    const EdgeInsets.symmetric(vertical: 10, horizontal: 5),
                child: Column(
                  children: [
                    Align(
                      alignment: Alignment.bottomCenter,
                      child: SizedBox(
                        width: 400,
                        height: 45,
                        child: ElevatedButton(
                            onPressed: _handleSaveChanges,
                            style: ElevatedButton.styleFrom(
                              elevation: 0,
                              backgroundColor: PrimaryColors.brightGreen,
                              padding: const EdgeInsets.symmetric(
                                  horizontal: 40, vertical: 12),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(
                                    10), // Rounded corners
                              ),
                            ),
                            child: Text(
                              "Save Changes",
                              style: GoogleFonts.roboto(
                                  fontSize: 16, color: Colors.white),
                            )),
                      ),
                    ),
                    const SizedBox(
                      height: 10,
                    ),
                    Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Align(
                          alignment: Alignment.bottomLeft,
                          child: SizedBox(
                            width: 150,
                            height: 40,
                            child: ElevatedButton(
                                onPressed: _handleChangePassword,
                                style: ElevatedButton.styleFrom(
                                  elevation: 0,
                                  backgroundColor: PrimaryColors.darkGreen,
                                  padding: const EdgeInsets.symmetric(
                                      horizontal: 0, vertical: 5),
                                  shape: RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(
                                        10), // Rounded corners
                                  ),
                                ),
                                child: Text(
                                  "Change Password",
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
                                onPressed: () => showDialog(
                                    context: context,
                                    builder: (BuildContext context) {
                                      return InputDialog(
                                          confirmButtonColor: Colors.red,
                                          confirmButtonText: "Delete Account",
                                          hintText: "OTP",
                                          title: "Delete Account",
                                          content:
                                              "An OTP has been sent to your email to confirm your identity",
                                          onConfirm: (inputText) {
                                            Navigator.of(context).pop();
                                            return _handleDeleteAccount(
                                                inputText);
                                          },
                                          onCancel: () {
                                            Navigator.of(context).pop();
                                          });
                                    }),
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
                                  "Delete Account",
                                  style: GoogleFonts.roboto(
                                      fontSize: 16, color: Colors.white),
                                )),
                          ),
                        )
                      ],
                    ),
                    const SizedBox(
                      height: 15,
                    )
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
