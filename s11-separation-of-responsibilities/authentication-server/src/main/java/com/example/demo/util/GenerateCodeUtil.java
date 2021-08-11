package com.example.demo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateCodeUtil {

    public static String generateOtp() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            int nextInt = secureRandom.nextInt(9000) + 1000;
            return Integer.toString(nextInt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem generating random code");
        }
    }

}
