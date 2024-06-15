package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Agence}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AgenceDTO(AddressDTO idAddress, String nomAgence, String localisation) implements Serializable {
}