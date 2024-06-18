package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for SimulationResponse (response of the simulation endpoint)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationResponse implements Serializable {
    private String paysDepart;
    private String villeDepart;
    private String agenceDepart;
    private LocalDate dateEnvoiPrevu; // calculer la date de livraison à partir de la date current de SimulationRequest
    private String paysDestination;
    private String villeDestination;
    private String agenceArrive;
    private LocalDate dateLivraisonPrevu; // calculer la date de livraison à partir de la date dateEnvoiPrevu
    private BigDecimal poidsTotal; // le poids total calculé par la simulation d'envois
    private BigDecimal volumeTotal; // le volume total calculé par la simulation d'envois
    private BigDecimal prixTotal; // le prix total calculé par rapport au poidsTotal de la simulation d'envois
    private String statut; // TODO: enum ? (envoyé, rejeté, etc.) ?

}