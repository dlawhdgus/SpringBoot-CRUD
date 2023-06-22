package com.example.springbootcrud.Util;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    @Value("${salt}")
    private static String salt;

    public static String cncode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] encodedHash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            return encodedHash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean PasswordMatch(String password, String encodedPassword) {
        String hashedPassword = cncode(password);

        return hashedPassword.equals(encodedPassword);
    }

}