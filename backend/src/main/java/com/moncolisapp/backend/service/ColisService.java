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
            BigDecimal volume = colisDTO.getHauteur().multiply(colisDTO.getLargeur()).multiply(colisDTO.getLongueur());
            BigDecimal totalDimensions = colisDTO.getHauteur().add(colisDTO.getLargeur()).add(colisDTO.getLongueur());
            BigDecimal maxDimension = colisDTO.getHauteur().max(colisDTO.getLargeur().max(colisDTO.getLongueur()));

            if (totalDimensions.compareTo(BigDecimal.valueOf(360)) <= 0 &&
                    maxDimension.compareTo(BigDecimal.valueOf(120)) <= 0 &&
                    volume.compareTo(BigDecimal.valueOf(1728)) >= 0 &&
                    colisDTO.getPoidsColis().compareTo(BigDecimal.valueOf(70)) <= 0 &&
                    colisDTO.getPoidsColis().compareTo(BigDecimal.valueOf(1)) >= 0) {
                colisDTO.setVolumeColis(volume);
//                validatedColis.add(colisDTO);
            }
        }

        return validatedColis;
    }

    public double calculatePrixTotal(List<ColisDTO> colisDTOList) {
        BigDecimal poidsTotal = colisDTOList.stream()
                .map(ColisDTO::getPoidsColis)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Optional<Tarif> tarifFixeOpt = tarifRepository.findByPoidsMaxAndPrixFixeIsNotNull(BigDecimal.valueOf(10.0));
        Optional<Tarif> tarifVariableOpt = tarifRepository.findByPoidsMaxIsNullAndPrixParKiloIsNotNull();

        if (poidsTotal.compareTo(BigDecimal.valueOf(10)) <= 0 && tarifFixeOpt.isPresent()) {
            return tarifFixeOpt.get().getPrixFixe().doubleValue();
        } else if (poidsTotal.compareTo(BigDecimal.valueOf(10)) > 0 && tarifVariableOpt.isPresent()) {
            return poidsTotal.multiply(tarifVariableOpt.get().getPrixParKilo()).doubleValue();
        }

        return 0.0; // Default to 0 if no matching tariff is found
    }
}
