package com.nextia.PruebaEb.Ws.Request.Users;

public class PasswordRequest {
    public String hashUser;
    public String newPassword;

    public String getHashUser() {
        return hashUser;
    }

    public void setHashUser(String hashUser) {
        this.hashUser = hashUser;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
