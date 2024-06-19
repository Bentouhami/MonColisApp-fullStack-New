package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO combiné pour la validation de l'envoi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvoisRequestDTO implements Serializable {
    private String paysDepart;
    private String villeDepart;
    private String agenceDepart;
    private LocalDate dateEnvoiPrevu;
    private String paysDestination;
    private String villeDestination;
    private String agenceArrive;
    private LocalDate dateLivraisonPrevu;
    private BigDecimal poidsTotal;
    private BigDecimal volumeTotal;
    private BigDecimal prixTotal;
    private String statut;
    private List<ColisDTO> colis;
    private String displayName;
    private DestinataireDTO idDestinataire;
    private AgenceDTO idAgenceArrivee;
    private AgenceDTO idAgenceDepart;
    private ClientDTO idClient;
    private CouponDTO idCoupon;
    private AddressDTO idAddress;
    private Integer idTarif;
    private TransportDTO idTransport;
}
