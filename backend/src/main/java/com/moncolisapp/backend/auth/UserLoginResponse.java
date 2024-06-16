package com.moncolisapp.backend.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginResponse {
    private String nom;
    private String prenom;
    private String dateDeNaissance;
    private String sexe;
    private String telephone;
    private String email;
    private String rue;
    private String numero;
    private String ville;
    private String codepostal;
    private String pays;
}
