package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.dto.SimulationRequest;
import com.moncolisapp.backend.dto.SimulationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class SimulationService {

    public SimulationResponse calculateSimulation(SimulationRequest request) {
//        ColisDTO coliss = request.getColis().get(0);
        double poidsTotal = request.getColis().stream().mapToDouble(ColisDTO::getPoidsColis).sum();
        double volumeTotal = request.getColis().stream().mapToDouble(colis -> colis.getHauteur() * colis.getLargeur() * colis.getLongueur()).sum();
        double prixTotal = (poidsTotal <= 10) ? 15 : poidsTotal * 1.60;

        LocalDate currentDate = request.getCurrentDate();
        LocalDate dateEnvoiPrevu = currentDate.plusDays(2); // exemple, ajustez selon votre logique
        LocalDate dateLivraisonPrevu = dateEnvoiPrevu.plusDays(5); // exemple, ajustez selon votre logique

        return new SimulationResponse(
                request.getPaysDepart(),
                request.getVilleDepart(),
                request.getAgenceDepart(),
                dateEnvoiPrevu,
                request.getPaysDestination(),
                request.getVilleDestination(),
                request.getAgenceArrive(),
                dateLivraisonPrevu,
                BigDecimal.valueOf(poidsTotal),
                BigDecimal.valueOf(volumeTotal),
                BigDecimal.valueOf(prixTotal),
                "envoyé" // ou tout autre statut que vous souhaitez
        );
    }
}
