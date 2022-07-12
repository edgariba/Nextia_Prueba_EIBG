package com.nextia.PruebaEb.Controllers;

import com.nextia.PruebaEb.Business.Interfaces.FilesBusiness;
import com.nextia.PruebaEb.Business.Interfaces.UsersBusiness;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.LoginRequest;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import com.nextia.PruebaEb.Ws.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * * @author Edgar Ivan Barrera
 * Clase controller de archivos
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping(ConstantText.API_V1 + "files/")
public class FilesController {

    @Autowired
    private FilesBusiness filesBusiness;

    /**
     * Metodo para subir los archivos
     * @param file
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<HeaderResponse> uploadFiles(@RequestParam("file") MultipartFile[] file) {
        HeaderResponse response;
        String msg;
        try {
            return filesBusiness.saveFiles(file);
        } catch (ConflictException e) {
            msg = e.getMessage();
            response = new HeaderResponse(ConstantText.CONFLICT, HttpStatus.CONFLICT.value(), msg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = e.getMessage();
            response = new HeaderResponse(ConstantText.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Metodo para obtener todos los archivos
     * @return
     */
    @GetMapping(value = "/loadAllFiles")
    public ResponseEntity<FilesResponse> loadAllFiles() {
        FilesResponse response;
        String msg;
        try {
            return filesBusiness.loadAllFiles();
        } catch (ConflictException e) {
            msg = e.getMessage();
            response = new FilesResponse(new HeaderResponse(ConstantText.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = e.getMessage();
            response = new FilesResponse(new HeaderResponse(ConstantText.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
