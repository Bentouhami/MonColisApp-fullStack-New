package com.moncolisapp.backend.controller;


import com.moncolisapp.backend.auth.EmailRequest;
import com.moncolisapp.backend.auth.UserLoginRequest;
import com.moncolisapp.backend.auth.UserRegisterRequest;
import com.moncolisapp.backend.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/auth/")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;



    // create register request method
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        // create register request method
        String response = iAuthService.register(userRegisterRequest);
        if (response.equals("User created")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginRequest userLoginRequest) {
        ResponseEntity<Map<String, Object>> response = iAuthService.login(userLoginRequest.getEmail(), userLoginRequest.getPassword());
        return response;
    }


    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody EmailRequest emailRequest) {
        Map<String, Boolean> response = new HashMap<>();
        boolean existsUser = iAuthService.existsClientByEmail(emailRequest.getEmail());
        response.put("exists", existsUser);
        return ResponseEntity.ok(response);
    }



}
