package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Address}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record AddressDTO(String rue, String numero, String ville, String codepostal,
                         String pays) implements Serializable {
}