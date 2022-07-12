package com.nextia.PruebaEb.Pojos;

import com.nextia.PruebaEb.Entity.UsersEntity;

public class UserLogin {
    public String tokenJWT;
    public UsersEntity user;

    public UserLogin(String tokenJWT, UsersEntity user) {
        this.tokenJWT = tokenJWT;
        this.user = user;
    }
}
