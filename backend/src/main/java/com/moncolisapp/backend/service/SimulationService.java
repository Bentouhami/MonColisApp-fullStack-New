package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.dto.SimulationRequest;
import com.moncolisapp.backend.dto.SimulationResponse;
import com.moncolisapp.backend.dto.TarifResult;
import com.moncolisapp.backend.entities.StatutEnvoi;
import com.moncolisapp.backend.entities.Tarif;
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

        TarifResult tarifResult = getPrixTotal(poidsTotal);

        // get idTarif from tarifResult
        BigDecimal prixTotal = tarifResult.getPrixTotal();
        Integer idTarif = tarifResult.getTarifId();

        LocalDate currentDate = request.getCurrentDate();
        LocalDate dateEnvoiPrevu = getNextTuesday(currentDate);
        LocalDate dateLivraisonPrevu = dateEnvoiPrevu.plusDays(6);

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
                StatutEnvoi.EN_ATTENTE_DE_CONFIRMATION.getDisplayName(),
                colisList,
                idTarif
        );
    }


    private LocalDate getNextTuesday(LocalDate currentDate) {
        while (currentDate.getDayOfWeek() != DayOfWeek.TUESDAY) {
            currentDate = currentDate.plusDays(1);
        }
        return currentDate;
    }

    /**
     * Calculate the total price of the colis based on the tariff details and the weight of the colis
     * @param poidsTotal the total weight of the colis
     * @return TarifResult containing the total price of the colis and the tariff id
     */
    private TarifResult getPrixTotal(BigDecimal poidsTotal) {
        // Fetch tariff details from the database
        Optional<Tarif> tarifFixeOpt = tarifRepository.findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal.valueOf(10.0));
        Optional<Tarif> tarifVariableOpt = tarifRepository.findByPoidsMaxIsNullAndPrixParKiloIsNotNull();

        // if no matching tariff is found, return a default value of 0 and null
        // verify that the poidsTotal is less or equal to 10 kg and that the tariff is not null
        if (poidsTotal.compareTo(BigDecimal.valueOf(10)) <= 0 && tarifFixeOpt.isPresent()) {
            // if the tariff is a fixed tariff, return the prixTotal and the tariff id of the tariff
            Tarif tarifFixe = tarifFixeOpt.get();

            // return the prixTotal and the tariff id as a TarifResult
            return new TarifResult(tarifFixe.getPrixFixe(), tarifFixe.getId());

        } else if (poidsTotal.compareTo(BigDecimal.valueOf(10)) > 0 && tarifVariableOpt.isPresent()) {
            // if the tariff is a variable tariff, calculate the total price based on the weight of the colis
            Tarif tarifVariable = tarifVariableOpt.get();

            //
            BigDecimal prixTotal = poidsTotal.multiply(tarifVariable.getPrixParKilo());
            return new TarifResult(prixTotal, tarifVariable.getId());
        }

        // if no matching tariff is found, return a default value of 0 and null
        return new TarifResult(BigDecimal.ZERO, null); // Default to 0 if no matching tariff is found
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
