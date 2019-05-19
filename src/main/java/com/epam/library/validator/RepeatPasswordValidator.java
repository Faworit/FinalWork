package com.epam.library.validator;

public class RepeatPasswordValidator {

    public static boolean isEqualPasswords(String password, String repeatPassword){
        return password.equals(repeatPassword);
    }
}
