package com.moncolisapp.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationDTO {
    private String paysDepart;
    private String villeDepart;
    private String agenceDepart;
    private String paysDestination;
    private String villeDestination;
    private String agenceArrive;
    private LocalDate currentDate;
    private LocalDate dateEnvoiPrevu;
    private LocalDate dateLivraisonPrevu;
    private BigDecimal poidsTotal;
    private BigDecimal volumeTotal;
    private BigDecimal prixTotal;
    private String statut;
    private List<ColisDTO> colis;

    // Getters and Setters

}
