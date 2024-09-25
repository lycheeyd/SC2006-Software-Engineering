import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

class PrimaryColors {
  static const dullGreen = Color.fromARGB(255, 119, 181, 119);
  static const brightGreen = Color.fromARGB(255, 58, 229, 58);
  static const orange = Color.fromARGB(255, 252, 173, 97);
  static const black = Color.fromARGB(255, 0, 0, 0);
}

class PrimaryFonts {
  static final logoFont = GoogleFonts.rammettoOne(
      textStyle: const TextStyle(color: PrimaryColors.black, fontSize: 50));
}
