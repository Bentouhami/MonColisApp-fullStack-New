package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.entities.Tarif;
import com.moncolisapp.backend.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColisService {

    @Autowired
    private TarifRepository tarifRepository;

    public List<ColisDTO> validateColis(List<ColisDTO> colisDTOList) {
        List<ColisDTO> validatedColis = new ArrayList<>();

        for (ColisDTO colisDTO : colisDTOList) {
            double volume = colisDTO.getHauteur() * colisDTO.getLargeur() * colisDTO.getLongueur();
            double totalDimensions = colisDTO.getHauteur() + colisDTO.getLargeur() + colisDTO.getLongueur();
            double maxDimension = Math.max(colisDTO.getHauteur(), Math.max(colisDTO.getLargeur(), colisDTO.getLongueur()));

            if (totalDimensions <= 360 && maxDimension <= 120 && volume >= 1728 && colisDTO.getPoidsColis() <= 70 && colisDTO.getPoidsColis() >= 1) {
                colisDTO.setVolumeColis(volume);
                validatedColis.add(colisDTO);
            }
        }

        return validatedColis;
    }

    public double calculatePrixTotal(List<ColisDTO> colisDTOList) {
        double poidsTotal = colisDTOList.stream().mapToDouble(ColisDTO::getPoidsColis).sum();

        Optional<Tarif> tarifFixeOpt = tarifRepository.findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal.valueOf(10.0));
        Optional<Tarif> tarifVariableOpt = tarifRepository.findByPoidsMaxIsNullAndPrixParKiloIsNotNull();

        if (poidsTotal <= 10 && tarifFixeOpt.isPresent()) {
            return tarifFixeOpt.get().getPrixFixe().doubleValue();
        } else if (poidsTotal > 10 && tarifVariableOpt.isPresent()) {
            return poidsTotal * tarifVariableOpt.get().getPrixParKilo().doubleValue();
        }

        return 0.0; // Default to 0 if no matching tariff is found
    }

//
//    public double calculateTotalPrice(List<ColisDTO> colisDTOList) {
//        double totalWeight = 0;
//        for (ColisDTO colis : colisDTOList) {
//            totalWeight += colis.getPoidsColis();
//        }
//
//        // Fetch tariff details from the database
//        double fixedPrice = tarifsRepository.findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal.valueOf(10.0)).get().getPrixFixe().doubleValue();
//        double pricePerKg = 1.60;
//        double totalPrice;
//
//        if (totalWeight <= 10) {
//            totalPrice = fixedPrice;
//        } else {
//            totalPrice = totalWeight * pricePerKg;
//        }
//
//        return totalPrice;
//    }
}
