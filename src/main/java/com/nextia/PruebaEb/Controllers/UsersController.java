package com.nextia.PruebaEb.Controllers;

import com.nextia.PruebaEb.Business.Interfaces.UsersBusiness;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.*;
import com.nextia.PruebaEb.Ws.Response.LoginResponse;
import com.nextia.PruebaEb.Ws.Response.UserResponse;
import com.nextia.PruebaEb.Ws.Response.UsersPagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * * @author Edgar Ivan Barrera
 * Clase controller de usuarios
 */
@Validated
@CrossOrigin
@RestController
@RequestMapping(ConstantText.API_V1 + "users/")
public class UsersController {

    @Autowired
    private UsersBusiness usersBusiness;

    /**
     * Get de usuario
     *
     * @param hashUser
     * @return
     */
    @GetMapping(value = "/getByHash")
    public ResponseEntity<UserResponse> getUserByHash(@RequestParam(value = "hashUser") String hashUser) {
        UserResponse response;
        String msg;
        try {
            return usersBusiness.getUserByHash(hashUser);
        } catch (ConflictException e) {
            msg = e.getMessage();
            response = new UserResponse(new HeaderResponse(ConstantText.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = e.getMessage();
            response = new UserResponse(new HeaderResponse(ConstantText.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Login de usuario
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request) {
        LoginResponse response;
        String msg;
        try {
            return usersBusiness.loginUser(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            response = new LoginResponse(new HeaderResponse(ConstantText.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = e.getMessage();
            response = new LoginResponse(new HeaderResponse(ConstantText.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * POST para añadir usuario
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/add")
    public ResponseEntity<HeaderResponse> addUser(@RequestBody UserAddRequest request) {
        HeaderResponse response;
        String msg;
        try {
            return usersBusiness.addUser(request);
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
     * PUT para actualizar un usuario
     *
     * @param request
     * @return
     */
    @PutMapping(value = "/update")
    public ResponseEntity<HeaderResponse> updateUser(@RequestBody UserUpdateRequest request) {
        HeaderResponse response;
        String msg;
        try {
            return usersBusiness.updateUser(request);
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
     * Delete usuario por hash
     *
     * @param hashUser
     * @return
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<HeaderResponse> deleteUser(@RequestParam(value = "hashUser") String hashUser) {
        HeaderResponse response;
        String msg;
        try {
            return usersBusiness.deleteUser(hashUser);
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
     * Post para cambiar la contraseña
     *
     * @param request
     * @return
     */
    @PutMapping(value = "/changePassword")
    public ResponseEntity<HeaderResponse> changePassword(@RequestBody PasswordRequest request) {
        HeaderResponse response;
        String msg;
        try {
            return usersBusiness.changePassword(request);
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
     * Metodo para obtener el listado de usuarios
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/getAll")
    public ResponseEntity<UsersPagResponse> getAll(@RequestBody UsersPagRequest request) {
        UsersPagResponse response;
        String msg;
        try {
            return usersBusiness.findAllUsers(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            response = new UsersPagResponse(new HeaderResponse(ConstantText.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = e.getMessage();
            response = new UsersPagResponse(new HeaderResponse(ConstantText.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}


