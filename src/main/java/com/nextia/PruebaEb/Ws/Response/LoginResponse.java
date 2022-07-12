package com.nextia.PruebaEb.Ws.Response;

import com.nextia.PruebaEb.Entity.UsersEntity;
import com.nextia.PruebaEb.Pojos.UserLogin;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;

public class LoginResponse {
    public HeaderResponse header;
    public UserLogin data;

    public LoginResponse(HeaderResponse header, UserLogin data) {
        this.header = header;
        this.data = data;
    }
}
