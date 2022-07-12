package com.nextia.PruebaEb.Controllers;

import com.nextia.PruebaEb.Business.Interfaces.FilesBusiness;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
     *
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
     *
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

    /**
     * Metodo para obtener el rchivo por nombre
     *
     * @param filename
     * @return
     */
    @GetMapping("/get/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = filesBusiness.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /**
     * Eliminar archivo por nombre
     * @param filename
     * @return
     */
    @DeleteMapping(value = "/delete/{filename}")
    public ResponseEntity<HeaderResponse> deleteFiles(@PathVariable String filename) {
        HeaderResponse response;
        String msg;
        try {
            return filesBusiness.deleteFile(filename);
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
}
