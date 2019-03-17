package com.epam.library.validator;

public class RepeatPasswordValidator {

    public static boolean isEqualPasswords(String password, String repeatPassword){
        boolean isEqual = false;
        if(password.equals(repeatPassword)){
            isEqual = true;
        }
        return isEqual;
    }
}
