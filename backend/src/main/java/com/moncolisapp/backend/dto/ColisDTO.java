package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Colis}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColisDTO implements Serializable {
    private BigDecimal hauteur;
    private BigDecimal largeur;
    private BigDecimal longueur;
    private BigDecimal poidsColis;
    private BigDecimal volumeColis;

}