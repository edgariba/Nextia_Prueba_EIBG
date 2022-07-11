package com.nextia.PruebaEb.Ws.Request.Users;

/**
 * Clase para el login request
 */
public class LoginRequest {
    public String email;
    public String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
