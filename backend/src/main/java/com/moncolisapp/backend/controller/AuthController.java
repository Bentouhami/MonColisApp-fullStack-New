package com.moncolisapp.backend.controller;


import com.moncolisapp.backend.auth.UserRegisterRequest;
import com.moncolisapp.backend.dto.AddressDTO;
import com.moncolisapp.backend.dto.ClientDTO;
import com.moncolisapp.backend.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auth/")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;

    private ClientDTO usersDao;
    private AddressDTO idAddress;


    // create register request method
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest userRegisterRequest) {

        // create register request method
        String response = iAuthService.register(userRegisterRequest);

        // return response
        return ResponseEntity.ok(response);
    }


}
