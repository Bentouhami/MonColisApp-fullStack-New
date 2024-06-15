package com.moncolisapp.backend.auth;


public record UserRegisterRequest(
        String nom,
        String prenom,
        String dateDeNaissance,
        String sexe,
        String telephone,
        String email,
        String rue,
        String numero,
        String ville,
        String codepostal,
        String pays,
        String password
) {
}
