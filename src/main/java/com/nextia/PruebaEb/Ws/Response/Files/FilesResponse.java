package com.nextia.PruebaEb.Ws.Response.Files;

import com.nextia.PruebaEb.Pojos.FilesPojo;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;

import java.util.List;

public class FilesResponse {
    public HeaderResponse header;
    public List<FilesPojo> data;

    public FilesResponse(HeaderResponse header, List<FilesPojo> data) {
        this.header = header;
        this.data = data;
    }
}
