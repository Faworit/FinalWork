package com.epam.library.validator;

import com.epam.library.entity.User;

public class AuthorizationValidator {

    private static final String notBlock = "n";
    private static final String MAIL_REGEX = "([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{6}";

    public static boolean validateMailRegex(String login){
        return login.matches(MAIL_REGEX);
    }

    public static boolean validatePasswordRegex(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validatePassword(String password, String passFromDB){
        return passFromDB.equals(password);
    }

    public static boolean validateBlock(User user){
        String block = user.getBlock();
        return block.equals(notBlock);
    }
}
