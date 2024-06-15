package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Client}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientDTO implements Serializable {
    private String nom;
    private String prenom;
    private LocalDate dateDeNaissance;
    private Boolean sexe;
    private String telephone;
    private String email;
    private AddressDTO idAddress;
    private String motDePasse;
}