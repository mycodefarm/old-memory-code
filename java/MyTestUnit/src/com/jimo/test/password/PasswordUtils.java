package com.jimo.test.password;

import java.util.List;

/**
 * Created by jimo on 17-8-27.
 */
public class PasswordUtils {

    @UseCase(id = 10, desc = "password must contain at least one number")
    public boolean validatePassword(String password) {
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 11)
    public String encryptPassword(String password) {
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 12, desc = "new password cannot equal previously used ones")
    public boolean checkForNewPassword(List<String> oldPasswords, String newPassword) {
        return !oldPasswords.contains(newPassword);
    }
}
