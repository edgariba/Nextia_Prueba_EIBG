package com.nextia.PruebaEb.Business.Interfaces;

import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.*;
import com.nextia.PruebaEb.Ws.Response.LoginResponse;
import com.nextia.PruebaEb.Ws.Response.UserResponse;
import com.nextia.PruebaEb.Ws.Response.UsersPagResponse;
import org.springframework.http.ResponseEntity;

public interface UsersBusiness {
    ResponseEntity<UserResponse> getUserByHash(String hashUser);

    ResponseEntity<UsersPagResponse> findAllUsers(UsersPagRequest request);

    ResponseEntity<LoginResponse> loginUser(LoginRequest request);

    ResponseEntity<HeaderResponse> addUser(UserAddRequest request);

    ResponseEntity<HeaderResponse> updateUser(UserUpdateRequest request);

    ResponseEntity<HeaderResponse> deleteUser(String hashUser);

    ResponseEntity<HeaderResponse> changePassword(PasswordRequest request);

}
