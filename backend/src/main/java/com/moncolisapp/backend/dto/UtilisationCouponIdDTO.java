package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * DTO for {@link com.moncolisapp.backend.entities.UtilisationCouponId}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UtilisationCouponIdDTO(Integer idClient, Integer idCoupon) implements Serializable {
}