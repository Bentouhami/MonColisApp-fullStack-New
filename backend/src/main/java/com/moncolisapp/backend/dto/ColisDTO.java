package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Colis}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColisDTO implements Serializable {
    private EnvoisDTO idEnvoi;
    private double hauteur;
    private double largeur;
    private double longueur;
    private double poidsColis;
    private double volumeColis;
}