package com.nextia.PruebaEb.Ws.Response;

import com.nextia.PruebaEb.Entity.UsersEntity;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;

public class UserResponse {
    public HeaderResponse header;
    public UsersEntity data;

    /**
     * Constructor para el usuario
     * @param header
     * @param data
     */
    public UserResponse(HeaderResponse header, UsersEntity data) {
        this.header = header;
        this.data = data;
    }
}
