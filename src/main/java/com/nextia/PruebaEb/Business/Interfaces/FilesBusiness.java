package com.nextia.PruebaEb.Business.Interfaces;

import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesBusiness {

    void createDirectory();
    ResponseEntity<HeaderResponse> saveFiles(MultipartFile[] file);

    //public void deleteAll();

    ResponseEntity<FilesResponse> loadAllFiles();
}
