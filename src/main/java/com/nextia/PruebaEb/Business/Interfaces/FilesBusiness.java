package com.nextia.PruebaEb.Business.Interfaces;

import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
public interface FilesBusiness {

    void createDirectory();

    ResponseEntity<HeaderResponse> saveFiles(MultipartFile[] file);

    ResponseEntity<HeaderResponse> deleteFile(String fileName);

    Resource loadFile(String filename);

    ResponseEntity<FilesResponse> loadAllFiles();
}
