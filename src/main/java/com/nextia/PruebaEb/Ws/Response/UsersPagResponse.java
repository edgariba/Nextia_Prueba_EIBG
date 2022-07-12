package com.nextia.PruebaEb.Ws.Response;

import com.nextia.PruebaEb.Entity.UsersEntity;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import org.springframework.data.domain.Page;

public class UsersPagResponse {
    public HeaderResponse header;
    public Page<UsersEntity> data;

    public UsersPagResponse(HeaderResponse header, Page<UsersEntity> data) {
        this.header = header;
        this.data = data;
    }
}
