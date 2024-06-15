package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link com.moncolisapp.backend.entities.Envois}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvoisDTO implements Serializable {
    private String codeDeSuivi;
    private LocalDate dateEnvoi;
    private LocalDate dateLivraisonPrevu;
    private BigDecimal poidsTotal;
    private BigDecimal volumeTotal;
    private BigDecimal prixTotal;
    private String statut;
    private AgenceDTO idAgenceArrivee;
    private AgenceDTO idAgenceDepart;
    private ClientDTO idClient;
    private CouponDTO idCoupon;
    private DestinataireDTO idDestinataire;
    private TarifDTO idTarif;
    private TransportDTO idTransport;
}