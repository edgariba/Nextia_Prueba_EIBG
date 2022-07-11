package com.nextia.PruebaEb.Ws.Request.Users;

public class UserUpdateRequest {
    public String name;
    public String lastName;
    public String email;
    public String password;
    public String hashUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getHashUser() {
        return hashUser;
    }

    public void setHashUser(String hashUser) {
        this.hashUser = hashUser;
    }
}
