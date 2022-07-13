package com.nextia.PruebaEb.Business.Impl;

import com.nextia.PruebaEb.Business.Interfaces.FilesBusiness;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Pojos.FilesPojo;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Response.Files.FilesResponse;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
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

    /**
     * Metodo para crear el directorio
     */
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

    /**
     * Metodo para guardar los archivos
     * @param file
     * @return
     */
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
            throw new ConflictException("No se ha podido almacenar el archivo. Error: " + e.getMessage());
        }
    }

    /**
     * Metodo para eliminar un archivo
     * @param fileName
     * @return
     */
    @Override
    public ResponseEntity<HeaderResponse> deleteFile(String fileName) {
        String msg;
        HeaderResponse response;
        Path file = pathFiles.resolve(fileName);
        try {
            FileSystemUtils.deleteRecursively(file);
            msg = ConstantText.MSG_DELETE;
            response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrio un error al eliminar: " + e);
        }
    }

    /**
     * Metodo para cargar un archivo por nombre
     * @param filename
     * @return
     */
    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = pathFiles.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se pudo leer el archivo solicitado.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * Metodo para obtener todos los files
     * @return
     */
    @Override
    public ResponseEntity<FilesResponse> loadAllFiles() {
        String msg;
        FilesResponse response;
        List<FilesPojo> filesPojos = new ArrayList<>();
        try {
            Stream<Path> pathStream = Files.walk(this.pathFiles, 1).filter(path -> !path.equals(this.pathFiles)).map(this.pathFiles::relativize);
            pathStream.forEach(path -> {
                Path file = pathFiles.resolve(path.getFileName());
                filesPojos.add(new FilesPojo(path.getFileName().toString(), file.toAbsolutePath().toString().replace("\\", "/")));
            });
            msg = ConstantText.MSG_LIST;
            response = new FilesResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), filesPojos);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("No se han podido cargar los archivos.");
        }
    }
}
