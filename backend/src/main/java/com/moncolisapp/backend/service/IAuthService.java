package com.moncolisapp.backend.service;

import com.moncolisapp.backend.auth.UserRegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IAuthService {
//    String register(ClientDTO clientDTO);

    String register(UserRegisterRequest userRegisterRequest);

//    ResponseEntity<String> login(UserLoginRequest userLoginRequest);

    ResponseEntity<Map<String, Object>> login(String email, String password);

    boolean existsClientByEmail(String userEmailRequest);
}
