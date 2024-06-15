package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.UtilisationCoupon}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UtilisationCouponDTO(ClientDTO idClient, CouponDTO idCoupon) implements Serializable {
}