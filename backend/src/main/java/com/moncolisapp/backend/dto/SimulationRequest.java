package com.moncolisapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for SimulationRequest (request of the simulation endpoint)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationRequest implements Serializable {
    private String paysDepart;
    private String villeDepart;
    private String agenceDepart;
    private String paysDestination;
    private String villeDestination;
    private String agenceArrive;
    private LocalDate currentDate; // pour calculer les dates envois/arrivées
    private List<ColisDTO> colis;

}