package com.moncolisapp.backend.service;

import com.moncolisapp.backend.dto.ColisDTO;
import com.moncolisapp.backend.repository.TarifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

            if (totalDimensions <= 150 && maxDimension <= 120 && volume >= 3375) {
                double prixTotal = calculatePrix(colisDTO);
                colisDTO.setVolumeColis(volume);
//                colisDTO.setPrixTotal(prixTotal);
                validatedColis.add(colisDTO);
            }
        }

        return validatedColis;
    }

    private double calculatePrix(ColisDTO colisDTO) {
        double poidsTotal = colisDTO.getPoidsColis();
        if (poidsTotal <= 10) {
            return 15;
        } else {
            return poidsTotal * 1.60;
        }
    }
}
