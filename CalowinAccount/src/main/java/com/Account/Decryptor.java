package com.Account;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Decryptor {
    protected static String decrypt(String encrypted, String key) throws Exception {
        String[] parts = encrypted.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[1]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[0]);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        byte[] original = cipher.doFinal(encryptedBytes);
        return new String(original);
    }
}
