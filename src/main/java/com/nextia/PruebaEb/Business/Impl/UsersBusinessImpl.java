package com.nextia.PruebaEb.Business.Impl;

import com.nextia.PruebaEb.Business.Interfaces.UsersBusiness;
import com.nextia.PruebaEb.Dao.UsersDao;
import com.nextia.PruebaEb.Entity.UsersEntity;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Pojos.UserLogin;
import com.nextia.PruebaEb.Security.JwtUtil;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.GenerateUuid;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.*;
import com.nextia.PruebaEb.Ws.Response.LoginResponse;
import com.nextia.PruebaEb.Ws.Response.UserResponse;
import com.nextia.PruebaEb.Ws.Response.UsersPagResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Optional;

@Service("usersBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class UsersBusinessImpl implements UsersBusiness {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    /**
     * Metodo para crear un usuario por default
     */
    @PostConstruct
    private void postConstruct() {
        UsersEntity user = new UsersEntity();
        Optional<UsersEntity> byEmail = usersDao.findByEmail("test@test.com");
        if (!byEmail.isPresent()) {
            user.setPassword(passwordEncoder.encode("test"));
            user.setHashUser(new GenerateUuid().generateUuid());
            user.setName("test");
            user.setLastName("test");
            user.setEmail("test@test.com");
            user.setCreatedDate(new Date());
            usersDao.save(user);
        }
    }

    /**
     * Otner usuario por hash
     * @param hashUser
     * @return
     */
    @Override
    public ResponseEntity<UserResponse> getUserByHash(String hashUser) {
        String msg;
        UserResponse response;
        UsersEntity userEntity = usersDao.findByHashUser(hashUser).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        msg = ConstantText.MSG_GET;
        response = new UserResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), userEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtener todos los usuarios
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<UsersPagResponse> findAllUsers(UsersPagRequest request) {
        String msg;
        UsersPagResponse response;
        Page<UsersEntity> allUsers = usersDao.findAllByIsDeleted(false, PageRequest.of(request.page, request.maxResults));
        msg = ConstantText.MSG_LIST;
        response = new UsersPagResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), allUsers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Login de usuario
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) {
        String msg;
        LoginResponse response;
        UsersEntity usersEntity = usersDao.findByEmail(request.getEmail()).orElseThrow(() -> new ConflictException(ConstantText.LOGIN_ERROR));
        if (usersEntity.isDeleted()) {
            throw new ConflictException(ConstantText.USER_NOT_ACTIVE);
        }
        boolean matches = passwordEncoder.matches(request.getPassword(), usersEntity.getPassword());
        if (!matches) {
            throw new ConflictException(ConstantText.LOGIN_ERROR);
        }
        msg = ConstantText.LOGIN_OK;
        response = new LoginResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), new UserLogin(jwtUtil.getJWTToken(request.getEmail()), usersEntity));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Agregar un usuario
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<HeaderResponse> addUser(UserAddRequest request) {
        String msg;
        HeaderResponse response;
        Optional<UsersEntity> byEmail = usersDao.findByEmail(request.getEmail());
        if (byEmail.isPresent()) {
            throw new ConflictException(ConstantText.USER_DUPLICATE);
        }
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setHashUser(new GenerateUuid().generateUuid());
        usersEntity.setCreatedDate(new Date());
        usersEntity.setName(request.getName());
        usersEntity.setLastName(request.getLastName());
        usersEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        usersEntity.setEmail(request.getEmail());
        usersDao.save(usersEntity);
        msg = ConstantText.SAVE_MSG;
        response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Actualizar usuario
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<HeaderResponse> updateUser(UserUpdateRequest request) {
        String msg;
        HeaderResponse response;
        UsersEntity userEntity = usersDao.findByHashUser(request.getHashUser()).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        userEntity.setModifiedDate(new Date());
        userEntity.setName(request.getName());
        userEntity.setLastName(request.getLastName());
        usersDao.save(userEntity);
        msg = ConstantText.UPDATE_MSG;
        response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Eliminar usuario
     * @param hashUser
     * @return
     */
    @Override
    public ResponseEntity<HeaderResponse> deleteUser(String hashUser) {
        String msg;
        HeaderResponse response;
        UsersEntity usersEntity = usersDao.findByHashUser(hashUser).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        //Es posble eliminarlo directamente pero por una buena practica solo lo elimino de forma logica
        usersEntity.setDeleted(true);
        usersDao.save(usersEntity);
        msg = ConstantText.MSG_DELETE;
        response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Cambiar la contrasseña
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<HeaderResponse> changePassword(PasswordRequest request) {
        String msg;
        HeaderResponse response;
        UsersEntity usersEntity = usersDao.findByHashUser(request.getHashUser()).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        usersEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usersDao.save(usersEntity);
        msg = ConstantText.USER_PASS;
        response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
