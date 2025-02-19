package com.auth.microservice.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CryptoPassword {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password){
        return encoder.encode(password);
    }

    public static boolean verifyPasswords(String savedPassword, String requestPassword){
        return encoder.matches(savedPassword, requestPassword);
    }

}
