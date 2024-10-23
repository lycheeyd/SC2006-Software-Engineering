import 'package:flutter/services.dart';

class MaxLinesInputFormatter extends TextInputFormatter {
  final int maxLines;

  MaxLinesInputFormatter({required this.maxLines});

  @override
  TextEditingValue formatEditUpdate(
      TextEditingValue oldValue, TextEditingValue newValue) {
    // Split the new text into lines
    final lines = newValue.text.split('\n');

    // Check for length and insert new line character if needed
    String modifiedText = '';
    for (var line in lines) {
      while (line.length > 35) {
        modifiedText += '${line.substring(0, 35)}\n'; // Add line with newline
        line = line.substring(35); // Reduce the line
      }
      modifiedText += '$line\n'; // Add the remaining part of the line
    }

    // Split again to enforce max lines
    final finalLines = modifiedText.split('\n');

    if (finalLines.length > maxLines) {
      // Join the lines that are within the limit
      final trimmedText = finalLines.sublist(0, maxLines).join('\n');
      // Return the new value with the trimmed text and the cursor at the end
      return TextEditingValue(
        text: trimmedText,
        selection: TextSelection.fromPosition(
          TextPosition(offset: trimmedText.length),
        ),
      );
    }

    // Return the modified value if it's within the limit
    return TextEditingValue(
      text: modifiedText.trim(), // Remove trailing newline
      selection: TextSelection.fromPosition(
        TextPosition(offset: modifiedText.trim().length),
      ),
    );
  }
}
