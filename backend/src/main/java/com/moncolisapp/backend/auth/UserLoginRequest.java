package com.moncolisapp.backend.auth;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {
    String email;
    String password; // password is the user's password


}
