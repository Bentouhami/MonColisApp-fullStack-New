package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Tarif}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record TarifDTO(BigDecimal poidsMax, BigDecimal prixFixe, BigDecimal prixParKilo) implements Serializable {
}