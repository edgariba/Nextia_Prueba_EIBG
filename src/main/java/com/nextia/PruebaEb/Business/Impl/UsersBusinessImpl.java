package com.nextia.PruebaEb.Business.Impl;

import com.nextia.PruebaEb.Business.Interfaces.UsersBusiness;
import com.nextia.PruebaEb.Dao.UsersDao;
import com.nextia.PruebaEb.Entity.UsersEntity;
import com.nextia.PruebaEb.Exceptions.ConflictException;
import com.nextia.PruebaEb.Utils.ConstantText;
import com.nextia.PruebaEb.Utils.GenerateUuid;
import com.nextia.PruebaEb.Utils.Header.HeaderResponse;
import com.nextia.PruebaEb.Ws.Request.Users.LoginRequest;
import com.nextia.PruebaEb.Ws.Request.Users.PasswordRequest;
import com.nextia.PruebaEb.Ws.Request.Users.UserAddRequest;
import com.nextia.PruebaEb.Ws.Request.Users.UserUpdateRequest;
import com.nextia.PruebaEb.Ws.Response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("usersBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class UsersBusinessImpl implements UsersBusiness {

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<UserResponse> getUserByHash(String hashUser) {
        String msg;
        UserResponse response;
        UsersEntity userEntity = usersDao.findByHashUser(hashUser).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        msg = ConstantText.MSG_GET;
        response = new UserResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), userEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserResponse> loginUser(LoginRequest request) {
        String msg;
        UserResponse response;
        UsersEntity usersEntity = usersDao.findByEmail(request.getEmail()).orElseThrow(() -> new ConflictException(ConstantText.LOGIN_ERROR));
        if(usersEntity.isDeleted()){
            throw new ConflictException(ConstantText.USER_NOT_ACTIVE);
        }
        boolean matches = passwordEncoder.matches(request.getPassword(), usersEntity.getPassword());
        if(!matches){
            throw new ConflictException(ConstantText.LOGIN_ERROR);
        }
        msg = ConstantText.LOGIN_OK;
        response = new UserResponse(new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg), usersEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> addUser(UserAddRequest request) {
        String msg;
        HeaderResponse response;
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

    @Override
    public ResponseEntity<HeaderResponse> updateUser(UserUpdateRequest request) {
        String msg;
        HeaderResponse response;
        UsersEntity userEntity = usersDao.findByHashUser(request.getHashUser()).orElseThrow(() -> new ConflictException(ConstantText.USER_INVALID));
        userEntity.setModifiedDate(new Date());
        userEntity.setName(request.getName());
        userEntity.setLastName(request.getLastName());
        userEntity.setEmail(request.getEmail());
        usersDao.save(userEntity);
        msg = ConstantText.UPDATE_MSG;
        response = new HeaderResponse(ConstantText.SUCCESS, HttpStatus.OK.value(), msg);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
