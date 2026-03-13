package com.miguel.assistencesystem.application.validation;

import java.util.regex.Pattern;

public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private EmailValidator() {}

    public static boolean isValid(String email) {
        if (email == null || email.isBlank())
            return false;

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
