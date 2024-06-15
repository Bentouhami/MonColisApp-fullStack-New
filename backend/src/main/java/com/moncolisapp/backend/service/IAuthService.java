package com.moncolisapp.backend.service;

import com.moncolisapp.backend.auth.UserRegisterRequest;

public interface IAuthService {
//    String register(ClientDTO clientDTO);

    String register(UserRegisterRequest userRegisterRequest);
}
