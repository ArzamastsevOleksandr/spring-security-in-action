package com.ssia.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha512PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        var sha512 = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] bytes = messageDigest.digest(rawPassword.toString().getBytes());
            for (var b : bytes) {
                sha512.append(Integer.toHexString(0xFF & b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Bad algorithm");
        }
        return sha512.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String sha512 = encode(rawPassword);
        return sha512.equals(encodedPassword);
    }

}
