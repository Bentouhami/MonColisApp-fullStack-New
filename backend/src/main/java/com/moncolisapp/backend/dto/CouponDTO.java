package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Coupon}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CouponDTO(String codeCoupon, String conditions, LocalDate dateDebut, LocalDate dateFin,
                        BigDecimal montantRemise, Integer nombreUtilisationMax,
                        BigDecimal pourcentageRemise) implements Serializable {
}