package com.nextia.PruebaEb.Business.Impl;

import com.nextia.PruebaEb.Business.Interfaces.FilesBusiness;
import com.nextia.PruebaEb.Controllers.FilesController;
import com.nextia.PruebaEb.Pojos.FilesPojo;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service("filesBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class FilesBusinessImpl implements FilesBusiness {
    private final Path pathFiles = Paths.get("filesUploads");

    @Override
    public void createDirectory() {
        try {
            if (!Files.exists(pathFiles)) {
                Files.createDirectory(pathFiles);
            }
        } catch (IOException e) {
            System.out.println("No se ha podido inicializar la carpeta files");
        }
    }

    @Override
    public ResponseEntity<HeaderResponse> saveFiles(MultipartFile[] file) {
        String msg;
        HeaderResponse response;
        try {
            for (MultipartFile multipartFile : file) {
                Files.copy(multipartFile.getInputStream(), this.pathFiles.resolve(multipartFile.getOriginalFilename()));
            }
            msg = ConstantText.FILES_UPLOAD;
            response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("No se ha podido almacenar el archivo. Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<FilesResponse> loadAllFiles() {
        String msg;
        FilesResponse response;
        List<FilesPojo> filesPojos = new ArrayList<>();
        try {
            Stream<Path> pathStream = Files.walk(this.pathFiles, 1).filter(path -> !path.equals(this.pathFiles)).map(this.pathFiles::relativize);
            pathStream.forEach(path -> filesPojos.add(new FilesPojo(path.getFileName().toString(), path.toFile().getAbsolutePath().toString())));
            msg = ConstantText.MSG_LIST;
            response = new FilesResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), filesPojos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException("No se han podido cargar los archivos.");
        }
    }
}
