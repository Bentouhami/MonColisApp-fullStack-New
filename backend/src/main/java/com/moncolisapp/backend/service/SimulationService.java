package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.dto.SimulationRequest;
import com.moncolisapp.backend.dto.SimulationResponse;
import com.moncolisapp.backend.entities.Tarif;
import com.moncolisapp.backend.entities.StatutEnvoi;
import com.moncolisapp.backend.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SimulationService {

    @Autowired
    private TarifRepository tarifRepository;

    public SimulationResponse calculateSimulation(SimulationRequest request) {
        List<ColisDTO> colisList = request.getColis();

        BigDecimal poidsTotal = getPoidsTotal(colisList);

        BigDecimal volumeTotal = getVolumeTotal(colisList);

        BigDecimal prixTotal = getPrixTotal(poidsTotal);

        LocalDate currentDate = request.getCurrentDate();
        LocalDate dateEnvoiPrevu = getNextTuesday(currentDate);
        LocalDate dateLivraisonPrevu = dateEnvoiPrevu.plusDays(6); // Lundi suivant selon votre logique

        return new SimulationResponse(
                request.getPaysDepart(),
                request.getVilleDepart(),
                request.getAgenceDepart(),
                dateEnvoiPrevu,
                request.getPaysDestination(),
                request.getVilleDestination(),
                request.getAgenceArrive(),
                dateLivraisonPrevu,
                poidsTotal,
                volumeTotal,
                prixTotal,
                StatutEnvoi.EN_ATTENTE_DE_CONFIRMATION.getDisplayName()
        );
    }

    private LocalDate getNextTuesday(LocalDate currentDate) {
        while (currentDate.getDayOfWeek() != DayOfWeek.TUESDAY) {
            currentDate = currentDate.plusDays(1);
        }
        return currentDate;
    }

    private BigDecimal getPrixTotal(BigDecimal poidsTotal) {
        // Fetch tariff details from the database
        Optional<Tarif> tarifFixeOpt = tarifRepository.findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal.valueOf(10.0));
        Optional<Tarif> tarifVariableOpt = tarifRepository.findByPoidsMaxIsNullAndPrixParKiloIsNotNull();

        if (poidsTotal.compareTo(BigDecimal.valueOf(10)) <= 0 && tarifFixeOpt.isPresent()) {
            return tarifFixeOpt.get().getPrixFixe();
        } else if (poidsTotal.compareTo(BigDecimal.valueOf(10)) > 0 && tarifVariableOpt.isPresent()) {
            return poidsTotal.multiply(tarifVariableOpt.get().getPrixParKilo());
        }

        return BigDecimal.ZERO; // Default to 0 if no matching tariff is found
    }

    private static BigDecimal getVolumeTotal(List<ColisDTO> colisList) {
        return colisList.stream()
                .map(colis -> colis.getHauteur().multiply(colis.getLargeur()).multiply(colis.getLongueur()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getPoidsTotal(List<ColisDTO> colisList) {
        return colisList.stream()
                .map(ColisDTO::getPoidsColis)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
