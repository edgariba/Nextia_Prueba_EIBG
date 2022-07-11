package com.nextia.PruebaEb.Business.Impl;

import com.nextia.PruebaEb.Business.Interfaces.UsersBusiness;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.LoginRequest;
import com.nextia.PruebaEb.Ws.Request.Users.PasswordRequest;
import com.nextia.PruebaEb.Ws.Request.Users.UserAddRequest;
import com.nextia.PruebaEb.Ws.Request.Users.UserUpdateRequest;
import com.nextia.PruebaEb.Ws.Response.UserResponse;
import org.springframework.http.ResponseEntity;

public class UsersBusinessImpl implements UsersBusiness {
    @Override
    public ResponseEntity<UserResponse> getUserByHash(String hashUser) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponse> loginUser(LoginRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<HeaderResponse> addUser(UserAddRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<HeaderResponse> updateUser(UserUpdateRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteUser(String hashUser) {
        return null;
    }

    @Override
    public ResponseEntity<HeaderResponse> changePassword(PasswordRequest request) {
        return null;
    }
}
