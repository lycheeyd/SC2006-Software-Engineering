import 'dart:convert';
import 'package:encrypt/encrypt.dart' as encrypt;

class AES_Encryptor {
  static const String _key = "hd8Hd7K8djHY8dh4"; // Ensure this matches your backend key
  static final encrypt.IV _iv = encrypt.IV.fromLength(16);

  /// Encrypts the given plaintext using AES-128 CBC mode with PKCS7 padding.
  static String encrypt(String plainText) {
    final key = encrypt.Key.fromUtf8(_key);
    final encrypter = encrypt.Encrypter(encrypt.AES(key, mode: encrypt.AESMode.cbc, padding: 'PKCS7'));

    final encrypted = encrypter.encrypt(plainText, iv: _iv);
    return '${encrypted.base64}:${_iv.base64}';
  }
}