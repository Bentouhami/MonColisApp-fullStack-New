package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvoisRequest implements Serializable {
    private String codeDeSuivi;
    private LocalDate dateEnvoi;
    private LocalDate dateLivraisonPrevu;
    private BigDecimal poidsTotal;
    private BigDecimal volumeTotal;
    private BigDecimal prixTotal;
    private String statut;
    private String nomAgenceArrivee;
    private String nomAgenceDepart;
    private String emailClient;
    private String codeCoupon;
    private String emailDestinataire;
    private BigDecimal prixTarif;
    private TransportDTO idTransport;
}