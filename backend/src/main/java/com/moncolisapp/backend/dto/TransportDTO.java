package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Transport}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransportDTO implements Serializable {
    private Integer id;
    private Boolean estDisponible;
    private String plaqueMatriculation;
    private BigDecimal poidsActuel;
    private BigDecimal poidsDeBase;
    private BigDecimal volumeActuel;
    private BigDecimal volumeDeBase;


}