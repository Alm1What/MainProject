package org.example.mainpriject.dto;

import java.util.Arrays;

public class PasswordChangeDto {
    private char[] currentPassword;
    private char[] newPassword;

    public PasswordChangeDto() {
    }

    public PasswordChangeDto(char[] currentPassword, char[] newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public char[] getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(char[] currentPassword) {
        this.currentPassword = currentPassword;
    }

    public char[] getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(char[] newPassword) {
        this.newPassword = newPassword;
    }

    public void clearPasswords() {
        Arrays.fill(currentPassword, '\0');
        Arrays.fill(newPassword, '\0');
    }
}
