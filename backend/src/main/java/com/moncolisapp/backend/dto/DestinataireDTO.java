package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Destinataire}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DestinataireDTO(String nomPrenom, String telephone, String email,
                              AddressDTO idAddress) implements Serializable {
}